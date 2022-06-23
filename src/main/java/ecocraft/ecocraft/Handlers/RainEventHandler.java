package ecocraft.ecocraft.Handlers;

import ecocraft.ecocraft.Events.AcidRain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.Plugin;

public class RainEventHandler implements Listener {

    Plugin plugin;
    boolean isRain = false;
    AcidRain acidRain;

    public RainEventHandler(Plugin plugin) {
        this.plugin = plugin;
        this.acidRain = new AcidRain();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e)
    {
        if(e.toWeatherState() )
        {
            isRain = true;
            acidRain.runTaskTimer(plugin, 0, 40);
        }else{
            if(isRain){
                acidRain.cancel();
            }
            isRain = false;
        }
    }
}
