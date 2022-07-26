package ecocraft.ecocraft.Handlers.CustomBlocksHandlers;

import org.bukkit.Material;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
        if (e.getItem() == null) return;
        if (!e.getItem().getType().equals(Material.NOTE_BLOCK)) return;
        switch (e.getItem().getItemMeta().getDisplayName()) {
            case ("Solar Panel"):
                function = PlaceUtils::onSolarPanelPlaced;
                break;
            case ("Solar Panel Base"):
                function = PlaceUtils::onSolarPanelBasePlaced;
                break;
            case ("Cable"):
                function = PlaceUtils::onCablePlaced;
                break;
        }

    }

    @EventHandler
    public void handle(BlockPlaceEvent e) {
        if (function != null) {
            function.handle(e);
        }
        function = null;
    }

}
