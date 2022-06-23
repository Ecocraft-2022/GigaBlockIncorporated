package ecocraft.ecocraft.CustomBlocks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SolarPanel {

    private static ItemStack solarPanel;

    public static ItemStack getSolarPanel() {
        return solarPanel;
    }

    public static void init() {
        createSolarPanel();
        createRecep();
    }

    private static void createSolarPanel() {

        ItemStack item =  new ItemStack(Material.DAYLIGHT_DETECTOR,1);

        ItemMeta meta  = item.getItemMeta();
        meta.setDisplayName("Solar Panel");
        List<String> data = new ArrayList<>();
        data.add("Solar panel - produces green energy");
        meta.setLore(data);
        System.out.println(meta.getAsString());
        item.setItemMeta(meta);
        solarPanel = item;

    }

    private static void createRecep(){

        NamespacedKey key = NamespacedKey.fromString("solar_panel");

        ShapedRecipe sr =new ShapedRecipe(key,solarPanel);
        sr.shape(
                " R ",
                "RDR",
                " R ");
        sr.setIngredient('R',Material.REDSTONE_BLOCK);
        sr.setIngredient('D',Material.DAYLIGHT_DETECTOR);

        Bukkit.addRecipe(sr);
    }
}


