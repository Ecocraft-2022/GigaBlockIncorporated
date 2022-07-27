package ecocraft.ecocraft.CustomBlocks;

import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SolarPanel implements CompareBlocks {

    private static SolarPanel solarPanelObj;

    public static final Note note = Note.natural(1, Note.Tone.A);

    public static final Instrument instrument = Instrument.GUITAR;


    private static ItemStack solarPanel;

    public static SolarPanel getInstance() {
        if (solarPanelObj == null) {
            solarPanelObj = new SolarPanel();
        }
        return solarPanelObj;
    }

    protected SolarPanel() {
        createSolarPanel();
        createRecep();
    }

    public ItemStack getItem() {
        return solarPanel;
    }


    private static void createSolarPanel() {

        ItemStack item = new ItemStack(Material.NOTE_BLOCK, 1);

        ItemMeta meta = item.getItemMeta();


        meta.setDisplayName("Solar Panel");
        List<String> data = new ArrayList<>();
        data.add("Solar panel - produces green energy");


        meta.setLore(data);
        System.out.println(meta.getAsString());
        item.setItemMeta(meta);
        solarPanel = item;

    }

    private static void createRecep() {

        NamespacedKey key = NamespacedKey.fromString("solar_panel");

        ShapedRecipe sr = new ShapedRecipe(key, solarPanel);
        sr.shape(
                " R ",
                "RDR",
                " R ");
        sr.setIngredient('R', Material.REDSTONE_BLOCK);
        sr.setIngredient('D', Material.DAYLIGHT_DETECTOR);

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
}


