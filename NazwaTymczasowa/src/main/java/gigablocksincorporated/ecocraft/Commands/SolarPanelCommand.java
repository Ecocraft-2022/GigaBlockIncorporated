package gigablocksincorporated.ecocraft.Commands;

import gigablocksincorporated.ecocraft.CustomItems.SolarPanel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SolarPanelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Unable to use this command");
            return true;
        }

        Player player= (Player) sender;

        if(command.getName().equalsIgnoreCase("solar")){
            player.getInventory().addItem(SolarPanel.getItemStack());
        }

        return true;
    }
}
