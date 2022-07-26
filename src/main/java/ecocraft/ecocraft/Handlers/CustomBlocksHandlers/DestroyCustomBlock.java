package ecocraft.ecocraft.Handlers.CustomBlocksHandlers;

import ecocraft.ecocraft.CustomBlocks.Cable;
import ecocraft.ecocraft.CustomBlocks.SolarPanel;
import ecocraft.ecocraft.CustomBlocks.SolarPanelBase;
import ecocraft.ecocraft.Utils.Util;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

public class DestroyCustomBlock implements Listener {

    private Plugin plugin;

    public DestroyCustomBlock(Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onCustomBlockDestroyed(BlockBreakEvent e){
        if(e.getBlock().getType().equals(Material.NOTE_BLOCK)){
            NoteBlock noteblock = (NoteBlock) e.getBlock().getBlockData();
            World world =  e.getPlayer().getWorld();

            if (Util.compareBlocks(SolarPanel.getInstance(), noteblock)) {
                e.setDropItems(false);
                world.dropItem( e.getBlock().getLocation(), SolarPanel.getItem());

            }
            if (Util.compareBlocks(SolarPanelBase.getInstance(), noteblock)) {
                e.setDropItems(false);
                world.dropItem( e.getBlock().getLocation(), SolarPanelBase.getItem());

            }
            if (Util.compareBlocks(Cable.getInstance(), noteblock)) {
                e.setDropItems(false);
                world.dropItem( e.getBlock().getLocation(), Cable.getItem());
            }
        }
    }

}
