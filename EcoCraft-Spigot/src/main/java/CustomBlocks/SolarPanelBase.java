package CustomBlocks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SolarPanelBase {

    private static ItemStack solarPanelBase;

    public static ItemStack getSolarPanel() {
        return solarPanelBase;
    }

    public static void init() {
        createSolarPanelBase();
        createRecep();
    }


    private static void createSolarPanelBase() {
        ItemStack item =  new ItemStack(Material.ANDESITE,1);
        ItemMeta meta  = item.getItemMeta();
        meta.setDisplayName("Solar Panel Base");
        List<String> data = new ArrayList<>();
        data.add("Solar Panel Base");
        meta.setLore(data);

        item.setItemMeta(meta);
        solarPanelBase = item;
    }
    private static void createRecep() {
        NamespacedKey key = NamespacedKey.fromString("solar_panel_base");

        ShapedRecipe sr =new ShapedRecipe(key,solarPanelBase);
        sr.shape(
                " R ",
                " D ",
                " R ");
        sr.setIngredient('R',Material.REDSTONE_BLOCK);
        sr.setIngredient('D',Material.ANDESITE);

        Bukkit.addRecipe(sr);
    }


}
