package ecocraft.ecocraft.CustomBlocks;

import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.inventory.ItemStack;

public interface CompareBlocks {

    Instrument getInstrument();

    Note getNote();

    ItemStack getItem();
}
