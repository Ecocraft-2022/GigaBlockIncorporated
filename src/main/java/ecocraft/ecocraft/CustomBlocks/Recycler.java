package ecocraft.ecocraft;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class RecyclerBlock {
    public static void register(Plugin plugin) {
        ItemStack item = new ItemStack(Material.DISPENSER);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Component.text("Recycler"));
        item.setItemMeta(itemMeta);
        NamespacedKey key = new NamespacedKey(plugin, "recycler");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("CCC","CBC","CDC");
        recipe.setIngredient('C', Material.COBBLESTONE);
        recipe.setIngredient('B', Material.BOW);
        recipe.setIngredient('D', Material.DIAMOND);
        Bukkit.addRecipe(recipe);
    }
}
