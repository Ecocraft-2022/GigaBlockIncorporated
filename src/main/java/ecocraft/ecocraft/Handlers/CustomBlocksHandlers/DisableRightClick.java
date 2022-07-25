package ecocraft.ecocraft.Handlers.CustomBlocksHandlers;

import ecocraft.ecocraft.CustomBlocks.SolarPanel;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class DisableRightClick implements Listener {

    private Plugin plugin;

    public DisableRightClick(Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void onPlayerClickItem(PlayerInteractEvent event) {
        if (event.getClickedBlock().getType() == Material.NOTE_BLOCK) {
            Player player = event.getPlayer();
            if ((!player.isSneaking() || player.getMainHand() == null) &&event.getAction()==Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);
            }

        }
    }
}
