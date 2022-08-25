package ecocraft.ecocraft.Handlers.CustomBlocksHandlers;

import ecocraft.ecocraft.CustomBlocks.SolarPanel;
import ecocraft.ecocraft.Handlers.CustomBlocksHandlers.Actions.PlaceUtils;
import ecocraft.ecocraft.Utils.Util;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;


public class MainBlockHandler implements Listener {

    private Plugin plugin;

    private FunctionalInterface function;

    public MainBlockHandler(Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void onBlockPlacedAboveSolarPanel(BlockPlaceEvent e) {

        if (!Util.compareBlocks(SolarPanel.getInstance(), e.getBlockPlaced().getRelative(BlockFace.DOWN))) return;

        Util util = new Util();

        List<Block> solarPanels = util.getSolarPanels(e.getBlockPlaced().getRelative(BlockFace.DOWN));

        long count = solarPanels.stream().filter((panel) -> {
            World w = panel.getWorld();
            Block highest = w.getHighestBlockAt(panel.getLocation());
            return highest.equals(panel);
        }).count();


        if (count == 0) {

            util.findFurnaces(e.getBlockPlaced().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN)).forEach((furnace -> {
                furnace.setBurnTime(Short.valueOf("0"));
                furnace.update();
            }));
        }
    }

    @EventHandler
    public void placeCustomBlock(PlayerInteractEvent e) {
        if (e.getItem() == null) return;
        if (!e.getItem().getType().equals(Material.NOTE_BLOCK)) return;
        if (!e.getItem().getItemMeta().hasDisplayName()) return;
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
            function.handle(e, plugin);
        }
        function = null;
    }

}
