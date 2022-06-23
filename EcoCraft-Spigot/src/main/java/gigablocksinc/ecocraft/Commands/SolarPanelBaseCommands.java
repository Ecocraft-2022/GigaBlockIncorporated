package gigablocksinc.ecocraft.Commands;

import gigablocksinc.ecocraft.CustomBlocks.SolarPanelBase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SolarPanelBaseCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Unable to send message");
            return true;
        }
        Player player = (Player) sender;

        player.getInventory().addItem(SolarPanelBase.getSolarPanel());
        return true;
    }
}
