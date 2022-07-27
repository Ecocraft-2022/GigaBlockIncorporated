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
import java.util.List;


public class Util {
    private static final BlockFace list[] = {BlockFace.DOWN, BlockFace.EAST, BlockFace.UP, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST};

    public List<Furnace> findFurnaces(Block block){
        return findDesiredBlocks(block,new ArrayList<>(), new ArrayList<>());
    }

    private List<Furnace> findDesiredBlocks(Block block,List<Furnace> result,List<String> visited) {
        for (BlockFace b : list) {
            Block bl = block.getRelative(b);
            BlockData data = bl.getBlockData();

            if (bl.getType().equals(Material.FURNACE)) {
                result.add((Furnace) bl.getState());
                String coordinates = cordsToString(bl);
                if (!visited.contains(coordinates)) {
                    visited.add(coordinates);
                    findDesiredBlocks(bl,result,visited);
                }
            }

            if (block.getRelative(b).getType().equals(Material.NOTE_BLOCK)) {
                NoteBlock noteblock = (NoteBlock) data;
                if (compareBlocks(SolarPanelBase.getInstance(), noteblock) || compareBlocks(Cable.getInstance(), noteblock)) {
                    String coordinates = cordsToString(bl);
                    if (!visited.contains(coordinates)) {
                        visited.add(coordinates);
                        findDesiredBlocks(bl,result,visited);
                    }
                }
            }
        }
        return result;
    }


    public Boolean isConnectedToGrid(Block block){
        List<String> visited = new ArrayList<>();
        List<Boolean> result = new ArrayList<>();
        Boolean res = connected(block,visited,result);

        return result.contains(true);
    }



    public Boolean connected(Block block,List<String> visited,List<Boolean> res) {

        for (BlockFace b : list) {
            Block bl = block.getRelative(b);
            BlockData data = bl.getBlockData();

            if (bl.getType().equals(Material.NOTE_BLOCK)) {
                NoteBlock nb = (NoteBlock) data;
                res.add(compareBlocks(SolarPanelBase.getInstance(), nb));
                if (compareBlocks(SolarPanelBase.getInstance(), nb)) {
                    return compareBlocks(SolarPanelBase.getInstance(),nb);
                }

                if (compareBlocks(Cable.getInstance(), nb)) {
                    String coordinates = cordsToString(bl);
                    if (!visited.contains(coordinates)) {
                        visited.add(coordinates);
                        connected(bl,visited,res);
                    }
                }

            }

        }

    return false;
    }

    private String cordsToString(Block bl) {
        StringBuilder str = new StringBuilder();
        str.append(bl.getX());
        str.append(bl.getY());
        str.append(bl.getZ());
        return str.toString();
    }




    public static boolean compareBlocks(CompareBlocks block1, NoteBlock block2) {
        return block1.getNote().equals(block2.getNote()) && block1.getInstrument().equals(block2.getInstrument());
    }


}

