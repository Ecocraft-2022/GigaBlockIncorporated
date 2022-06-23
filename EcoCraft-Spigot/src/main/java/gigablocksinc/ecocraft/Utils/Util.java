package gigablocksinc.ecocraft.Utils;

import gigablocksinc.ecocraft.CustomBlocks.Cable;
import gigablocksinc.ecocraft.CustomBlocks.SolarPanel;
import gigablocksinc.ecocraft.CustomBlocks.SolarPanelBase;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Furnace;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Util {

    private static final BlockFace list[] ={BlockFace.DOWN,BlockFace.EAST,BlockFace.UP,BlockFace.NORTH,BlockFace.SOUTH,BlockFace.WEST};
    private ArrayList<String> visited = new ArrayList<>();

    private ArrayList<Furnace> furnaces = new ArrayList<>();

    public ArrayList<Furnace> getFurnaces() {
        return furnaces;
    }

    public void findDesiredBlocks(Block block){



        for (BlockFace b : list){
            Block bl = block.getRelative(b);



            if(bl.getType().equals(SolarPanelBase.getSolarPanel().getType()) || bl.getType().equals(Cable.getCable().getType()) || bl.getType().equals(Material.FURNACE)){

                if( bl.getType().equals(Material.FURNACE)){
                    furnaces.add((Furnace) bl.getState());
                }

                StringBuilder str = new StringBuilder();
                str.append(bl.getX());
                str.append(bl.getY());
                str.append(bl.getZ());
                    if(!visited.contains(str.toString())) {
                        System.out.println(visited.size());
                        System.out.println(str.toString());
                        visited.add(str.toString());
                        findDesiredBlocks(bl);
                    }

            }

        }

    }
}
