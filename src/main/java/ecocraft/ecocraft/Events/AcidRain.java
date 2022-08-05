package ecocraft.ecocraft.Events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AcidRain {

    public void rain() {


        List<Block> allBlocks = new ArrayList<>();

        List<Entity> allEntries = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            int radius = 20;

             getBlocksFromPlayerLevel(player, radius).forEach(b->{
                 allBlocks.add(b);
             });

            player.getNearbyEntities(10, 10, 10).forEach(entity -> {
              allEntries.add(entity);
            });

        }
         decayBlocks(allBlocks,allEntries);

    }

    private void decayBlocks(List<Block> blocks,List<Entity> entities ){

            Random random = new Random();

            int randomIndex = random.nextInt(blocks.size());
            Block randomElement = blocks.get(randomIndex);
            blocks.remove(randomIndex);

            if (randomElement.getBlockData() instanceof Leaves) {
                randomElement.setType(Material.AIR);
            }

            if (randomElement.getType().equals(Material.GRASS_BLOCK)) {
                randomElement.setType(Material.DIRT);
            }

            entities.forEach(entity -> {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.damage(1);
            });

    }

    private List<Block> getBlocksFromPlayerLevel(Player player, Integer radios) {
        int z = player.getLocation().getBlockZ();
        int x = player.getLocation().getBlockX();
        List<Block> result = new ArrayList<>();
        World w = Bukkit.getWorld(player.getWorld().getName());
        for (int xc = x - radios; xc < x + radios; xc++) {
            for (int zc = z - radios; zc < z + radios; zc++) {

                Block b = w.getHighestBlockAt(xc,zc);

                if ( b.getBlockData() instanceof Leaves
                        || b.getType().equals(Material.GRASS)
                        || b.getType().equals(Material.TALL_GRASS)
                        || b.getType().equals(Material.GRASS_BLOCK)
                ) {
                    result.add(b);
                }
            }
        }
        return result;
    }
}






