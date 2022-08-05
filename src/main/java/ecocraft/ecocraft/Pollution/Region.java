package ecocraft.ecocraft.Pollution;

import org.bukkit.Location;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Region {

    private static Map<Integer,Region> regionMap = new HashMap<>();
    //MAX ?
    private Integer pollutionLevel;

    private final String url = "https://api.waqi.info/feed/";
    //TODO
    private final String token = "e7cc8c16a2d5b77601e7c2d1dcc38dd554678408";

    private HttpURLConnection httpURLConnection;

    private Region(Location location) throws IOException {

        String coordinates = minecraftCoordinatesToRealCoordinates(location.getBlockX(),location.getBlockZ());

        StringBuilder builder = new StringBuilder(url);


        URL populatedUrl = new URL(builder.append(geoString(coordinates)).append(getToken(token)).toString());

        httpURLConnection = (HttpURLConnection) populatedUrl.openConnection();

        httpURLConnection.setRequestMethod("GET");

        //TODO HANDLING JSON RESPONSE





    }

    private String getToken(String token) {
        StringBuilder builder = new StringBuilder();
        return builder.append("/?token=").append(token).toString();
    }

    private String geoString(String coordinates) {
        StringBuilder builder = new StringBuilder();
        return builder.append("/geo/").append(coordinates).toString();
    }

    private String minecraftCoordinatesToRealCoordinates(Integer blockX, Integer blockY) {
        // format lat;lng

        StringBuilder sb  = new StringBuilder();
        //TODO CALCULATE CORDINATES
        String lang = blockX.toString();
        String lat = blockY.toString();

        return sb.append(lat).append(":").append(lang).toString();

    }

    public static Region getPlayerRegion(Location location) throws IOException {
        if(!regionMap.containsKey(getRegionNumber(location))){
           regionMap.put(getRegionNumber(location),new Region(location));
        }
        return regionMap.get(getRegionNumber(location));
    }



    private static Integer getRegionNumber(Location location){
        Integer height = Double.valueOf(location.getBlockZ()/Regions.regionDim).intValue();
        Integer width =  Double.valueOf(location.getBlockX()/ Regions.regionDim).intValue();
        return width+height;
    }

    public Integer getPollutionLevel() {
        return pollutionLevel;
    }
}
