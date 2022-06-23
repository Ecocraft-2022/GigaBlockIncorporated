package gigablocksinc.ecocraft;

import Commands.SolarPanelBaseCommands;
import Commands.SolarPanelCommands;
import CustomBlocks.SolarPanel;
import CustomBlocks.SolarPanelBase;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ecocraft extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("solar").setExecutor(new SolarPanelCommands());
        getCommand("solarbase").setExecutor(new SolarPanelBaseCommands());
        SolarPanel.init();
        SolarPanelBase.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
