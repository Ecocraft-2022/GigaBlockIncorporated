package ecocraft.ecocraft.CustomItems;

import ecocraft.ecocraft.CustomBlocks.Cable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Mask {

    private static Mask maskObj;

    private static ItemStack mask;

    public static Mask getInstance(){
        if(maskObj==null){
            maskObj = new Mask();
        }
        return maskObj;
    }

    protected Mask(){
        createMask();
        createMaskRecep();
    }

    private static void createMask(){
        ItemStack item = new ItemStack(Material.LEATHER_HELMET,1);
        ItemMeta meta  = item.getItemMeta();
        meta.setDisplayName("Mask");
        meta.setCustomModelData(999999);
        List<String> data = new ArrayList<>();
        data.add("Mask - protect from heavy pollution");
        meta.setLore(data);
        item.setItemMeta(meta);
        mask = item;
    }

    private static void createMaskRecep(){
        NamespacedKey key = NamespacedKey.fromString("mask");
        ShapedRecipe sr = new ShapedRecipe(key, mask);

        sr.shape(
                " p ",
                "sws",
                " p ");
        sr.setIngredient('s',Material.STRING);
        sr.setIngredient('w',Material.WHITE_WOOL);
        sr.setIngredient('p',Material.PAPER);
        Bukkit.addRecipe(sr);
    }

    public ItemStack getItem() {
        return mask;
    }
}
