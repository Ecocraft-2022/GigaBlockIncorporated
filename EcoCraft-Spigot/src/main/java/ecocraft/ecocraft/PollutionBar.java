package ecocraft.ecocraft;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.awt.*;

public class PollutionBar {

    private int taskID = -1;

    private final Ecocraft plugin;

    private BossBar bar;


    public PollutionBar(Ecocraft plugin) {
        this.plugin = plugin;
    }

    public void addPlayer(Player player){
        bar.addPlayer(player);
    }

    public BossBar getBar(){
        return bar;
    }

    public void createBar(){
        bar = Bukkit.createBossBar("Pollution", BarColor.WHITE, BarStyle.SOLID);
        bar.setVisible(true);
    }

}
