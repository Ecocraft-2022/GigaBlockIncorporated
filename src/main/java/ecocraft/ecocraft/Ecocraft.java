package ecocraft.ecocraft;

import ecocraft.ecocraft.Commands.Pollution.PollutionDetails;
import ecocraft.ecocraft.Commands.Pollution.RealCoordinatesCommand;
import ecocraft.ecocraft.Commands.Pollution.SetLocalPollution;
import ecocraft.ecocraft.Commands.Pollution.WhereCommand;
import ecocraft.ecocraft.Commands.SolarPanles.CableCommands;
import ecocraft.ecocraft.Commands.SolarPanles.SolarPanelBaseCommands;
import ecocraft.ecocraft.Commands.SolarPanles.SolarPanelCommands;
import ecocraft.ecocraft.CustomBlocks.Cable;
import ecocraft.ecocraft.CustomBlocks.RecyclerBlock;
import ecocraft.ecocraft.CustomBlocks.SolarPanel;
import ecocraft.ecocraft.CustomBlocks.SolarPanelBase;

import ecocraft.ecocraft.Handlers.*;

import ecocraft.ecocraft.Pollution.PollutionHandler;
import ecocraft.ecocraft.Pollution.Region;
import ecocraft.ecocraft.Pollution.Regions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public final class Ecocraft extends JavaPlugin {
    @Override
    public void onEnable() {
        // Loading main config file
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        if (!getDataFolder().exists()) getDataFolder().mkdir();
        saveDefaultConfig();
        Objects.requireNonNull(getCommand("realTp")).setExecutor(new RealCoordinatesCommand());
        Objects.requireNonNull(getCommand("solar")).setExecutor(new SolarPanelCommands());
        Objects.requireNonNull(getCommand("solarbase")).setExecutor(new SolarPanelBaseCommands());
        Objects.requireNonNull(getCommand("cable")).setExecutor(new CableCommands());
        Objects.requireNonNull(getCommand("where")).setExecutor(new WhereCommand());
        Objects.requireNonNull(getCommand("details")).setExecutor(new PollutionDetails());
        Objects.requireNonNull(getCommand("localPollution")).setExecutor(new SetLocalPollution());





        // Initialize regions using config file

        Regions.init(getConfig());

        MainEventHandler.init(this);
        SolarPanel.getInstance();
        SolarPanelBase.getInstance();

        Cable.getInstance();

        MainEventHandler.init(this);

        RecyclerBlock.register(this);

        for(Player player: Bukkit.getOnlinePlayers()){
            Region region = PollutionHandler.initRegion(player);
            PollutionHandler.handleLocalPollution(player,region);
            PollutionHandler.loadRegions(player);


        }

    }

    @Override
    public void onDisable() {
        PollutionHandler.bossBarMap.values().forEach(bar -> bar.removeAll());
        // Plugin shutdown logic
    }

}