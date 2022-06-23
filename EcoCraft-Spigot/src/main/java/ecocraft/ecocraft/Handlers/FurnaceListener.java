package ecocraft.ecocraft.Handlers;


import org.bukkit.*;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;

public class FurnaceListener implements Listener {
    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent e) {
        if(e.getFuel().getType().equals(Material.COAL))
        {
            Directional direction = (Directional) e.getBlock().getBlockData();
            Location frontLocation = e.getBlock().getRelative(direction.getFacing()).getLocation();
            Location furnaceLocation = e.getBlock().getLocation();

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
                    while(true)
                    {
                        furnaceLocation.getWorld().spawnParticle(Particle.SMOKE_LARGE, particleLocation, 0, 0, 1, 0, 0.1);

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
