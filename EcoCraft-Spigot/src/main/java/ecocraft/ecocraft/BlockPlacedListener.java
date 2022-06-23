package ecocraft.ecocraft;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlacedListener implements Listener {
    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event)
    {
        event.getBlock().setType(Material.DIAMOND_BLOCK);
    }
}
