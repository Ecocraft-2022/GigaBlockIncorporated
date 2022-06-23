package ecocraft.ecocraft.Handlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class DispenserListener implements Listener {
    private static final String configPath = "config.cfg";
    private Plugin plugin;

    public DispenserListener(Plugin plugin) {
        this.plugin =plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private static String getLocationString(Location location)
    {
        return  Double.toString(location.getX()) + " " +
                Double.toString(location.getY()) + " " +
                Double.toString(location.getZ());
    }
    private static void addLocationToConfig(Location location) throws IOException
    {
        String locationStr = getLocationString(location);
        BufferedWriter writer = new BufferedWriter(new FileWriter(configPath, true));
        writer.append(locationStr);
        writer.append("\n");
        writer.close();
    }

    private static boolean checkLocation(Location location) throws IOException
    {
        String locationStr = getLocationString(location);
        try {
            File cfg = new File(configPath);
            Scanner reader = new Scanner(cfg);
            while (reader.hasNextLine()) {
                if(reader.nextLine().equals(locationStr))
                {
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return false;
    }

    @EventHandler
    public void onPlaced(BlockPlaceEvent e)
    {
        if(Objects.requireNonNull(e.getItemInHand().getItemMeta()).getDisplayName().equals("Recycler")) {
            try {
                addLocationToConfig(e.getBlock().getLocation());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @EventHandler
    public void onDispensed(BlockDispenseEvent e) throws IOException {
        if(e.getBlock().getType() != Material.DISPENSER)
            return;

        Dispenser dispenser = (Dispenser) e.getBlock().getState(); // not working rn
        dispenser.getInventory().removeItem(e.getItem());
        dispenser.update();

        ItemStack itemStack = new ItemStack(e.getItem().getType());

        if(checkLocation(e.getBlock().getLocation()))
        {
            switch (e.getItem().getType())
            {
                case GLASS_BOTTLE:
                case EXPERIENCE_BOTTLE:
                case HONEY_BOTTLE:
                    itemStack = new ItemStack(Material.GLASS);
                    break;
                case DIAMOND_SWORD:
                    itemStack = new ItemStack(Material.DIAMOND);
                    break;
                case BOOK:
                    itemStack = new ItemStack(Material.PAPER, 64);
                    break;
            }

            e.setItem(itemStack);
        }
    }
}
