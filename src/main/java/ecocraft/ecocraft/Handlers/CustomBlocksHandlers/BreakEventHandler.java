package ecocraft.ecocraft.Handlers.CustomBlocksHandlers;

import ecocraft.ecocraft.CustomBlocks.SolarPanel;
import ecocraft.ecocraft.CustomBlocks.SolarPanelBase;
import ecocraft.ecocraft.Utils.Util;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Furnace;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;


public class BreakEventHandler implements Listener {

    static Plugin plugin;

    public BreakEventHandler(Plugin plugin) {
        BreakEventHandler.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCableDestroyed(BlockBreakEvent e) {
        Util.deactivateFurnaces(e.getBlock(), plugin);
    }



    @EventHandler
    public void onSolarPanelDestroyed(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (block.getType().equals(Material.NOTE_BLOCK)) {
//             NoteBlock noteBlock = (NoteBlock) block.getBlockData();
            Util utils = new Util();

            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,()->{



               Long number =  utils.getSolarPanels(block).stream().filter(l -> {
                    Block below = l.getRelative(BlockFace.DOWN);
                    return  (below.getType().equals(Material.NOTE_BLOCK) && Util.compareBlocks(SolarPanelBase.getInstance(), (NoteBlock) l.getRelative(BlockFace.DOWN).getBlockData()));
                }).count();


               if(number==0) {

                   List<Furnace> furnaces = utils.findFurnaces(block);

                   furnaces.stream().forEach(furnace -> {
                       furnace.setBurnTime(Short.parseShort("0"));
                       furnace.update();
                   });
               }
            },2);

        }
    }

}
