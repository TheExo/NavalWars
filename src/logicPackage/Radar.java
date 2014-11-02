package logicPackage;

import java.util.ArrayList;

public class Radar {
    
    Ocean ocean;
    private ArrayList<Coordenates> radarCheck = new ArrayList();

    public Radar(Ocean ocean) {
        this.ocean = ocean;
    }
       
    public void scan(int posX, int posY, int Range){
        for (int i = 0; i < ocean.submarines.size() ; i++){
            Coordenates point = new Coordenates(posX, posY, ocean.submarines.get(i).depth);
            if ((ocean.submarines.get(i).posX <= (posX+Range) && ocean.submarines.get(i).posX >= (posX-Range)) && 
                (ocean.submarines.get(i).posY <= (posY+Range) && ocean.submarines.get(i).posY >= (posY-Range)) &&
                (ocean.submarines.get(i).posX != posX && ocean.submarines.get(i).posX != posY)){
                radarCheck.add(point);
            }
        }
    }
    
    public ArrayList returnCoor(){
        if (!radarCheck.isEmpty()){
        System.out.println("X :" + radarCheck.get(0).points.get(0) + "Y :" + radarCheck.get(0).points.get(1) + " Z :" + radarCheck.get(0).points.get(2));
        return radarCheck; 
        }else
            return null;
    }
}
