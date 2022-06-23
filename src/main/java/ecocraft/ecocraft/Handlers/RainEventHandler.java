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

    public RainEventHandler(Plugin plugin) {
        this.plugin =plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

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
