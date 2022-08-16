package ecocraft.ecocraft;

import ecocraft.ecocraft.Commands.Pollution.PollutionDetails;
import ecocraft.ecocraft.Commands.Pollution.RealCoordinatesCommand;
import ecocraft.ecocraft.Commands.Pollution.WhereCommand;
import ecocraft.ecocraft.Commands.SolarPanles.CableCommands;
import ecocraft.ecocraft.Commands.SolarPanles.SolarPanelBaseCommands;
import ecocraft.ecocraft.Commands.SolarPanles.SolarPanelCommands;
import ecocraft.ecocraft.CustomBlocks.Cable;
import ecocraft.ecocraft.CustomBlocks.RecyclerBlock;
import ecocraft.ecocraft.CustomBlocks.SolarPanel;
import ecocraft.ecocraft.CustomBlocks.SolarPanelBase;

import ecocraft.ecocraft.Handlers.*;

import ecocraft.ecocraft.Pollution.Regions;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public final class Ecocraft extends JavaPlugin {


    @Override
    public void onEnable() {

        if (!getDataFolder().exists()) getDataFolder().mkdir();
        saveDefaultConfig();
        Objects.requireNonNull(getCommand("realTp")).setExecutor(new RealCoordinatesCommand());
        Objects.requireNonNull(getCommand("solar")).setExecutor(new SolarPanelCommands());
        Objects.requireNonNull(getCommand("solarbase")).setExecutor(new SolarPanelBaseCommands());
        Objects.requireNonNull(getCommand("cable")).setExecutor(new CableCommands());
        Objects.requireNonNull(getCommand("where")).setExecutor(new WhereCommand());
        Objects.requireNonNull(getCommand("details")).setExecutor(new PollutionDetails());
        //TODO wartosci w pilku konfiguracyjnym
        Regions.init(getConfig());

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
