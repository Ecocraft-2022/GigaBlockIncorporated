package ecocraft.ecocraft.Commands.Pollution;

import ecocraft.ecocraft.Pollution.Regions;
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

        Double blockX = Double.valueOf(strings[0]);
        Double blockZ = Double.valueOf(strings[2]);
        Double blockY = Double.valueOf(strings[1]);

        Double langFactor = 360/ Double.valueOf(Regions.width);
        Double latFactor = 180 / Double.valueOf(Regions.height);

        Double lang = Double.valueOf( blockX / langFactor);
        Double lat = Double.valueOf( (-1)*blockZ / latFactor);


        ((Player) sender).teleport(new Location(((Player) sender).getWorld(),lang,blockY,lat));
     return true;
    }
}
