package ecocraft.ecocraft.Handlers;

import ecocraft.ecocraft.CustomBlocks.SolarPanel;
import ecocraft.ecocraft.Events.AcidRain;
import ecocraft.ecocraft.Events.NightEvent;
import ecocraft.ecocraft.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.Plugin;

public class RainEventHandler implements Listener {

    Plugin plugin;
    boolean isRain = false;
    AcidRain acidRain;

    public RainEventHandler(Plugin plugin) {
        this.plugin = plugin;
        this.acidRain = new AcidRain();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e)
    {
        if(e.toWeatherState() )
        {
            isRain = true;
            acidRain.runTaskTimer(plugin, 0, 40);
        }else{
            if(isRain){
                acidRain.cancel();
            }
            isRain = false;
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
                        for (int y = 0; y < 128; y++) {
                            Block test = w.getBlockAt(x, y, z);

                            if (test.getType().equals(Material.NOTE_BLOCK) && Util.compareBlocks(SolarPanel.getInstance(), (NoteBlock) test.getBlockData())) {
                                Util u = new Util();
                                u.findDesiredBlocks(test.getRelative(BlockFace.DOWN));

                                u.getFurnaces().stream().forEach(
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
