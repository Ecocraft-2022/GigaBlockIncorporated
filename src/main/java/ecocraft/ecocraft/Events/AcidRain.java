package ecocraft.ecocraft.Events;

import ecocraft.ecocraft.Ecocraft;
import ecocraft.ecocraft.Pollution.Region;
import ecocraft.ecocraft.Pollution.Regions;
import ecocraft.ecocraft.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.javatuples.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AcidRain {

    private final Plugin plugin = Ecocraft.getPlugin(Ecocraft.class);
    private Integer mediumPollution = plugin.getConfig().getInt("mediumPollutionEnd");
    private Integer lowPollutionEnd = plugin.getConfig().getInt("lowPollutionEnd");

    private Integer lowDamage = plugin.getConfig().getInt("lightAcidRain");
    private Integer highDamage = plugin.getConfig().getInt("heavyAcidRain");
    public void rain() throws IOException {

        List<Block> allBlocks = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {

            Region region = Region.getRegionBy(player);

                //player.getClientViewDistance() zwraca ilosc chunk
                getBlocksFromPlayerLevel(player).forEach(b -> {
                    allBlocks.add(b);
                });

            if (region.getPollutionLevel() + region.getLocalPollution() > lowPollutionEnd) {
                World w = player.getWorld();
                player.getNearbyEntities(20, 20, 20).forEach(entity -> {

                    if(entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        if(w.getHighestBlockAt(entity.getLocation()).getY() < entity.getLocation().getY()){
                        livingEntity.damage(region.getLocalPollution() + region.getPollutionLevel() > mediumPollution ? highDamage : lowDamage);
                    }

                }});
                if(w.getHighestBlockAt(player.getLocation()).getY() < player.getLocation().getY()){
                    player.damage(region.getLocalPollution() + region.getPollutionLevel() > mediumPollution ? highDamage/2 : lowDamage);
                }
            }
            decayBlocks(allBlocks);
        }
    }

    private void decayBlocks(List<Block> blocks) {
        if (blocks.size() > 0) {
            Random random = new Random();



            int randomIndex = random.nextInt(blocks.size());
            Block randomElement = blocks.get(randomIndex);
            blocks.remove(randomIndex);

            if (randomElement.getBlockData() instanceof Leaves ||  Util.isCrop(randomElement)) {
                randomElement.setType(Material.AIR);
            }

            if (randomElement.getType().equals(Material.GRASS_BLOCK)) {
                randomElement.setType(Material.DIRT);
            }
        }


    }

    private List<Block> getBlocksFromPlayerLevel(Player player) {

        //player.getClientViewDistance() zwraca ilosc chunk




        List<Block> result = new ArrayList<>();

        List<Region> regions = Regions.loadedRegions.get(player);

        for (Region region : regions) {
            if (region.getPollutionLevel() + region.getLocalPollution() > lowPollutionEnd) {

                Pair<Integer, Integer> center = region.getCenter();
                World world = player.getWorld();
                for (double x = center.getValue0() - (Regions.regionDim / 2); x <= center.getValue0() + (Regions.regionDim / 2); x++) {
                    for (double z = center.getValue1() - (Regions.regionDim / 2); z <= center.getValue1() + (Regions.regionDim / 2); z++) {
                        Block block = world.getHighestBlockAt((int) x, (int) z);

                        if (block.getBlockData() instanceof Leaves || block.getType().equals(Material.GRASS_BLOCK) || Util.isCrop(block)) {
                            result.add(block);
                        }

                    }
                }
            }
        }

        return result;
    }



}






