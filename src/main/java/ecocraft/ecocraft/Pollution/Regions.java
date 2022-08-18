package ecocraft.ecocraft.Pollution;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Optional;

public class Regions {
    private static Regions regions;

    public static Double regionDim;

    public static Integer width;

    public static Integer height;

    public static String apiToken;

    private Regions(Integer width, Integer height,Integer numberOfRegions) {
        this.width = width;
        this.height = height;
        this.regionDim = Math.floor(Math.sqrt((height*width)/numberOfRegions));


    }

    public static void init(Integer width,Integer height,Integer numberOfRegions){
        regions = new Regions(width,height,numberOfRegions);
    }


    public static void init(FileConfiguration config){
        if (regions != null) return;
        System.out.println(
                config.getInt("worldWidth") + " " +
                config.getInt("worldHeight") + " " +
                config.getInt("numberOfRegions"));
        regions = new Regions(
                config.getInt("worldWidth"),
                config.getInt("worldHeight"),
                config.getInt("numberOfRegions")
        );
        apiToken = config.getString("apiToken");
    }

    public static Regions getInstance(){
        return Optional.ofNullable(regions).orElseThrow(NullPointerException::new);
    }

}
