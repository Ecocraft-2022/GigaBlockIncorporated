package ecocraft.ecocraft.Pollution;

import com.google.common.collect.Lists;
import ecocraft.ecocraft.CustomBlocks.SolarPanel;
import ecocraft.ecocraft.Events.ChangeRegionEvent;
import ecocraft.ecocraft.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.javatuples.Pair;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PollutionHandler implements Listener {

    private static Plugin plugin;

    public PollutionHandler(Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void regionChangeJoin(PlayerJoinEvent join) {
        Region region = initRegion(join.getPlayer());
        loadRegions(join.getPlayer());
        handleLocalPollution(join.getPlayer(),region);
    }

    public static Region initRegion(Player player) {
        Location playerLocation = player.getLocation();
        Region region;

        try {
            loadRegions(player);
            region = Region.getPlayerRegion(playerLocation.getBlockX(), playerLocation.getBlockZ());
            handlePollution(player, region);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return region;
    }

    @EventHandler
    public void regionChangeMove(PlayerMoveEvent e) {
        regionChangeEvent(e.getPlayer());
    }


    public static void regionChangeEvent(Player player) {
        Scoreboard sb = player.getScoreboard();
        Objective obj;

        if (player.getScoreboard().getObjective("") != null) {
            obj = player.getScoreboard().getObjective("");
        } else {
            obj = sb.registerNewObjective("", "dummy", "");
        }
        Score score = obj.getScore("region");

        Integer playerX = player.getLocation().getBlockX();

        Integer playerZ = player.getLocation().getBlockZ();

        Pair<Integer, Integer> regionN = Region.getRegionNumber(playerX, playerZ);


        if (score.getScore() != regionN.getValue0() + regionN.getValue1()) {
            score.setScore(regionN.getValue0() + regionN.getValue1());
            try {
                ChangeRegionEvent event = new ChangeRegionEvent(player);
                Bukkit.getServer().getPluginManager().callEvent(event);
                loadRegions(player);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }



    }

    public static void loadRegions(Player player) {


        Integer playerX = player.getLocation().getBlockX() -  Double.valueOf(Math.ceil( player.getLocation().getDirection().getX())).intValue();

        Integer playerZ = player.getLocation().getBlockZ() - Double.valueOf(Math.ceil( player.getLocation().getDirection().getZ())).intValue();

        List<Region> loadedRegions = new ArrayList<>();

        Region region;
        try {
            region = Region.getPlayerRegion(playerX, playerZ);

            Pair<Integer, Integer> center = region.getCenter();
            Regions.loadedRegions.put(player, Lists.newArrayList(region));
            for (double x = center.getValue0() - Regions.regionDim; x <= center.getValue0() + Regions.regionDim; x += Regions.regionDim/2) {
                for (double z = center.getValue1() - Regions.regionDim; z <= center.getValue1() + Regions.regionDim; z += Regions.regionDim/2) {
                    loadedRegions.add(Region.getPlayerRegion((int) x, (int) z));
                }
            }
            Regions.loadedRegions.put(player, loadedRegions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void localPollution(ChangeRegionEvent e) {
        handleLocalPollution(e.getPlayer(),e.getRegion());
    }

    public static void handleLocalPollution(Player player,Region reg) {

        for(Region region : Regions.loadedRegions.get(player)) {

            Integer localPollution = region.getLocalPollution();
            if (localPollution == null) {

                localPollution = 0;

                Pair<Integer, Integer> leftBottom = new Pair<>(region.getCenter().getValue0() - Double.valueOf(Regions.regionDim/2).intValue()
                        , region.getCenter().getValue1() - Double.valueOf(Regions.regionDim/2).intValue());
                Pair<Integer, Integer> rightTop = new Pair<>(region.getCenter().getValue0() + Double.valueOf(Regions.regionDim/2).intValue()
                        , region.getCenter().getValue1() + Double.valueOf(Regions.regionDim/2).intValue());

                for(int x = leftBottom.getValue0(); x<=rightTop.getValue0();x++){
                    for(int z = leftBottom.getValue1();z<=rightTop.getValue1();z++){
                        for(int y = 20; y<200; y++) {

                            World world = player.getWorld();

                            Block b = world.getBlockAt(new Location(world, x, y, z));

                            localPollution = goodPollution(localPollution, b);

                        }
                    }
                    region.setLocalPollution(localPollution);
                }
            }
        }
        handlePollution(player, reg);
    }


    private static Integer goodPollution(Integer pollution, Block b) {

        Integer localPollution = pollution;

        if (b.getType().equals(Material.FURNACE)) {

            localPollution++;
        }

        if (b.getType().equals(Material.SPRUCE_SAPLING) ||
                b.getType().equals(Material.ACACIA_SAPLING) ||
                b.getType().equals(Material.BIRCH_SAPLING) ||
                b.getType().equals(Material.OAK_SAPLING) ||
                b.getType().equals(Material.DARK_OAK_SAPLING) ||
                b.getType().equals(Material.JUNGLE_SAPLING)
        ) {
            localPollution--;
        }

        if (b.getType().equals(Material.NOTE_BLOCK)) {
            if (Util.compareBlocks(SolarPanel.getInstance(), (NoteBlock) b.getBlockData())) {
                localPollution--;
            }
        }

        return localPollution;
    }

    @EventHandler
    public void localPollutionOnPlace(BlockPlaceEvent e) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            Block blockPlaced = e.getBlockPlaced();

            Player player = e.getPlayer();

            Region region;

            try {
                region = Region.getPlayerRegion(player.getLocation().getBlockX(), player.getLocation().getBlockZ());
            } catch (IOException ex) {
                return;
            }

            Integer localPollution = region.getLocalPollution();

            localPollution = goodPollution(localPollution, blockPlaced);

            region.setLocalPollution(localPollution);

            handlePollution(e.getPlayer(), region);
        }, 2);
    }

    @EventHandler
    public void localPollutionOnBreak(BlockBreakEvent e) {

        Block blockPlaced = e.getBlock();

        Player player = e.getPlayer();

        Region region;

        try {
            region = Region.getPlayerRegion(player.getLocation().getBlockX(), player.getLocation().getBlockZ());
        } catch (IOException ex) {
            return;
        }

        Integer localPollution = region.getLocalPollution();

        if (blockPlaced.getType().equals(Material.FURNACE)) {
            localPollution--;
        }

        if (blockPlaced.getType().equals(Material.SPRUCE_SAPLING) ||
                blockPlaced.getType().equals(Material.ACACIA_SAPLING) ||
                blockPlaced.getType().equals(Material.BIRCH_SAPLING) ||
                blockPlaced.getType().equals(Material.OAK_SAPLING) ||
                blockPlaced.getType().equals(Material.DARK_OAK_SAPLING) ||
                blockPlaced.getType().equals(Material.JUNGLE_SAPLING)
        ) {
            localPollution++;
        }
        if (blockPlaced.getType().equals(Material.NOTE_BLOCK)) {
            if (Util.compareBlocks(SolarPanel.getInstance(), (NoteBlock) blockPlaced.getBlockData())) {
                localPollution++;
            }
        }

        region.setLocalPollution(localPollution);

        handlePollution(e.getPlayer(), region);
    }

    @EventHandler
    public void updateRegionData(ChangeRegionEvent e) {
        Region r = e.getRegion();
        Player player = e.getPlayer();

        Map<String, String> data = r.regionInfo;

        if (player.getScoreboard().getObjective("LocalPollution") == null) {
            return;
        }
        Scoreboard sb = player.getScoreboard();
        Objective obj = sb.getObjective("LocalPollution");

        for (String key : data.keySet()) {
            //region data that is usefull here is stored in JSON format
            //this try checks if its JSON, if it is sets new score to scoreboard
            try {
                if (key.equals("pm25") || key.equals("pm10") || key.equals("o3") || key.equals("no2") || key.equals("so2") || key.equals("co")) {
                    Score score = obj.getScore(key.toUpperCase());
                    JSONObject v = new JSONObject(data.get(key));
                    score.setScore(v.getInt("v"));
                }
            } catch (Exception xe) {
            }
        }

        (player).setScoreboard(sb);

    }

    //    @EventHandler
//    public void isPlayerCloseToPollution(PlayerMoveEvent e){
//        Player p  = e.getPlayer();
//        int x = p.getLocation().getBlockX();
//        int z = p.getLocation().getBlockZ();
//        int y = p.getLocation().getBlockY();
//
//        int pollutionCount = 0;
//
//        List<Furnace> furnaces= new ArrayList<>();
//        for (int i = x-5;i<=x+5;i++){
//            for(int j = z-5;j<z+5;j++){
//                for (int k = y-3; k <= y+3; k++) {
//                    Block b = Bukkit.getWorld(Bukkit.getWorlds().stream().findFirst().get().getName()).getBlockAt(i,k,j);
//                    if(b.getType().equals(Material.FURNACE)){
//                        furnaces.add((Furnace )b.getState());
//                    }
//                }
//            }
//        }
//
//        for(Furnace f : furnaces){
//            if(f.getInventory().getFuel()!=null && f.getInventory().getFuel().getType().equals(Material.COAL)&& f.getCookTime()>0){
//                pollutionCount++;
//            }
//        }
//        handlePollution(p,pollutionCount);
//    }
    private static BossBar b;

    public static boolean between(int variable, int minValueInclusive, int maxValueInclusive) {
        return variable >= minValueInclusive && variable <= maxValueInclusive;
    }

    private static void handlePollution(Player p, Region region) {

        StringBuilder BossBarTitle = new StringBuilder();
        Integer overallPollution;
        if (region.getLocalPollution() == null) {
            overallPollution = region.getPollutionLevel();
        } else {
            overallPollution = region.getPollutionLevel() + region.getLocalPollution();
        }

        String title = BossBarTitle
//               .append("Local Pollution: ").append(region.getLocalPollution()).append("\n")
                .append("Pollution: ").append(overallPollution).toString();

        if (b == null) {
            b = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID);
        }


        b.setTitle(title);

        b.setVisible(true);
        if (!b.getPlayers().contains(p)) {
            b.addPlayer(p);
        }


        Integer maxPollution = plugin.getConfig().getInt("maxPollution");

        Integer lowPollution = plugin.getConfig().getInt("lowPollutionEnd");

        Integer mediumPollution = plugin.getConfig().getInt("mediumPollutionEnd");

        Float barPol = (float) overallPollution / maxPollution;


        if (between(overallPollution, 0, lowPollution)) {
            b.setColor(BarColor.GREEN);
        }

        if (between(overallPollution, lowPollution + 1, mediumPollution)) {
            b.setColor(BarColor.YELLOW);
        }

        if (between(overallPollution, mediumPollution + 1, maxPollution)) {
            b.setColor(BarColor.RED);
        }

        if (barPol > 1) {
            barPol = 1f;
        }

        b.setProgress(barPol);
//        try {
//            if (p.getInventory().getHelmet().getType().equals(Material.TURTLE_HELMET)) {
//                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, pollution * 100, pollution));
//            } else {
//                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, pollution * 100, pollution));
//                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, pollution * 100, pollution));
//            }
//        } catch (Exception e) {
//            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, pollution * 100, pollution));
//            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, pollution * 100, pollution));
//        }

//        if (pollution == 0) {
//            p.removePotionEffect(PotionEffectType.BLINDNESS);
//            p.removePotionEffect(PotionEffectType.SLOW);
//        }

    }

    public static BossBar getBossBar() {
        return b;
    }

}
