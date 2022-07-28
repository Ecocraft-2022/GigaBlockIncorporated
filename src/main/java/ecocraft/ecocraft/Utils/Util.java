package ecocraft.ecocraft.Utils;

import com.google.common.collect.Lists;
import ecocraft.ecocraft.CustomBlocks.Cable;
import ecocraft.ecocraft.CustomBlocks.CompareBlocks;
import ecocraft.ecocraft.CustomBlocks.SolarPanel;
import ecocraft.ecocraft.CustomBlocks.SolarPanelBase;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Furnace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.units.qual.A;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Util {
    private static final BlockFace list[] = {BlockFace.DOWN, BlockFace.EAST, BlockFace.UP, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST};

    public List<Furnace> findFurnaces(Block block) {
        return findDesiredBlocks(block, new ArrayList<>(), new ArrayList<>());
    }

    private List<Furnace> findDesiredBlocks(Block block, List<Furnace> result, List<String> visited) {
        for (BlockFace b : list) {
            Block bl = block.getRelative(b);
            BlockData data = bl.getBlockData();

            if (bl.getType().equals(Material.FURNACE)) {
                result.add((Furnace) bl.getState());
                String coordinates = cordsToString(bl);
                if (!visited.contains(coordinates)) {
                    visited.add(coordinates);
                    findDesiredBlocks(bl, result, visited);
                }
            }

            if (block.getRelative(b).getType().equals(Material.NOTE_BLOCK)) {
                NoteBlock noteblock = (NoteBlock) data;
                if (compareBlocks(SolarPanelBase.getInstance(), noteblock) || compareBlocks(Cable.getInstance(), noteblock)) {
                    String coordinates = cordsToString(bl);
                    if (!visited.contains(coordinates)) {
                        visited.add(coordinates);
                        findDesiredBlocks(bl, result, visited);
                    }
                }
            }
        }
        return result;
    }

    public List<Block> getSolarPanels(Block block) {

        ArrayList visited = new ArrayList();

        visited.add(cordsToString(block));

        return findCustomBlocks(block, SolarPanel.getInstance(), new ArrayList<>(),  visited);
    }


    private List<Block> findCustomBlocks(Block block, CompareBlocks base, List<Block> result, List<String> visited) {


        for (BlockFace b : list) {
            Block relativeBlock = block.getRelative(b);
            if (relativeBlock.getType().equals(Material.NOTE_BLOCK)) {


                BlockData noteBlockData = relativeBlock.getBlockData();

                NoteBlock noteBlock = (NoteBlock) noteBlockData;

                if (Util.compareBlocks(base, noteBlock)) {
                    result.add(relativeBlock);
                }

                String coordinates = cordsToString(relativeBlock);
                if (Util.compareBlocks(SolarPanel.getInstance(), noteBlock) || Util.compareBlocks(Cable.getInstance(), noteBlock) || Util.compareBlocks(SolarPanelBase.getInstance(), noteBlock)){

                    if (!visited.contains(coordinates)){

                    visited.add(coordinates);
                    findCustomBlocks(relativeBlock, base, result, visited);
                }
                }
            }
        }
        return result;
    }


    public Boolean isConnectedToGrid(Block block) {
        List<Boolean> result = new ArrayList<>();
        connected(block, new ArrayList<>(), result);
        return result.contains(true);
    }


    public void connected(Block block, List<String> visited, List<Boolean> res) {

        for (BlockFace b : list) {
            Block bl = block.getRelative(b);
            BlockData data = bl.getBlockData();

            if (bl.getType().equals(Material.NOTE_BLOCK)) {
                NoteBlock nb = (NoteBlock) data;


                if(bl.getRelative(BlockFace.UP).equals(Material.NOTE_BLOCK)){
                    NoteBlock relativeUP = (NoteBlock)bl.getRelative(BlockFace.UP);
                    res.add(compareBlocks(SolarPanelBase.getInstance(), nb) && compareBlocks(SolarPanel.getInstance(),relativeUP));
                }

                if (compareBlocks(Cable.getInstance(), nb)) {
                    String coordinates = cordsToString(bl);
                    if (!visited.contains(coordinates)) {
                        visited.add(coordinates);
                        connected(bl, visited, res);
                    }
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


    public static boolean compareBlocks(CompareBlocks block1, NoteBlock block2) {
        return block1.getNote().equals(block2.getNote()) && block1.getInstrument().equals(block2.getInstrument());
    }

    public static void deactivateFurnaces(Block block, Plugin plugin) {

        BlockData data = block.getBlockData();

        if (block.getType().equals(Material.NOTE_BLOCK) && Util.compareBlocks(Cable.getInstance(), (NoteBlock) data)) {

            Util util = new Util();

            List<Furnace> furnaces = util.findFurnaces(block);

            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> furnaces.stream().filter(f -> !util.isConnectedToGrid(f.getBlock())).forEach(furnace -> {
                        furnace.setBurnTime(Short.parseShort("0"));
                        furnace.update();
                    })
                    , 2);
        }
    }

    public static void activateFurnaces(Block block, Plugin plugin) {

        if (NightDetector.getInstance(plugin).isNight()) return;

        BlockData data = block.getBlockData();

        if (block.getType().equals(Material.NOTE_BLOCK) && Util.compareBlocks(Cable.getInstance(), (NoteBlock) data)) {

            Util util = new Util();

            List<Furnace> furnaces = util.findFurnaces(block);

            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> furnaces.stream().filter(f -> util.isConnectedToGrid(f.getBlock())).forEach(furnace -> {
                        furnace.setBurnTime(Short.MAX_VALUE);
                        furnace.update();
                    })
                    , 2);
        }

    }


}

