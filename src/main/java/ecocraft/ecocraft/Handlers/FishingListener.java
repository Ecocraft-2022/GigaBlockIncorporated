package ecocraft.ecocraft.Handlers;


import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

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
        if (event.getCaught() == null)
            return;

        Player player = event.getPlayer();
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