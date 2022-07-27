package ecocraft.ecocraft.Handlers;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class PollutionHandler implements Listener {

    Plugin plugin;

    public PollutionHandler(Plugin plugin) {
        this.plugin =plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void isPlayerCloseToPollution(PlayerMoveEvent e){
        Player p  = e.getPlayer();
        int x = p.getLocation().getBlockX();
        int z = p.getLocation().getBlockZ();
        int y = p.getLocation().getBlockY();

        int pollutionCount = 0;

        List<Furnace> furnaces= new ArrayList<>();
        for (int i = x-5;i<=x+5;i++){
            for(int j = z-5;j<z+5;j++){
                for (int k = y-3; k <= y+3; k++) {
                    Block b = Bukkit.getWorld(Bukkit.getWorlds().stream().findFirst().get().getName()).getBlockAt(i,k,j);
                    if(b.getType().equals(Material.FURNACE)){
                        furnaces.add((Furnace )b.getState());
                    }
                }
            }
        }

        for(Furnace f : furnaces){
            if(f.getInventory().getFuel()!=null && f.getInventory().getFuel().getType().equals(Material.COAL)&& f.getCookTime()>0){
                pollutionCount++;
            }
        }
        handlePollution(p,pollutionCount);

    }
private BossBar b;
private void handlePollution(Player p,int pollution){
    if(b==null) {
        b = Bukkit.createBossBar("Pollution", BarColor.WHITE, BarStyle.SOLID);
    }
    b.setVisible(true);
    if(!b.getPlayers().contains(p)){
        b.addPlayer(p);
    }

    b.setProgress((float)pollution/5);
    try {
        if (p.getInventory().getHelmet().getType().equals(Material.TURTLE_HELMET)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, pollution * 100, pollution));
        }else{
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, pollution * 100, pollution));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, pollution * 100, pollution));
        }
    }catch (Exception e){
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, pollution * 100, pollution));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, pollution * 100, pollution));
    }

    if (pollution == 0) {
        p.removePotionEffect(PotionEffectType.BLINDNESS);
        p.removePotionEffect(PotionEffectType.SLOW);
    }

}

}
