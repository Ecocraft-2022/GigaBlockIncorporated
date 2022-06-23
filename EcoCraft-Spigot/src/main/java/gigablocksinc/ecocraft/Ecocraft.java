package gigablocksinc.ecocraft;

import gigablocksinc.ecocraft.Commands.CableCommands;
import gigablocksinc.ecocraft.Commands.SolarPanelBaseCommands;
import gigablocksinc.ecocraft.Commands.SolarPanelCommands;
import gigablocksinc.ecocraft.CustomBlocks.Cable;
import gigablocksinc.ecocraft.CustomBlocks.SolarPanel;
import gigablocksinc.ecocraft.CustomBlocks.SolarPanelBase;
import gigablocksinc.ecocraft.Handlers.SolarPanelEventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ecocraft extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("solar").setExecutor(new SolarPanelCommands());
        getCommand("solarbase").setExecutor(new SolarPanelBaseCommands());
        getCommand("cable").setExecutor(new CableCommands());
        SolarPanel.init();
        SolarPanelBase.init();
        Cable.init();
        new SolarPanelEventHandler(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
