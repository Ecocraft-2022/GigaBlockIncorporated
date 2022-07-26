package ecocraft.ecocraft.Utils;

import ecocraft.ecocraft.CustomBlocks.Cable;
import ecocraft.ecocraft.CustomBlocks.CompareBlocks;
import ecocraft.ecocraft.CustomBlocks.SolarPanel;
import ecocraft.ecocraft.CustomBlocks.SolarPanelBase;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Furnace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.NoteBlock;


import java.util.ArrayList;


public class Util {
    private boolean connected = false;

    public boolean isConnected() {
        return connected;
    }

    private static final BlockFace list[] = {BlockFace.DOWN, BlockFace.EAST, BlockFace.UP, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST};
    private ArrayList<String> visited = new ArrayList<>();
    private ArrayList<String> visited2 = new ArrayList<>();
    private ArrayList<Furnace> furnaces = new ArrayList<>();

    public ArrayList<Furnace> getFurnaces() {
        return furnaces;
    }

    public void findDesiredBlocks(Block block) {


        for (BlockFace b : list) {

            Block bl = block.getRelative(b);
            BlockData data = bl.getBlockData();

            if (bl.getType().equals(Material.FURNACE)) {
                furnaces.add((Furnace) bl.getState());
                addVisited(bl);
            }

            if (block.getRelative(b).getType().equals(Material.NOTE_BLOCK)) {
                NoteBlock noteblock = (NoteBlock) data;
                if (compareBlocks(new SolarPanelBase(), noteblock) || compareBlocks(new Cable(), noteblock)) {
                    addVisited(bl);
                }

            }

        }

    }

    private void addVisited(Block bl) {
        StringBuilder str = new StringBuilder();
        str.append(bl.getX());
        str.append(bl.getY());
        str.append(bl.getZ());
        System.out.println(visited);
        if (!visited.contains(str.toString())) {
            visited.add(str.toString());
            findDesiredBlocks(bl);
        }
    }


    public void connected(Block block) {

        for (BlockFace b : list) {
            Block bl = block.getRelative(b);


            if (bl.getType().equals(Material.FURNACE)) getCordinatesString(bl);

            BlockData data = bl.getBlockData();

            if (bl.getType().equals(Material.NOTE_BLOCK)) {

                NoteBlock nb = (NoteBlock) data;

                if (compareBlocks(new SolarPanel(), nb) || compareBlocks(new Cable(), nb)) {
                    if (compareBlocks(new SolarPanelBase(), nb)) {
                        connected = true;
                        break;
                    }
                    getCordinatesString(bl);
                }
            }
        }
    }

    private void getCordinatesString(Block bl) {
        StringBuilder str = new StringBuilder();
        str.append(bl.getX());
        str.append(bl.getY());
        str.append(bl.getZ());

        if (!visited2.contains(str.toString())) {
            visited2.add(str.toString());
            connected(bl);
        }
    }


    public static boolean compareBlocks(CompareBlocks block1, NoteBlock block2) {
        return block1.getNote().equals(block2.getNote()) && block1.getInstrument().equals(block2.getInstrument());
    }


}

