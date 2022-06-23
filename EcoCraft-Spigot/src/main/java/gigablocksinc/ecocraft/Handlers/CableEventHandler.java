package gigablocksinc.ecocraft.Handlers;

import gigablocksinc.ecocraft.CustomBlocks.Cable;
import gigablocksinc.ecocraft.Ecocraft;
import gigablocksinc.ecocraft.Utils.Util;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;


public class CableEventHandler implements Listener {

    Plugin plugin;

    public CableEventHandler(Ecocraft plugin) {
        this.plugin =plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCablePlaced(BlockPlaceEvent e) {
        if (e.getBlockPlaced().getType().equals(Cable.getCable().getType())) {
            Util u = new Util();
            Block block = e.getBlockPlaced();
            u.connected(block);
            if (u.isConnected()) {
                u.findDesiredBlocks(block);
                u.getFurnaces().stream().forEach(
                        f -> {
                            f.setBurnTime(Short.MAX_VALUE);
                            f.update();
                        }
                );
            }
        }
    }
    private static final BlockFace list[] ={BlockFace.DOWN,BlockFace.EAST,BlockFace.UP,BlockFace.NORTH,BlockFace.SOUTH,BlockFace.WEST};
    @EventHandler
    public void onCableDestroyed(BlockBreakEvent e){


        Block block = e.getBlock();
        if(block.getType().equals(Cable.getCable().getType())) {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    for(BlockFace b: list){
                        if(block.getRelative(b).getType().equals(Cable.getCable().getType())) {
                            Util u = new Util();
                            u.connected(block.getRelative(b));
                           if(!u.isConnected()){
                               u.findDesiredBlocks(block);
                           }
                            u.getFurnaces().stream().forEach(
                                    f ->{
                                        f.setBurnTime(Short.valueOf("0"));
                                        f.update();
                                    });

                        }
                    }
                }
            }, 1);



        }
    }

}
