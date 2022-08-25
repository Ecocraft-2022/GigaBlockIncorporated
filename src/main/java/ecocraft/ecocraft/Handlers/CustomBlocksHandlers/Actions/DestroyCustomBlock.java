package ecocraft.ecocraft.Handlers.CustomBlocksHandlers.Actions;

import ecocraft.ecocraft.CustomBlocks.Cable;
import ecocraft.ecocraft.CustomBlocks.CompareBlocks;
import ecocraft.ecocraft.CustomBlocks.SolarPanel;
import ecocraft.ecocraft.CustomBlocks.SolarPanelBase;
import ecocraft.ecocraft.Utils.Util;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class DestroyCustomBlock implements Listener {

    private Plugin plugin;

    public DestroyCustomBlock(Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
@EventHandler
    public void blockEvent(BlockPhysicsEvent e){
        Block block = e.getBlock();
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            List visited = new ArrayList();
            prevent(block,visited);
        },1);
    }

    @EventHandler
    public void onCustomBlockDestroyed(BlockBreakEvent e){

        preventCustomBlockUpdateUpdate(e.getBlock());


        if(e.getBlock().getType().equals(Material.NOTE_BLOCK)){

            NoteBlock noteblock = (NoteBlock) e.getBlock().getBlockData();
            World world =  e.getPlayer().getWorld();

            if (Util.compareBlocks(SolarPanel.getInstance(), noteblock)) {
                e.setDropItems(false);
                world.dropItem( e.getBlock().getLocation(), SolarPanel.getInstance().getItem());
            }

            if (Util.compareBlocks(SolarPanelBase.getInstance(), noteblock)) {
                e.setDropItems(false);
                world.dropItem( e.getBlock().getLocation(), SolarPanelBase.getInstance().getItem());

            }
            if (Util.compareBlocks(Cable.getInstance(), noteblock)) {
                e.setDropItems(false);
                world.dropItem( e.getBlock().getLocation(), Cable.getInstance().getItem());
            }     e.getBlock().getState().update();
        }
    }

    @EventHandler
    public void onCustomBlockPlaced(BlockPlaceEvent e){
        preventCustomBlockUpdateUpdate(e.getBlock());
    }


    private void preventCustomBlockUpdateUpdate(Block block){
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            List visited = new ArrayList();
            prevent(block,visited);
        },1);
    }

    private void prevent(Block block, List<String> visited){
        for(BlockFace face: Util.list){
            Block relative = block.getRelative(face);
            if(relative.getType().equals(Material.NOTE_BLOCK)){
                String coordinates = cordsToString(relative);
                if (!visited.contains(coordinates)) {
                    NoteBlock nb =(NoteBlock) relative.getBlockData();
                    nb.setInstrument(Instrument.GUITAR);
                    relative.setBlockData(nb);
                    relative.getState().update();
                    visited.add(coordinates);
                    prevent(relative, visited);
                }
            }
        }
    }
    private String cordsToString(Block bl) {
        StringBuilder str = new StringBuilder();
        str.append(bl.getX());
        str.append(bl.getY());
        str.append(bl.getZ());
        return str.toString();
    }
}
