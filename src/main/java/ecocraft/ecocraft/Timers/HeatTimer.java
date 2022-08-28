package ecocraft.ecocraft.Timers;

import ecocraft.ecocraft.Pollution.Region;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class HeatTimer extends BukkitRunnable {
    private final Integer mediumPollution;
    private final Plugin plugin;

    public HeatTimer(Plugin plugin) {
        this.plugin = plugin;
        this.mediumPollution = plugin.getConfig().getInt("mediumPollutionEnd");
    }

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
            for(Player player : Bukkit.getOnlinePlayers()){
                Location playerLocation = player.getLocation();
                EntityEquipment equipment = player.getEquipment();

                // Do not harm the player if he is wearing leather boots
                if(
                    equipment != null &&
                    equipment.getBoots() != null &&
                    equipment.getBoots().getType() == Material.LEATHER_BOOTS
                ) continue;

                Material blockMaterial = player.getWorld().getBlockAt(
                        playerLocation.getBlockX(),
                        playerLocation.getBlockY() - 1,
                        playerLocation.getBlockZ()
                ).getBlockData().getMaterial();
                if(Arrays.stream(HEAT_BLOCKS).anyMatch(block -> block == blockMaterial)){
                    int ft = player.getFreezeTicks();
                    if(ft <= 200){
                        player.setFreezeTicks(ft + 5);
                    }
                }
            }
        }
    }
}
