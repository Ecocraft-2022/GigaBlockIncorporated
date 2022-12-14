package ecocraft.ecocraft.Handlers;

import ecocraft.ecocraft.CustomBlocks.SolarPanel;
import ecocraft.ecocraft.CustomBlocks.SolarPanelBase;
import ecocraft.ecocraft.Events.AcidRain;
import ecocraft.ecocraft.Events.NightEvent;
import ecocraft.ecocraft.Pollution.Region;
import ecocraft.ecocraft.Utils.Util;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Furnace;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class RainEventHandler implements Listener {

    Plugin plugin;

    AcidRain acidRain;

    int task = -1;

    public RainEventHandler(Plugin plugin) {
        this.plugin = plugin;
        this.acidRain = new AcidRain();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void pollutedDestroy(BlockBreakEvent e){

        Integer mediumPollution = plugin.getConfig().getInt("mediumPollutionEnd");

        Block block = e.getBlock();
        if(Util.isCrop(block)) {
            Location blockLocation = block.getLocation();
            Region region = Region.getRegionBy(blockLocation.getBlockX(), blockLocation.getBlockZ());
            Integer overallPollution = region.getLocalPollution() + region.getPollutionLevel();
            if(overallPollution>mediumPollution){
                Random random = new Random();
                    e.setDropItems(random.nextBoolean());
            }
        }
    }
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        AcidRain rain = new AcidRain();
        if(!e.getWorld().hasStorm()) {
            task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                try {
                    rain.rain();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }, 0, 30);
        }else {
            if(task>0) {
                Bukkit.getScheduler().cancelTask(task);
            }
        }
    }





    @EventHandler
    public void onNightEvent(NightEvent e) {
        World w = Bukkit.getWorld(plugin.getServer().getWorlds().stream().findFirst().get().getName());
        Chunk c[] = w.getLoadedChunks();

        for (Chunk ch : c) {

            if (ch.contains(SolarPanel.getInstance().getItem().getType().createBlockData())) {
                int cx = ch.getX() << 4;
                int cz = ch.getZ() << 4;

                for (int x = cx; x < cx + 16; x++) {
                    for (int z = cz; z < cz + 16; z++) {

                            Block test = w.getHighestBlockAt(x,z);

                            if (test.getType().equals(Material.NOTE_BLOCK) && Util.compareBlocks(SolarPanel.getInstance(), (NoteBlock) test.getBlockData())) {
                                Util u = new Util();

                                if (test.getRelative(BlockFace.DOWN).getType().equals(Material.NOTE_BLOCK) && Util.compareBlocks(SolarPanelBase.getInstance(), (NoteBlock) test.getRelative(BlockFace.DOWN).getBlockData())) {
                                    List<Furnace> furnaces = u.findFurnaces(test.getRelative(BlockFace.DOWN));

//                                u.findDesiredBlocks(test.getRelative(BlockFace.DOWN));
//
                                    furnaces.stream().forEach(
                                            f -> {
                                                if (e.isNight()) {
                                                    f.setBurnTime(Short.valueOf("0"));
                                                    f.update();
                                                } else {
                                                    f.setBurnTime(Short.MAX_VALUE);
                                                    f.update();
                                                }
                                            }
                                    );

                            }
                        }
                    }
                }
            }

        }
    }
}
