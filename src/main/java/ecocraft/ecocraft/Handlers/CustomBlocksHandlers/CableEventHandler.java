package ecocraft.ecocraft.Handlers.CustomBlocksHandlers;

import ecocraft.ecocraft.Ecocraft;
import ecocraft.ecocraft.Utils.NightDetector;
import ecocraft.ecocraft.Utils.Util;
import ecocraft.ecocraft.CustomBlocks.Cable;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;


public class CableEventHandler implements Listener {

    static Plugin plugin;

    public CableEventHandler(Plugin plugin) {
        this.plugin =plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    public static void onCablePlaced(BlockPlaceEvent e) {

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

    private static final BlockFace list[] ={BlockFace.DOWN,BlockFace.EAST,BlockFace.UP,BlockFace.NORTH,BlockFace.SOUTH,BlockFace.WEST};
    @EventHandler
    public void onCableDestroyed(BlockBreakEvent e){


        Block block = e.getBlock();
        if(block.getType().equals(Cable.getCable().getType())) {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    for(BlockFace b: list){
                        if(block.getRelative(b).getType().equals(Cable.getCable().getType()) || block.getRelative(b).getType().equals(Material.FURNACE)) {
                            Util u = new Util();
                            u.connected(block.getRelative(b));
                            System.out.println(u.isConnected());
                           if(!u.isConnected()){
                               u.findDesiredBlocks(block);
                           }
                            u.getFurnaces().stream().forEach(
                                    f ->{
                                        Util u2 = new Util();
                                        u2.connected(f.getBlock());
                                        if(!u2.isConnected()) {
                                            f.setBurnTime(Short.valueOf("0"));
                                            f.update();
                                        }
                                    });

                        }
                    }
                }
            }, 2);



        }
    }

}
