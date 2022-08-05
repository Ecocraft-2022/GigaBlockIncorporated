package ecocraft.ecocraft.Pollution;


import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Regions {

    private static Regions regions;

    public static Double regionDim;

    private Regions(Integer width, Integer height,Integer numberOfRegions) {
         this.regionDim = Math.floor(Math.sqrt((height*width)/numberOfRegions));
    }

    public static void init(Integer width,Integer height,Integer numberOfRegions){
        regions = new Regions(width,height,numberOfRegions);
    }

    public static Regions getInstance(){
        return Optional.ofNullable(regions).orElseThrow(NullPointerException::new);
    }

    public static Region getPlayerRegion(Location location) throws IOException {
       return Region.getPlayerRegion(location);
    }

}
