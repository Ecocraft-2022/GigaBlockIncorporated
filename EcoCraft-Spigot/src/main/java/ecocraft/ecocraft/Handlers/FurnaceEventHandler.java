package ecocraft.ecocraft.Handlers;

import ecocraft.ecocraft.Ecocraft;
import ecocraft.ecocraft.Utils.Util;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class FurnaceEventHandler implements Listener {

    public FurnaceEventHandler(Ecocraft plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void isConnectedToGrid(BlockPlaceEvent e){
        Util u = new Util();
        Block b = e.getBlockPlaced();
        if(b.getType().equals(Material.FURNACE)){
            u.connected(b);

            if(u.isConnected()){
               Furnace fur =  (Furnace) b.getState();
               fur.setBurnTime(Short.MAX_VALUE);
               fur.update();
            }
        }

    }

}
