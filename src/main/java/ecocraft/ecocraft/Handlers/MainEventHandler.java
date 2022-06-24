package ecocraft.ecocraft.Handlers;


import ecocraft.ecocraft.Timers.HeatTimer;
import ecocraft.ecocraft.Utils.NightDetector;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class MainEventHandler {

    static MainEventHandler mainEventHandler;

    private MainEventHandler(Plugin plugin){
        new SolarPanelEventHandler(plugin);
        new CableEventHandler(plugin);
        new FurnaceEventHandler(plugin);
        new MapEventHandler(plugin);
        new PollutionHandler(plugin);
        new FishingListener(plugin);
        new RainEventHandler(plugin);
        new DispenserListener(plugin);
        NightDetector d = NightDetector.getInstance(plugin);
        d.detectNight();

        HeatTimer heatTimer = new HeatTimer();
        heatTimer.runTaskTimer(plugin, 0, 2);
    }

    public static MainEventHandler init(Plugin plugin){
        if(mainEventHandler == null){
            mainEventHandler = new MainEventHandler(plugin);
        }
        return mainEventHandler;
    }

}
