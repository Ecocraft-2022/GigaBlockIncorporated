package ecocraft.ecocraft.Handlers.CustomBlocksHandlers;

import ecocraft.ecocraft.CustomBlocks.SolarPanel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;


public class MainBlockHandler implements Listener {

    private Plugin plugin;

    private FunctionalInterface function;

    public MainBlockHandler(Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void placeCustomBlock(PlayerInteractEvent e) {
        if(e.getItem() == null) return;
        switch (e.getItem().getItemMeta().getDisplayName()) {
            case ("Solar Panel"):
                function = SolarPanelHandler::onSolarPanelPlaced;
                break;
            case ("Solar Panel Base"):
                function = SolarPanelHandler::onSolarPanelBasePlaced;
                break;
            case ("Cable"):
                function = CableEventHandler::onCablePlaced;
        }

    }

    @EventHandler
    public void handle(BlockPlaceEvent e) {
        function.handle(e);
    }

}
