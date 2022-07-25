package ecocraft.ecocraft.Handlers.CustomBlocksHandlers;

import ecocraft.ecocraft.CustomBlocks.SolarPanelBase;
import ecocraft.ecocraft.Utils.Util;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.event.block.BlockPlaceEvent;

public class SolarPanelHandler {


    public static void onSolarPanelPlaced(BlockPlaceEvent e) {

        NoteBlock nb = (NoteBlock) e.getBlockPlaced().getBlockData();
        nb.setInstrument(Instrument.GUITAR);

        nb.setNote(Note.natural(1, Note.Tone.A));
        e.getBlock().setBlockData(nb);
        e.getBlock().getState().update();


        if (e.getBlockPlaced().getRelative(BlockFace.DOWN).getType() != SolarPanelBase.getSolarPanel().getType()) {
            e.getPlayer().sendMessage("Solar panel will not produce power");
            return;
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


    public static void onSolarPanelBasePlaced(BlockPlaceEvent e) {
        System.out.println("test");
        NoteBlock nb = (NoteBlock) e.getBlockPlaced().getBlockData();
        nb.setInstrument(Instrument.GUITAR);
        nb.setNote(Note.natural(1, Note.Tone.E));

        e.getBlock().setBlockData(nb);
        e.getBlock().getState().update();


    }

}
