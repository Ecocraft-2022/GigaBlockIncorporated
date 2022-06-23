package ecocraft.ecocraft.Handlers;


import ecocraft.ecocraft.Utils.NightDetector;

import org.bukkit.plugin.Plugin;

public class MainEventHandler {

    static MainEventHandler mainEventHandler;

    private MainEventHandler(Plugin plugin){
        new SolarPanelEventHandler(plugin);
        new CableEventHandler(plugin);
        new FurnaceEventHandler(plugin);
        new MapEventHandler(plugin);
        new PollutionHandler(plugin);
        new FishingListener();
        new RainEventHandler(plugin);
        NightDetector d = NightDetector.getInstance(plugin);
        d.detectNight();
    }

    public static MainEventHandler init(Plugin plugin){
        if(mainEventHandler == null){
            mainEventHandler = new MainEventHandler(plugin);
        }
        return mainEventHandler;
    }

}
