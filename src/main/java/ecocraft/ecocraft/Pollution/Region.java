package ecocraft.ecocraft.Pollution;

import com.google.gson.JsonObject;
import org.json.*;
import org.bukkit.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Region {

    private static Map<Integer,Region> regionMap = new HashMap<>();
    //MAX ?
    private Integer pollutionLevel;

    private final String url = "https://api.waqi.info/feed";
    //TODO
    private final String token = "e7cc8c16a2d5b77601e7c2d1dcc38dd554678408";

    private HttpURLConnection httpURLConnection;

    private Region(Integer blockX,Integer blockZ) throws IOException {
        //Center of region square
        blockX = Double.valueOf(Regions.regionDim/2 * getRegionNumber(blockX,blockZ)).intValue();
        blockZ = Double.valueOf(Regions.regionDim/2 * getRegionNumber(blockX,blockZ)).intValue();


        String coordinates = minecraftCoordinatesToRealCoordinates(blockX,blockZ);

        StringBuilder builder = new StringBuilder(url);




        URL populatedUrl = new URL(builder.append(geoString(coordinates)).append(getToken(token)).toString());



        httpURLConnection = (HttpURLConnection) populatedUrl.openConnection();
        httpURLConnection.setRequestMethod("GET");


        BufferedReader content;

        if(httpURLConnection.getResponseCode()!=200) throw new IOException();

        content = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

        StringBuilder response = new StringBuilder();

        String currentLine;

        while ((currentLine = content.readLine())!=null){
            response.append(currentLine);
        }
        content.close();

        JSONObject jsonObject = new JSONObject(response.toString());

        JSONObject data = new JSONObject(jsonObject.get("data").toString());


        this.pollutionLevel = Integer.valueOf(data.get("aqi").toString());


    }




    private String getToken(String token) {
        StringBuilder builder = new StringBuilder();
        return builder.append("/?token=").append(token).toString();
    }

    private String geoString(String coordinates) {
        StringBuilder builder = new StringBuilder();
        return builder.append("/geo:").append(coordinates).toString();
    }

    private String minecraftCoordinatesToRealCoordinates(Integer blockX, Integer blockY) {
        // format lat;lng

        StringBuilder sb  = new StringBuilder();
        //TODO CALCULATE CORDINATES
        String lang = blockX.toString();
        String lat = blockY.toString();

        return sb.append(lat).append(";").append(lang).toString();

    }

    public static Region getPlayerRegion(Integer blockX,Integer blockZ ) throws IOException {
        if(!regionMap.containsKey(getRegionNumber(blockX,blockZ))){
           regionMap.put(getRegionNumber(blockX,blockZ),new Region(blockX,blockZ));
        }
        return regionMap.get(getRegionNumber(blockX,blockZ));
    }



    private static Integer getRegionNumber(Integer blockX,Integer blockZ ){
        Integer height = Double.valueOf(blockZ/Regions.regionDim).intValue();
        Integer width =  Double.valueOf(blockX/ Regions.regionDim).intValue();
        return width+height;
    }

    public  Integer getPollutionLevel() {
        return pollutionLevel;
    }
}
