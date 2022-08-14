package ecocraft.ecocraft.CustomBlocks;

import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Cable implements CompareBlocks  {

    private static Cable cableObj;
    public final static Note note = Note.natural(1, Note.Tone.G);
    public final static Instrument instrument = Instrument.GUITAR;
    private static ItemStack cable;

    public static Cable getInstance(){
        if(cableObj==null){
            cableObj = new Cable();
        }
        return cableObj;
    }

    protected Cable(){
        createCable();
        createRecep();
    }

    private static void createCable() {
        ItemStack item =  new ItemStack(Material.NOTE_BLOCK,1);

        ItemMeta meta  = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName("Cable");
        meta.setCustomModelData(88888);
        List<String> data = new ArrayList<>();
        data.add("Cable - Connect to solar panel base to transfer energy");
        meta.setLore(data);

        item.setItemMeta(meta);
        cable = item;

    }

    private static void createRecep(){
        NamespacedKey key = NamespacedKey.fromString("cable");
        assert key != null;

        ShapedRecipe sr = new ShapedRecipe(key, cable);
        sr.shape(
                "RRR",
                "DDD",
                "RRR");
        sr.setIngredient('R',Material.NETHER_BRICK_SLAB);
        sr.setIngredient('D',Material.COPPER_BLOCK);

        Bukkit.addRecipe(sr);
    }

    @Override
    public Instrument getInstrument() {
        return instrument;
    }

    @Override
    public Note getNote() {
        return note;
    }

    public ItemStack getItem() {
        return cable;
    }
}
