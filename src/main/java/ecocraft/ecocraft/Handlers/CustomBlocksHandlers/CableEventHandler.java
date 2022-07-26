package ecocraft.ecocraft.Handlers.CustomBlocksHandlers;

import ecocraft.ecocraft.CustomBlocks.Cable;
import ecocraft.ecocraft.Utils.Util;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;


public class CableEventHandler implements Listener {

    static Plugin plugin;

    public CableEventHandler(Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private static final BlockFace list[] = {BlockFace.DOWN, BlockFace.EAST, BlockFace.UP, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST};

    @EventHandler
    public void onCableDestroyed(BlockBreakEvent e) {


        Block block = e.getBlock();

        BlockData data = block.getBlockData();


        if (block.getType().equals(Material.NOTE_BLOCK) && Util.compareBlocks(Cable.getInstance(), (NoteBlock) data)) {

            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                for (BlockFace b : list) {

                    BlockData data2 = block.getRelative(b).getBlockData();

                    if (block.getRelative(b).getType().equals(Material.NOTE_BLOCK)
                            && (Util.compareBlocks(Cable.getInstance(), (NoteBlock) data2)
                            || block.getRelative(b).getType().equals(Material.FURNACE))) {
                        Util u = new Util();
                        u.connected(block.getRelative(b));
                        if (!u.isConnected()) {
                            u.findDesiredBlocks(block);
                        }
                        u.getFurnaces().stream().forEach(
                                f -> {
                                    Util u2 = new Util();
                                    u2.connected(f.getBlock());
                                    if (!u2.isConnected()) {
                                        f.setBurnTime(Short.valueOf("0"));
                                        f.update();
                                    }
                                });
                    }
                }
            }, 2);



        }
    }


}
