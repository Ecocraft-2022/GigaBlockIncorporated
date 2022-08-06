package ecocraft.ecocraft;

import ecocraft.ecocraft.Commands.CableCommands;
import ecocraft.ecocraft.Commands.SolarPanelBaseCommands;
import ecocraft.ecocraft.Commands.SolarPanelCommands;
import ecocraft.ecocraft.CustomBlocks.Cable;
import ecocraft.ecocraft.CustomBlocks.RecyclerBlock;
import ecocraft.ecocraft.CustomBlocks.SolarPanel;
import ecocraft.ecocraft.CustomBlocks.SolarPanelBase;

import ecocraft.ecocraft.Handlers.*;

import ecocraft.ecocraft.Pollution.Regions;
import ecocraft.ecocraft.Utils.NightDetector;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Ecocraft extends JavaPlugin {


    @Override
    public void onEnable() {

        Objects.requireNonNull(getCommand("solar")).setExecutor(new SolarPanelCommands());
        Objects.requireNonNull(getCommand("solarbase")).setExecutor(new SolarPanelBaseCommands());
        Objects.requireNonNull(getCommand("cable")).setExecutor(new CableCommands());
        //TODO wartosci w pilku konfiguracyjnym
        Regions.init(18432 , 9216,200000);

        MainEventHandler.init(this);
        SolarPanel.getInstance();
        SolarPanelBase.getInstance();

        Cable.getInstance();

        MainEventHandler.init(this);

        RecyclerBlock.register(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
