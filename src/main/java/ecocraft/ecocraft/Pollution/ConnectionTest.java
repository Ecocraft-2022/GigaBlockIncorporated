package ecocraft.ecocraft.Pollution;

import java.io.IOException;

public class ConnectionTest {
    public static void main(String[] args) throws IOException {
        Regions.init(18432 , 9216,200000);

        System.out.println(Region.getRegionBy(1000,1000).regionInfo.get("geo"));


    }
}
