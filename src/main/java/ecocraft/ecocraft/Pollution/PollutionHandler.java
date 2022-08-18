package ecocraft.ecocraft.Pollution;

import ecocraft.ecocraft.Events.ChangeRegionEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.javatuples.Pair;

import java.io.IOException;


public class PollutionHandler implements Listener {

    Plugin plugin;

    public PollutionHandler(Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void regionChangeJoin(PlayerJoinEvent join) {
        regionChangeEvent(join.getPlayer());
    }

    @EventHandler
    public void regionChangeMove(PlayerMoveEvent e) {
        regionChangeEvent(e.getPlayer());
    }


    private void regionChangeEvent(Player player) {
        Scoreboard sb = player.getScoreboard();
        Objective obj;

        if (player.getScoreboard().getObjective("") != null) {
            obj = player.getScoreboard().getObjective("");
        } else {
            obj = sb.registerNewObjective("", "dummy", "");
        }
        Score score = obj.getScore("region");
        Pair<Integer, Integer> regionN = Region.getRegionNumber(player.getLocation().getBlockX(), player.getLocation().getBlockZ());

        if (score.getScore() != regionN.getValue0() + regionN.getValue1()) {
            score.setScore(regionN.getValue0() + regionN.getValue1());
            try {
                ChangeRegionEvent event = new ChangeRegionEvent(player);
                Bukkit.getServer().getPluginManager().callEvent(event);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @EventHandler
    public void localPollution(ChangeRegionEvent e) {

        Region region = e.getRegion();
        Integer localPollution = region.getLocalPollution();
        if (localPollution == null) {
            localPollution = 0;
            Pair<Integer, Integer> leftBottom = new Pair<>(Double.valueOf(region.getCenter().getValue0() - Regions.regionDim).intValue()
                    , Double.valueOf(region.getCenter().getValue1() - Regions.regionDim).intValue());
            Pair<Integer, Integer> rightTop = new Pair<>(Double.valueOf(region.getCenter().getValue0() + Regions.regionDim).intValue()
                    , Double.valueOf(region.getCenter().getValue1() + Regions.regionDim).intValue());

            World world = e.getPlayer().getWorld();

            for (int x = leftBottom.getValue0(); x < rightTop.getValue0(); x++) {
                for (int z = leftBottom.getValue1(); z < rightTop.getValue1(); z++) {
                    for (int y = 20; y < 255; y++) {
                        Block b = world.getBlockAt(new Location(world, x, y, z));
                        if (b.getType().equals(Material.FURNACE)) {
                            localPollution++;
                        }
                    }
                }
            }
            region.setLocalPollution(localPollution);
        }
        handlePollution(e.getPlayer(),region);
    }

    @EventHandler
    public void localPollutionOnPlace(BlockPlaceEvent e) {

        Block blockPlaced = e.getBlockPlaced();

        Player player = e.getPlayer();

        Region region;

        try {
            region = Region.getPlayerRegion(player.getLocation().getBlockX(), player.getLocation().getBlockZ());
        } catch (IOException ex) {
            return;
        }

        Integer localPollution = region.getLocalPollution();

        if (blockPlaced.getType().equals(Material.FURNACE)) {
            localPollution++;
        }
        if (blockPlaced.getType().equals(Material.SPRUCE_SAPLING) ||
                blockPlaced.getType().equals(Material.ACACIA_SAPLING) ||
                blockPlaced.getType().equals(Material.BIRCH_SAPLING) ||
                blockPlaced.getType().equals(Material.OAK_SAPLING) ||
                blockPlaced.getType().equals(Material.DARK_OAK_SAPLING) ||
                blockPlaced.getType().equals(Material.JUNGLE_SAPLING)
        ) {
                localPollution--;
        }
        region.setLocalPollution(localPollution);

        handlePollution(e.getPlayer(),region);
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
        region.setLocalPollution(localPollution);

        handlePollution(e.getPlayer(),region);
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
    private BossBar b;

    public static boolean between(int variable, int minValueInclusive, int maxValueInclusive) {
        return variable >= minValueInclusive && variable <= maxValueInclusive;
    }

    private void handlePollution(Player p, Region region) {

        StringBuilder BossBarTitle = new StringBuilder();
        Integer overallPollution = region.getPollutionLevel()+region.getLocalPollution();
       String title = BossBarTitle
//               .append("Local Pollution: ").append(region.getLocalPollution()).append("\n")
                .append("Pollution: ").append(overallPollution).toString();

        if (b == null) {
            b = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID);
        }

        if(between(overallPollution,0,40)){
            b.setColor(BarColor.GREEN);
        }

        if(between(overallPollution,41,80)){
            b.setColor(BarColor.YELLOW);
        }

        if(between(overallPollution,81,120)){
            b.setColor(BarColor.RED);
        }


        b.setTitle(title);

        b.setVisible(true);
        if (!b.getPlayers().contains(p)) {
            b.addPlayer(p);
        }

        Integer pollution = region.getLocalPollution()+region.getPollutionLevel();

        Float barPol  = (float) pollution / 120;

        if(barPol>1){
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

}
