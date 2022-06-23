package ecocraft.ecocraft;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class FishingListener implements Listener {
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
        ArrayList<Component> lore = new ArrayList<Component>();
        lore.add(Component.text("Padlina z zanieczyszczonego jeziora"));
        itemMeta.lore(lore);
        itemStack.setItemMeta(itemMeta);
        item.setItemStack(itemStack);
    }
}