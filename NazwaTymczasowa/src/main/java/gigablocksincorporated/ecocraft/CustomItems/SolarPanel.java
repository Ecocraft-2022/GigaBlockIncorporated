package gigablocksincorporated.ecocraft.CustomItems;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilderApplicable;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SolarPanel  {
private static ItemStack itemStack;

public static void init(){
    createSolarPanel();
    craftSolarPanel();
}

private static void createSolarPanel(){
    ItemStack item = new ItemStack(Material.DAYLIGHT_DETECTOR);

    ItemMeta meta = item.getItemMeta();



        Component comp = Component.translatable("Test").color(TextColor.color(123)).asComponent();

        meta.displayName(comp);

        System.out.println(item.getItemMeta().getAsString());
//        meta.lore(Collections.singletonList(Component.translatable("Solar panel to create clear energy")));

    itemStack = item;
}

private static void craftSolarPanel(){
    ShapedRecipe sr = new ShapedRecipe(NamespacedKey.fromString("solar_panel"),itemStack);

    sr.shape(" R ","RDR"," R ");
    sr.setIngredient('R',Material.REDSTONE_BLOCK);
    sr.setIngredient('D',Material.DAYLIGHT_DETECTOR);

    Bukkit.getServer().addRecipe(sr);

}


    public static ItemStack getItemStack() {
        return itemStack;
    }
}
