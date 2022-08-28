package ecocraft.ecocraft.Commands.SolarPanles;

import ecocraft.ecocraft.CustomBlocks.Cable;
import ecocraft.ecocraft.Ecocraft;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class CableCommands implements CommandExecutor {

    private boolean showcase = Ecocraft.getPlugin(Ecocraft.class).getConfig().getBoolean("showcase");


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ( !(sender instanceof Player) || (!showcase && !sender.hasPermission("minecraft.command.op")) ) {
            sender.sendMessage("Unable to send command");
            return true;
        }
        Player player = (Player) sender;

        player.getInventory().addItem(Cable.getInstance().getItem());
        return true;
    }
}
