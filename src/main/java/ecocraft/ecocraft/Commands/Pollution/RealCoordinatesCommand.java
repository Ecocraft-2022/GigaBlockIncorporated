package ecocraft.ecocraft.Commands.Pollution;

import ecocraft.ecocraft.Ecocraft;
import ecocraft.ecocraft.Utils.Util;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.javatuples.Pair;

public class RealCoordinatesCommand implements CommandExecutor {
    private boolean showcase = Ecocraft.getPlugin(Ecocraft.class).getConfig().getBoolean("showcase");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if ( !(sender instanceof Player) || (!showcase && !sender.hasPermission("minecraft.command.op")) ) {

            sender.sendMessage("Unable to send message");
            return true;
        }

        Double lang = Double.valueOf(strings[1]);
        Double lat = Double.valueOf(strings[0]);

        Double blockY = Double.valueOf(strings[2]);

        Pair<Double,Double> coorindates = Util.calculateToMinecraftCoordinates(lat ,lang);

        Double blockX = coorindates.getValue0();
        Double blockZ = coorindates.getValue1();

        ((Player) sender).teleport(new Location(((Player) sender).getWorld(),blockX,blockY,blockZ));
     return true;
    }
}


