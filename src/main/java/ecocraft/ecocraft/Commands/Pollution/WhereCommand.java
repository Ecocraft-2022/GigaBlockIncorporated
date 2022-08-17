package ecocraft.ecocraft.Commands.Pollution;

import ecocraft.ecocraft.Pollution.Region;
import ecocraft.ecocraft.Utils.Util;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Map;

public class WhereCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Unable to send message");
            return true;
        }
        Player player = (Player) sender;

        Location playerLocation = player.getLocation();

        Map<String,String> regionData;

        try {
            Region region = Region.getPlayerRegion(playerLocation.getBlockX(),playerLocation.getBlockZ());

            System.out.println(Util.calculateToRealCoordinates(playerLocation.getX(), playerLocation.getZ()));

            regionData = region.regionInfo;
        } catch (IOException e) {
           regionData = null;
            return false;
        }

        String message = String.format("City: %s \n Location: %s",regionData.get("name"),regionData.get("geo"));

        player.sendMessage(message);


        return true;
    }
}
