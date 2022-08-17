package ecocraft.ecocraft.CustomBlocks;

import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SolarPanelBase  implements CompareBlocks {

    private static SolarPanelBase solarPanelBaseObj;

    public static final Note note = Note.natural(1, Note.Tone.E);

    public static final Instrument instrument = Instrument.GUITAR;

    private static ItemStack solarPanelBase;


    protected SolarPanelBase(){
        createSolarPanelBase();
        createRecep();
    }

    public static SolarPanelBase getInstance(){
        if(solarPanelBaseObj == null){
            solarPanelBaseObj = new SolarPanelBase();
        }
        return solarPanelBaseObj;
    }

    private static void createSolarPanelBase() {
        ItemStack item =  new ItemStack(Material.NOTE_BLOCK,1);
        ItemMeta meta  = item.getItemMeta();
        meta.setDisplayName("Solar Panel Base");
        List<String> data = new ArrayList<>();
        data.add("Solar Panel Base -  necessary for the solar panel to work. place under solar panel and connect with cable.");
        meta.setLore(data);
        meta.setCustomModelData(69420);
        item.setItemMeta(meta);
        solarPanelBase = item;
    }
    private static void createRecep() {
        NamespacedKey key = NamespacedKey.fromString("solar_panel_base");

        ShapedRecipe sr =new ShapedRecipe(key,solarPanelBase);
        sr.shape(
                "RRR",
                "DDD",
                "RRR");
        sr.setIngredient('R',Material.COPPER_BLOCK);
        sr.setIngredient('D',Material.REDSTONE_BLOCK);

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
        return solarPanelBase;
    }

}
