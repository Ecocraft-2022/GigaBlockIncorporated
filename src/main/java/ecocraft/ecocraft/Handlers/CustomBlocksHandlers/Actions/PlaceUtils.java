package ecocraft.ecocraft.Handlers.CustomBlocksHandlers.Actions;

import com.google.common.collect.Lists;
import ecocraft.ecocraft.CustomBlocks.Cable;
import ecocraft.ecocraft.CustomBlocks.CompareBlocks;
import ecocraft.ecocraft.CustomBlocks.SolarPanel;
import ecocraft.ecocraft.CustomBlocks.SolarPanelBase;

import ecocraft.ecocraft.Utils.NightDetector;
import ecocraft.ecocraft.Utils.Util;
import org.bukkit.*;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.event.block.BlockPlaceEvent;

import org.bukkit.plugin.Plugin;





public class PlaceUtils {


    public static void onSolarPanelPlaced(BlockPlaceEvent e,Plugin plugin) {

        addBlockData(e, SolarPanel.getInstance());

        if (!e.getBlockPlaced().getRelative(BlockFace.DOWN).getType().equals(Material.NOTE_BLOCK) ) {
            e.getPlayer().sendMessage("Solar panel will not produce power");
            return;
        }
        if(!Util.compareBlocks( SolarPanelBase.getInstance(), (NoteBlock) e.getBlockPlaced().getRelative(BlockFace.DOWN).getBlockData())) return;

        Util u = new Util();
//        u.findDesiredBlocks(e.getBlockPlaced().getRelative(BlockFace.DOWN));
        e.getPlayer().sendMessage("Solar panel is producing power");

        World world = e.getBlock().getWorld();
        Block highestBlock = world.getHighestBlockAt(e.getBlockPlaced().getX(),e.getBlockPlaced().getZ());
        if(Util.compareBlocks(SolarPanel.getInstance(),highestBlock)) {
            u.findFurnaces(e.getBlockPlaced().getRelative(BlockFace.DOWN)).stream().forEach(
                    f -> {
                        if (!NightDetector.getInstance(plugin).isNight()) {
                            f.setBurnTime(Short.MAX_VALUE);
                            f.update();
                        }
                    }
            );
        }
    }



    public static void onSolarPanelBasePlaced(BlockPlaceEvent e,Plugin plugin) {
        addBlockData(e, SolarPanelBase.getInstance());
    }

    public static void onCablePlaced(BlockPlaceEvent e,Plugin plugin) {
        addBlockData(e, Cable.getInstance());

        Util.activateFurnaces(e.getBlock(),plugin);
//        Util u = new Util();
//        Block block = e.getBlockPlaced();
//        u.connected(block);
//        if (u.isConnected() && !NightDetector.getInstance().isNight()) {
//            u.findDesiredBlocks(block);
//            u.getFurnaces().stream().forEach(
//                    f -> {
//                        f.setBurnTime(Short.MAX_VALUE);
//                        f.update();
//                    }
//            );
//        }
    }

    private static void addBlockData(BlockPlaceEvent e, CompareBlocks block) {
        NoteBlock nb = (NoteBlock) e.getBlockPlaced().getBlockData();
        nb.setInstrument(block.getInstrument());
        nb.setNote(block.getNote());


        e.getBlock().setBlockData(nb);
        e.getBlock().getState().update();
    }




}
