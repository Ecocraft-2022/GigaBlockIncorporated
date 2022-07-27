package ecocraft.ecocraft.Handlers.CustomBlocksHandlers;

import ecocraft.ecocraft.CustomBlocks.Cable;
import ecocraft.ecocraft.Utils.Util;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Furnace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.stream.Collectors;


public class CableEventHandler implements Listener {

    static Plugin plugin;

    public CableEventHandler(Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private final BlockFace list[] = {BlockFace.DOWN, BlockFace.EAST, BlockFace.UP, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST};

    @EventHandler
    public void onCableDestroyed(BlockBreakEvent e) {


        Block block = e.getBlock();

        BlockData data = block.getBlockData();


        if (block.getType().equals(Material.NOTE_BLOCK) && Util.compareBlocks(Cable.getInstance(), (NoteBlock) data)) {




            Util util = new Util();

            List<Furnace> furnaces = util.findFurnaces(block);

            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        furnaces.stream().filter(f -> !util.isConnectedToGrid(f.getBlock())).forEach(furnace -> {
                            furnace.setBurnTime(Short.valueOf("0"));
                            furnace.update();
                        });
                    }
            , 2);
        }
    }


}
