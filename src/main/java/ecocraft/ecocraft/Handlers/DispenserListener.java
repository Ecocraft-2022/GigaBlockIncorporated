package ecocraft.ecocraft;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.Scanner;

public class DispenserListener implements Listener {
    private static String configPath = "config.cfg";

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
        Component recyclerName = Component.text("Recycler");
        Bukkit.broadcast(Component.text(e.getItemInHand().getItemMeta().displayName().toString()));
        if(e.getItemInHand().getItemMeta().displayName().equals(recyclerName)) {
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
