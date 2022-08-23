package ecocraft.ecocraft.Handlers;


import ecocraft.ecocraft.Pollution.Region;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
            Item item = (Item) event.getCaught();
            handleFish(item);
        }
    }
    @EventHandler
    public void eatDeadFishEvent(PlayerItemConsumeEvent e){
        ItemStack item = e.getItem();
        String name = item.getItemMeta().getDisplayName();
        Player player = e.getPlayer();
        if(name.contains("Dead")){
            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,50,2));
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,90,10));
            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,500,10));
        }
    }
    @EventHandler
    public void smeltDeadFishEvent(FurnaceSmeltEvent e){
       Furnace furnace = (Furnace) e.getBlock().getState();
       if(furnace.getInventory().getSmelting().getItemMeta().getLore().contains("Fish died because of pollution")){
           ItemStack result = new ItemStack(Material.DRIED_KELP);

           ItemMeta meta = result.getItemMeta();
           meta.setDisplayName("Cooked Dead Fish");

           List<String> lore = new ArrayList<>();

           lore.add("You really shouldn't eat it");

           meta.setLore(lore);

           result.setItemMeta(meta);

           e.setResult(result);
       }
    }


    private static void handleFish(Item item){

        ItemStack itemStack = item.getItemStack();

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName("Dead " + item.getName().replaceFirst("Raw ",""));

        List<String> lore = new ArrayList<>();

        lore.add("Fish died because of pollution");

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        item.setItemStack(itemStack);
    }

}