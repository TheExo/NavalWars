package logicPackage;

import java.util.ArrayList;

public class CoordinatesServer {
    
    public ArrayList<Integer> points = new ArrayList();

    public CoordinatesServer(int posX, int posY, int depth) {
        points.add(posX);
        points.add(posY);
        points.add(depth);
    }
    
    public void moveCoor(int posX, int posY, int depth){
        if (posX != -1)
            points.add(0, posX);
        
        if (posY != -1)
            points.add(0, posY);
        
        if (depth != -1)
            points.add(0, depth);
        
    }
    
}
