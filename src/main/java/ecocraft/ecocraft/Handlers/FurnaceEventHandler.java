package ecocraft.ecocraft.Handlers;

import ecocraft.ecocraft.Ecocraft;
import ecocraft.ecocraft.Utils.NightDetector;
import ecocraft.ecocraft.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.plugin.Plugin;

import java.awt.*;

public class FurnaceEventHandler implements Listener {
    private Plugin plugin;
    public FurnaceEventHandler(Plugin plugin) {
        this.plugin =plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void isConnectedToGrid(BlockPlaceEvent e){
        Util u = new Util();
        Block b = e.getBlockPlaced();
        if(b.getType().equals(Material.FURNACE)){
            u.connected(b);

            if(u.isConnected() &&!NightDetector.getInstance(plugin).isNight()){
               Furnace fur =  (Furnace) b.getState();
               fur.setBurnTime(Short.MAX_VALUE);
               fur.update();
            }
        }

    }

    public static Location getCenterLocation(Location loc){
        int x = loc.getBlockX();
        int z = loc.getBlockZ();
        return new Location(loc.getWorld(), x > 0 ? x + 0.5 : x - 0.5, loc.getY(), z > 0 ? z + 0.5 : z - 0.5, loc.getYaw(), loc.getPitch());
    }

    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent e) {
        if(e.getFuel().getType().equals(Material.COAL)) {
            Directional direction = (Directional) e.getBlock().getBlockData();
            Location frontLocation = getCenterLocation(e.getBlock().getRelative(direction.getFacing()).getLocation());
            Location furnaceLocation = getCenterLocation(e.getBlock().getLocation());

            double x = frontLocation.getX() + furnaceLocation.getX();
            double y = frontLocation.getY() + furnaceLocation.getY();
            double z = frontLocation.getZ() + furnaceLocation.getZ();

            x /= 2;
            y /= 2;
            z /= 2;

            Location particleLocation = new Location(e.getBlock().getLocation().getWorld(), x, y, z);

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        furnaceLocation.getWorld().spawnParticle(Particle.SMOKE_LARGE, particleLocation, 5, 0, 1, 0, 0.1);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            };

            Thread t = new Thread(runnable);
            t.start();
        }
    }

}
