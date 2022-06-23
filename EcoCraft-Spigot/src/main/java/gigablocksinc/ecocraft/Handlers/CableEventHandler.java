package gigablocksinc.ecocraft.Handlers;

import gigablocksinc.ecocraft.CustomBlocks.Cable;
import gigablocksinc.ecocraft.Ecocraft;
import gigablocksinc.ecocraft.Utils.Util;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;


public class CableEventHandler implements Listener {

    public CableEventHandler(Ecocraft plugin) {
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
    @EventHandler
    public void onCableDestroyed(BlockBreakEvent e){
        Util u = new Util();

        Block block = e.getBlock();
        if(block.getType().equals(Cable.getCable().getType())) {
            u.connected(block);
            System.out.println(u.isConnected());
        }
    }

}
