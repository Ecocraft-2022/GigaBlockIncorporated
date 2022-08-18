package ecocraft.ecocraft.Commands.Pollution;

import ecocraft.ecocraft.Pollution.Region;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public class PollutionDetails implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Unable to send message");
            return true;
        }
        //checks if scoreboard is visible
        if(((Player) sender).getScoreboard().getObjective("LocalPollution")!=null && ((Player) sender).getScoreboard().getObjective("LocalPollution").getDisplaySlot() != null){
            //hide scoreboard
            ((Player) sender).getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
            return true;
        }

        //getting scoreboard manager
        ScoreboardManager sm = Bukkit.getScoreboardManager();


        Scoreboard sb = sm.getNewScoreboard();

        Integer blockX = ((Player) sender).getLocation().getBlockX() ;
        Integer blockZ = ((Player) sender).getLocation().getBlockZ() ;

        Map<String,String> data;

        try {
            //fetching region data
            data = Region.getPlayerRegion(blockX,blockZ).regionInfo;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //creating new objective on scoreboard
        Objective obj = sb.registerNewObjective("LocalPollution","dummy","Pollution Details");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);


        for (String key : data.keySet()){
        //region data that is usefull here is stored in JSON format
        //this try checks if its JSON, if it is sets new score to scoreboard
        try {
            if(key.equals("pm25")|| key.equals("pm10")||key.equals("o3")||key.equals("no2")||key.equals("so2")||key.equals("co")) {
                Score score = obj.getScore(key.toUpperCase());
                JSONObject v = new JSONObject(data.get(key));
                score.setScore(v.getInt("v"));
            }
        }catch (Exception e){}
        }

        ((Player) sender).setScoreboard(sb);

        return true;
    }
}
