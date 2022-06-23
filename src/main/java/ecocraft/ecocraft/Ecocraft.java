package ecocraft.ecocraft;

import ecocraft.ecocraft.Commands.CableCommands;
import ecocraft.ecocraft.Commands.SolarPanelBaseCommands;
import ecocraft.ecocraft.Commands.SolarPanelCommands;
import ecocraft.ecocraft.CustomBlocks.Cable;
import ecocraft.ecocraft.CustomBlocks.SolarPanel;
import ecocraft.ecocraft.CustomBlocks.SolarPanelBase;

import ecocraft.ecocraft.Handlers.*;

import ecocraft.ecocraft.Utils.NightDetector;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ecocraft extends JavaPlugin {

    @Override
    public void onEnable() {

        getCommand("solar").setExecutor(new SolarPanelCommands());
        getCommand("solarbase").setExecutor(new SolarPanelBaseCommands());
        getCommand("cable").setExecutor(new CableCommands());

        MainEventHandler.init(this);
        SolarPanel.init();
        SolarPanelBase.init();
        Cable.init();
      
        Recycler.register(this);

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new FishingListener(), this);
        pluginManager.registerEvents(new RainEvent(), this);
        pluginManager.registerEvents(new DispenserListener(), this);
        NightDetector d = NightDetector.getInstance(this);
        d.detectNight();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
