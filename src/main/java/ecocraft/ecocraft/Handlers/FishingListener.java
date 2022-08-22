package ecocraft.ecocraft.Handlers;


import ecocraft.ecocraft.Pollution.Region;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FishingListener implements Listener {

    Plugin plugin;

    public FishingListener(Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onFishCaught(PlayerFishEvent event) {
        Integer mediumPollution = plugin.getConfig().getInt("mediumPollutionEnd");


        if (event.getCaught() == null)
            return;

        Player player = event.getPlayer();
        Region region;
        try {
           region=  Region.getPlayerRegion(player.getLocation().getBlockX(),player.getLocation().getBlockZ());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Integer overallPollution = region.getLocalPollution() + region.getPollutionLevel();
        if(overallPollution>mediumPollution) {
            player.sendMessage(String.valueOf(event.getCaught().getType()));
            Item item = (Item) event.getCaught();
            ItemStack itemStack = item.getItemStack();
            itemStack.setType(Material.ROTTEN_FLESH);
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> lore = new ArrayList<>();
            lore.add("Padlina z zanieczyszczonego jeziora");
            itemMeta.setLore(lore);

            itemStack.setItemMeta(itemMeta);
            item.setItemStack(itemStack);
        }
    }
}