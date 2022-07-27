package ecocraft.ecocraft.Handlers.CustomBlocksHandlers.Actions;

import com.google.common.collect.Lists;
import ecocraft.ecocraft.CustomBlocks.Cable;
import ecocraft.ecocraft.CustomBlocks.CompareBlocks;
import ecocraft.ecocraft.CustomBlocks.SolarPanel;
import ecocraft.ecocraft.CustomBlocks.SolarPanelBase;
import ecocraft.ecocraft.Ecocraft;
import ecocraft.ecocraft.Utils.NightDetector;
import ecocraft.ecocraft.Utils.Util;
import jdk.tools.jlink.plugin.Plugin;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;



public class PlaceUtils {


    public static void onSolarPanelPlaced(BlockPlaceEvent e) {

        addBlockData(e, SolarPanel.getInstance());

        if (!e.getBlockPlaced().getRelative(BlockFace.DOWN).getType().equals(Material.NOTE_BLOCK) ) {
            e.getPlayer().sendMessage("Solar panel will not produce power");
            return;
        }
        if(!Util.compareBlocks( SolarPanelBase.getInstance(), (NoteBlock) e.getBlockPlaced().getRelative(BlockFace.DOWN).getBlockData())) return;

        Util u = new Util();
//        u.findDesiredBlocks(e.getBlockPlaced().getRelative(BlockFace.DOWN));
        e.getPlayer().sendMessage("Solar panel is producing power");

        u.findFurnaces(e.getBlockPlaced().getRelative(BlockFace.DOWN)).stream().forEach(
                f -> {
                    f.setBurnTime(Short.MAX_VALUE);
                    f.update();
                }
        );

    }



    public static void onSolarPanelBasePlaced(BlockPlaceEvent e) {
        addBlockData(e, SolarPanelBase.getInstance());
    }

    public static void onCablePlaced(BlockPlaceEvent e) {

        addBlockData(e, Cable.getInstance());

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
