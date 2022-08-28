package ecocraft.ecocraft.Commands.CustomItems;

import ecocraft.ecocraft.CustomItems.Mask;
import ecocraft.ecocraft.Ecocraft;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MaskCommand implements CommandExecutor {

    private boolean showcase = Ecocraft.getPlugin(Ecocraft.class).getConfig().getBoolean("showcase");


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if ( !(sender instanceof Player) || (!showcase && !sender.hasPermission("minecraft.command.op")) ) {
            sender.sendMessage("Unable to send message");
            return true;
        }


        Player player = (Player) sender;

        player.getInventory().addItem(Mask.getInstance().getItem());
        return true;
    }
}
