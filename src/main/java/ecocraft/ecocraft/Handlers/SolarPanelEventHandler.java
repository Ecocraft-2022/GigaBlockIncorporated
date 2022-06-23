package ecocraft.ecocraft.Handlers;

import ecocraft.ecocraft.CustomBlocks.SolarPanel;
import ecocraft.ecocraft.CustomBlocks.SolarPanelBase;
import ecocraft.ecocraft.Ecocraft;
import ecocraft.ecocraft.Events.NightEvent;
import ecocraft.ecocraft.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Furnace;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class SolarPanelEventHandler implements Listener {
    private Plugin plugin;

    public SolarPanelEventHandler(Ecocraft plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public boolean onSolarPanelPlaced(BlockPlaceEvent e) {

        if (e.getBlockPlaced().getType() == SolarPanel.getSolarPanel().getType()) {
            if (e.getBlockPlaced().getRelative(BlockFace.DOWN).getType() != SolarPanelBase.getSolarPanel().getType()) {
                e.getPlayer().sendMessage("Solar panel will not produce power");
                return true;
            }
            Util u = new Util();
            u.findDesiredBlocks(e.getBlockPlaced().getRelative(BlockFace.DOWN));
            e.getPlayer().sendMessage("Solar panel is producing power");

            u.getFurnaces().stream().forEach(
                    f -> {
                        f.setBurnTime(Short.MAX_VALUE);
                        f.update();
                    }
            );
        }
        return true;
    }


    @EventHandler
    public void onNightEvent(NightEvent e) {
        World w = Bukkit.getWorld(plugin.getServer().getWorlds().stream().findFirst().get().getName());
        Chunk c[] = w.getLoadedChunks();

        for (Chunk ch : c) {

            if (ch.contains(SolarPanel.getSolarPanel().getType().createBlockData())) {
                int cx = ch.getX() << 4;
                int cz = ch.getZ() << 4;

                for (int x = cx; x < cx + 16; x++) {
                    for (int z = cz; z < cz + 16; z++) {
                        for (int y = 0; y < 128; y++) {
                            Block test = w.getBlockAt(x, y, z);

                            if (test.getType().equals(SolarPanel.getSolarPanel().getType())) {
                                Util u = new Util();
                                u.findDesiredBlocks(test.getRelative(BlockFace.DOWN));

                                u.getFurnaces().stream().forEach(
                                        f -> {
                                            if(e.isNight()) {
                                                f.setBurnTime(Short.valueOf("0"));
                                                f.update();
                                            }else {
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
