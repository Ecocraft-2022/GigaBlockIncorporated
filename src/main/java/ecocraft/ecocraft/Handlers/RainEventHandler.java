package ecocraft.ecocraft.Handlers;

import com.google.common.collect.Lists;
import ecocraft.ecocraft.CustomBlocks.SolarPanel;
import ecocraft.ecocraft.CustomBlocks.SolarPanelBase;
import ecocraft.ecocraft.Events.AcidRain;
import ecocraft.ecocraft.Events.NightEvent;
import ecocraft.ecocraft.Utils.Util;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Furnace;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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
    public void onWeatherChange(WeatherChangeEvent e) {
        AcidRain rain = new AcidRain();
        if(!e.getWorld().hasStorm()) {
            task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> rain.rain(), 0, 30);
        }else {
            Bukkit.getScheduler().cancelTask(task);
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
}
