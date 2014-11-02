/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logicPackage;

import java.util.ArrayList;

/**
 *
 * @author Nacho
 */
public class Coordenates {
    
    public ArrayList<Integer> points = new ArrayList();

    public Coordenates(int posX, int posY, int depth) {
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
