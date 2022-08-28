package ecocraft.ecocraft.Commands.Pollution;

import ecocraft.ecocraft.Ecocraft;
import ecocraft.ecocraft.Pollution.PollutionHandler;
import ecocraft.ecocraft.Pollution.Region;
import ecocraft.ecocraft.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SetLocalPollution implements CommandExecutor {

    private boolean showcase = Ecocraft.getPlugin(Ecocraft.class).getConfig().getBoolean("showcase");
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if ( !(sender instanceof Player) || (!showcase && !sender.hasPermission("minecraft.command.op")) ) {

            sender.sendMessage("Unable to send message");
            return true;
        }

        Location playerLocation = ((Player) sender).getLocation();

        Region region = Region.getPlayerRegion(playerLocation.getBlockX(),playerLocation.getBlockZ());
        try {
            region.setLocalPollution(Integer.valueOf(strings[0]));
            Util.updatePollution(region);
        }catch (NumberFormatException e){
            sender.sendMessage("Argument is not an number");
        }

        return false;
    }
}
