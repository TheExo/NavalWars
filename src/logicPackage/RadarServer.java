package logicPackage;

import java.io.*;
import java.util.ArrayList;

public class RadarServer{
    
    Ocean ocean;
    private ArrayList<CoordinatesServer> radarCheck = new ArrayList();

    public RadarServer(Ocean ocean) {
        this.ocean = ocean;
    }
       
    public void scan(int posX, int posY, int Range){
        radarCheck.clear();
        for (int i = 0; i < ocean.submarines.size() ; i++){
            CoordinatesServer point = new CoordinatesServer(posX, posY, ocean.submarines.get(i).depth);
            if ((ocean.submarines.get(i).posX <= (posX+Range) && ocean.submarines.get(i).posX >= (posX-Range)) && 
                (ocean.submarines.get(i).posY <= (posY+Range) && ocean.submarines.get(i).posY >= (posY-Range)) &&
                (ocean.submarines.get(i).posX != posX && ocean.submarines.get(i).posX != posY)){
                radarCheck.add(point);
            }
        }
    }
    
    public ArrayList returnCoor(){
        if (!radarCheck.isEmpty()){
        return radarCheck; 
        }else
            return null;
    }
}
