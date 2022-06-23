package ecocraft.ecocraft;

import ecocraft.ecocraft.Commands.CableCommands;
import ecocraft.ecocraft.Commands.SolarPanelBaseCommands;
import ecocraft.ecocraft.Commands.SolarPanelCommands;
import ecocraft.ecocraft.CustomBlocks.Cable;
import ecocraft.ecocraft.CustomBlocks.RecyclerBlock;
import ecocraft.ecocraft.CustomBlocks.SolarPanel;
import ecocraft.ecocraft.CustomBlocks.SolarPanelBase;

import ecocraft.ecocraft.Handlers.*;

import ecocraft.ecocraft.Utils.NightDetector;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Ecocraft extends JavaPlugin {

    @Override
    public void onEnable() {

        Objects.requireNonNull(getCommand("solar")).setExecutor(new SolarPanelCommands());
        Objects.requireNonNull(getCommand("solarbase")).setExecutor(new SolarPanelBaseCommands());
        Objects.requireNonNull(getCommand("cable")).setExecutor(new CableCommands());

        MainEventHandler.init(this);
        SolarPanel.init();
        SolarPanelBase.init();
        Cable.init();
      
        RecyclerBlock.register(this);

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new FishingListener(this), this);
        pluginManager.registerEvents(new RainEventHandler(this), this);
        pluginManager.registerEvents(new DispenserListener(), this);
        NightDetector d = NightDetector.getInstance(this);
        d.detectNight();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}