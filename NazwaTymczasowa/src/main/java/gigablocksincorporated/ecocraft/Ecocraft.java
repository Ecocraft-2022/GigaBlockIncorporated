package gigablocksincorporated.ecocraft;

import gigablocksincorporated.ecocraft.Commands.SolarPanelCommand;
import gigablocksincorporated.ecocraft.CustomItems.SolarPanel;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ecocraft extends JavaPlugin {

    @Override
    public void onEnable() {
        SolarPanel.init();
        getCommand("solar").setExecutor(new SolarPanelCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
