package ecocraft.ecocraft.Commands.Pollution;

import ecocraft.ecocraft.Pollution.Regions;
import ecocraft.ecocraft.Utils.Util;
import jdk.internal.net.http.common.Pair;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RealCoordinatesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Unable to send message");
            return true;
        }

        Double lang = Double.valueOf(strings[0]);
        Double lat = Double.valueOf(strings[2]);
        Double blockY = Double.valueOf(strings[1]);

        Pair<Double,Double> coorindates = Util.calculateToMinecraftCoordinates(lat ,lang);

        Double blockX =coorindates.first;
        Double blockZ =coorindates.second;

        ((Player) sender).teleport(new Location(((Player) sender).getWorld(),blockX,blockY,blockZ));
     return true;
    }
}


