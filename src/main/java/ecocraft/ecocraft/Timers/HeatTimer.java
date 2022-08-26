package ecocraft.ecocraft.Timers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class HeatTimer extends BukkitRunnable {
    private static final Material[] HEAT_BLOCKS = {
            Material.BLACK_CONCRETE,
            Material.GRAY_CONCRETE,
            Material.BROWN_CONCRETE,
            Material.BLACK_CONCRETE_POWDER,
            Material.GRAY_CONCRETE_POWDER,
            Material.BROWN_CONCRETE_POWDER,
            Material.BLACK_WOOL,
            Material.GRAY_WOOL,
            Material.BROWN_WOOL,
            Material.IRON_BLOCK
    };
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

                if(Arrays.stream(HEAT_BLOCKS).anyMatch(block -> block == blockMaterial)){
                    int ft = p.getFreezeTicks();
                    if(ft <= 200){
                        p.setFreezeTicks(ft + 5);
                    }
                }
            }
        }
    }
}
