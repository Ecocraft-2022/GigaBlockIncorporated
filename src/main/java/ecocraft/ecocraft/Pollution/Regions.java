package ecocraft.ecocraft.Pollution;

import java.util.Optional;

public class Regions {

    private static Regions regions;

    public static Double regionDim;

    public static Integer width;

    public static Integer height;

    private Regions(Integer width, Integer height,Integer numberOfRegions) {
        this.width = width;
        this.height = height;
         this.regionDim = Math.floor(Math.sqrt((height*width)/numberOfRegions));
    }

    public static void init(Integer width,Integer height,Integer numberOfRegions){
        regions = new Regions(width,height,numberOfRegions);
    }

    public static Regions getInstance(){
        return Optional.ofNullable(regions).orElseThrow(NullPointerException::new);
    }

}
