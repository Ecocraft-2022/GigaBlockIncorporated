package ecocraft.ecocraft.Handlers;

import ecocraft.ecocraft.Events.LeavesDestroyer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class RainEventHandler implements Listener {

    Plugin plugin;
    boolean isRain = false;
    LeavesDestroyer leavesDestroyer;

    public RainEventHandler(Plugin plugin) {
        this.plugin = plugin;
        this.leavesDestroyer = new LeavesDestroyer();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e)
    {
        if(e.toWeatherState() )
        {
            isRain = true;
            leavesDestroyer.runTaskTimer(plugin, 0, 40);
        }else{
            if(isRain){
                leavesDestroyer.cancel();
            }
            isRain = false;
        }
    }
}
