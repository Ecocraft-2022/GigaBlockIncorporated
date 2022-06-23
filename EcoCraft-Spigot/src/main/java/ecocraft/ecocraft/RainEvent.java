package ecocraft.ecocraft;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

class LeavesDestroyer extends BukkitRunnable {
    @Override
    public void run() {
        ArrayList<Material> leaves = new ArrayList<Material>();
        leaves.add(Material.ACACIA_LEAVES);
        leaves.add(Material.AZALEA_LEAVES);
        leaves.add(Material.BIRCH_LEAVES);
        leaves.add(Material.DARK_OAK_LEAVES);
        leaves.add(Material.FLOWERING_AZALEA_LEAVES);
        leaves.add(Material.JUNGLE_LEAVES);
        leaves.add(Material.MANGROVE_LEAVES);
        leaves.add(Material.SPRUCE_LEAVES);
        leaves.add(Material.MANGROVE_LEAVES);
        leaves.add(Material.OAK_LEAVES);

        Random random = new Random();

        for(Player player : Bukkit.getOnlinePlayers())
        {
            int radius = 20;
            Location loc = player.getLocation();
            World world = loc.getWorld();
            for (int x = -radius; x < radius; x++) {
                for (int y = -radius; y < radius; y++) {
                    for (int z = -radius; z < radius; z++) {
                        Block block = world.getBlockAt(loc.getBlockX()+x, loc.getBlockY()+y, loc.getBlockZ()+z);
                        boolean leavesBlock = false;
                        for(int i = 0; i < leaves.size(); ++i)
                        {
                            if(block.getType().equals(leaves.get(i)))
                            {
                                leavesBlock = true;
                                break;
                            }
                        }

                        if(leavesBlock && random.nextFloat() < 0.1) {
                            block.setType(Material.AIR);
                        }

                        if(block.getType() == Material.GRASS_BLOCK && random.nextFloat() < 0.1)
                        {
                            Block topBlock = block.getLocation().add(0, 1, 0).getBlock();
                            if(topBlock.getType() == Material.TALL_GRASS ||
                            topBlock.getType() == Material.GRASS)
                                topBlock.setType(Material.AIR);
                            block.setType(Material.DIRT);
                        }
                    }
                }
            }
        }
    }
}

public class RainEvent implements Listener {
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e)
    {
        if(e.toWeatherState())
        {
            LeavesDestroyer leavesDestroyer = new LeavesDestroyer();
            leavesDestroyer.runTaskTimer(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("Ecocraft")), 0, 40);
        }
    }
}
