package ecocraft.ecocraft.Timers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.Random;

public class HeatTimer extends BukkitRunnable {
    @Override
    public void run() {
        long time = Objects.requireNonNull(Bukkit.getWorld("world")).getTime();
        if(time > 500 && time < 11000){
            for(Player p : Bukkit.getOnlinePlayers()){
                Location playerLocation = p.getLocation();
                Material blockMaterial = p.getWorld().getBlockAt(
                        playerLocation.getBlockX(),
                        playerLocation.getBlockY() - 1,
                        playerLocation.getBlockZ()
                ).getBlockData().getMaterial();

                if(blockMaterial == Material.GRAY_WOOL){
                    int ft = p.getFreezeTicks();
                    if(ft <= 200){
                        p.setFreezeTicks(ft + 5);
                    }
                    System.out.println(ft);
                }
            }
        }
    }
}
