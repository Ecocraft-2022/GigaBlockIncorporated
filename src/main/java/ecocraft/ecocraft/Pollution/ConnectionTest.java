package ecocraft.ecocraft.Pollution;



import java.io.IOException;

public class ConnectionTest {


    public static void main(String[] args) throws IOException {

        Regions.init(3000,3000,200);
        System.out.println(Region.getPlayerRegion(200,45).getPollutionLevel());





    }

}
