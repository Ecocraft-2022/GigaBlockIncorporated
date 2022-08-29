package ecocraft.ecocraft.Handlers;


import ecocraft.ecocraft.Handlers.CustomBlocksHandlers.*;
import ecocraft.ecocraft.Handlers.CustomBlocksHandlers.Actions.DestroyCustomBlock;
import ecocraft.ecocraft.Pollution.PollutionHandler;
import ecocraft.ecocraft.Timers.HeatTimer;
import ecocraft.ecocraft.Utils.NightDetector;

import org.bukkit.plugin.Plugin;

public class MainEventHandler {

    static MainEventHandler mainEventHandler;

    private MainEventHandler(Plugin plugin){
        new BreakEventHandler(plugin);
        new FurnaceEventHandler(plugin);
        new PollutionHandler(plugin);
        new FishingListener(plugin);
        new RainEventHandler(plugin);
        new DispenserListener(plugin);
        new MainBlockHandler(plugin);
        new DisableRightClick(plugin);
        new DestroyCustomBlock(plugin);
        new TexturePackHandler(plugin);
        NightDetector.getInstance(plugin).detectNight();

        HeatTimer heatTimer = new HeatTimer(plugin);
        heatTimer.runTaskTimer(plugin, 0, 2);
    }

    public static MainEventHandler init(Plugin plugin){
        if(mainEventHandler == null){
            mainEventHandler = new MainEventHandler(plugin);
        }
        return mainEventHandler;
    }

}
