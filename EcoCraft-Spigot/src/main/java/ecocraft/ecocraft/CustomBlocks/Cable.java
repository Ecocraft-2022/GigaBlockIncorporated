package ecocraft.ecocraft.CustomBlocks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Cable {

    private static ItemStack cable;

    public static ItemStack getCable() {
        return cable;
    }

    public static void init() {
        createCable();
        createRecep();
    }

    private static void createCable() {

        ItemStack item =  new ItemStack(Material.NETHER_BRICK_FENCE,1);

        ItemMeta meta  = item.getItemMeta();
        meta.setDisplayName("Cable");
        List<String> data = new ArrayList<>();
        data.add("Cable - Connect to energy source");
        meta.setLore(data);
        System.out.println(meta.getAsString());
        item.setItemMeta(meta);
        cable = item;

    }

    private static void createRecep(){

        NamespacedKey key = NamespacedKey.fromString("cable");

        ShapedRecipe sr =new ShapedRecipe(key,cable);
        sr.shape(
                " R ",
                " D ",
                " R ");
        sr.setIngredient('R',Material.REDSTONE_BLOCK);
        sr.setIngredient('D',Material.NETHER_BRICK_FENCE);

        Bukkit.addRecipe(sr);
    }

}
