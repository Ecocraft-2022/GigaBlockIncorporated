package ecocraft.ecocraft.Commands.CustomItems;

import ecocraft.ecocraft.CustomItems.Mask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MaskCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Unable to send message");
            return true;
        }
        sender.getEffectivePermissions().forEach(permissionAttachmentInfo -> {
            System.out.println(permissionAttachmentInfo);
        });
        Player player = (Player) sender;

        player.getInventory().addItem(Mask.getInstance().getItem());
        return true;
    }
}
