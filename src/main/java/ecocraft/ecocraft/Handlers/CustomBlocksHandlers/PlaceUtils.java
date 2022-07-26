package ecocraft.ecocraft.Handlers.CustomBlocksHandlers;

import ecocraft.ecocraft.CustomBlocks.Cable;
import ecocraft.ecocraft.CustomBlocks.SolarPanel;
import ecocraft.ecocraft.CustomBlocks.SolarPanelBase;
import ecocraft.ecocraft.Utils.NightDetector;
import ecocraft.ecocraft.Utils.Util;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.event.block.BlockPlaceEvent;

import static ecocraft.ecocraft.Handlers.CustomBlocksHandlers.CableEventHandler.plugin;

public class PlaceUtils {


    public static void onSolarPanelPlaced(BlockPlaceEvent e) {

        addBlockData(e, SolarPanel.note, SolarPanel.instrument);

        if (!e.getBlockPlaced().getRelative(BlockFace.DOWN).getType().equals(Material.NOTE_BLOCK) ) {
            e.getPlayer().sendMessage("Solar panel will not produce power");
            return;
        }
        if(!Util.compareBlocks(new SolarPanelBase(), (NoteBlock) e.getBlockPlaced().getRelative(BlockFace.DOWN).getBlockData())) return;

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



    public static void onSolarPanelBasePlaced(BlockPlaceEvent e) {
        addBlockData(e, SolarPanelBase.note,SolarPanelBase.instrument);
    }

    public static void onCablePlaced(BlockPlaceEvent e) {

        addBlockData(e, Cable.note,Cable.instrument);

        Util u = new Util();

        Block block = e.getBlockPlaced();
        u.connected(block);


        if (u.isConnected() && !NightDetector.getInstance(plugin).isNight()) {
            u.findDesiredBlocks(block);
            u.getFurnaces().stream().forEach(
                    f -> {
                        f.setBurnTime(Short.MAX_VALUE);
                        f.update();
                    }
            );
        }
    }

    private static void addBlockData(BlockPlaceEvent e, Note note,Instrument instrument) {
        NoteBlock nb = (NoteBlock) e.getBlockPlaced().getBlockData();
        nb.setInstrument(instrument);
        nb.setNote(note);
        e.getBlock().setBlockData(nb);
        e.getBlock().getState().update();
    }


}
