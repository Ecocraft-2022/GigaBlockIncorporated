package ecocraft.ecocraft.Pollution;
import com.google.gson.JsonObject;
import ecocraft.ecocraft.Utils.Util;
import org.javatuples.Pair;
import org.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Region {

    private static Map<Pair<Integer,Integer>,Region> regionMap = new HashMap<>();

    private Integer localPollution = null;

    private Pair<Integer,Integer> center;

    private Integer pollutionLevel;

    public  Map<String,String> regionInfo = new HashMap<>();

    private final String url = "https://api.waqi.info/feed";
    //TODO token do config
    private final String token = Regions.apiToken;

    private HttpURLConnection httpURLConnection;

    private Region(Integer blockX,Integer blockZ) throws IOException {

        Pair<Integer,Integer> regionNumber = getRegionNumber(blockX,blockZ);

        //Center of region square
        blockX = Double.valueOf((Regions.regionDim * regionNumber.getValue1()) - Regions.regionDim/2).intValue()  ;
        blockZ = Double.valueOf((Regions.regionDim * regionNumber.getValue0()) - Regions.regionDim/2).intValue();

        this.center = new Pair<>(blockX,blockZ);

        String coordinates = minecraftCoordinatesToRealCoordinates(blockX,blockZ);

        StringBuilder builder = new StringBuilder(url);

        //population gur
        URL populatedUrl = new URL(builder.append(geoString(coordinates)).append(getToken(token)).toString());

        //connection to api
        httpURLConnection = (HttpURLConnection) populatedUrl.openConnection();
        httpURLConnection.setRequestMethod("GET");

        //Reaing data
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

        JSONObject iaqi = new JSONObject(data.get("iaqi").toString());

        JSONObject city = new JSONObject(data.get("city").toString());


        addToHashmap(iaqi);
        addToHashmap(city);

            if( Integer.valueOf(data.get("aqi").toString())>300){
                this.pollutionLevel = 5;
            }else {
                this.pollutionLevel = Double.valueOf(  Integer.valueOf(data.get("aqi").toString())).intValue();
            }

    }


    private void addToHashmap(JSONObject object){
        object.keys().forEachRemaining( key -> regionInfo.put(key,object.get(key).toString()));
    }




    private String getToken(String token) {
        StringBuilder builder = new StringBuilder();
        return builder.append("/?token=").append(token).toString();
    }

    private String geoString(String coordinates) {
        StringBuilder builder = new StringBuilder();
        return builder.append("/geo:").append(coordinates).toString();
    }

    private String minecraftCoordinatesToRealCoordinates(Integer blockX, Integer blockZ) {

        // format lat;lng
        StringBuilder sb  = new StringBuilder();
        Pair<Double,Double> coordinates =  Util.calculateToRealCoordinates(Double.valueOf(blockX),Double.valueOf(blockZ));
        Double lang = coordinates.getValue1();
        Double lat = coordinates.getValue0();
        return sb.append(lat).append(";").append(lang).toString();

    }

    public static Region getPlayerRegion(Integer blockX,Integer blockZ ) throws IOException {
        if(!regionMap.containsKey(getRegionNumber(blockX,blockZ))){
           regionMap.put(getRegionNumber(blockX,blockZ),new Region(blockX,blockZ));
        }
        return regionMap.get(getRegionNumber(blockX,blockZ));
    }


    public static Pair<Integer,Integer> getRegionNumber(Integer blockX,Integer blockZ ){
        Integer height = Double.valueOf(blockZ/Regions.regionDim).intValue() ;
        Integer width =  Double.valueOf(blockX/ Regions.regionDim).intValue() +1 ;
        return new Pair<Integer, Integer>(height,width);
    }

    public  Integer getPollutionLevel() {
        return   pollutionLevel ;
    }
    public Pair<Integer, Integer> getCenter() {
        return center;
    }

    public Integer getLocalPollution() {
        return localPollution;
    }

    public void setLocalPollution(Integer localPollution) {
        this.localPollution = localPollution;
    }
}

