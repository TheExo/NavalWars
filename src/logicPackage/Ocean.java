/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logicPackage;

import java.util.ArrayList;

/**
 *
 * @author Seth
 */
public class Ocean{   
    public int oceanDepth = 10000000; //bellow this depth sub will explode
    public int spawnRange = 400;
    public int radarRange = oceanDepth;
    
    //ocean center
    public int centerXY = (int) (Math.pow(2, 30) / 2);
    
    /*min and max amount of boats to spawn near player*/
    public int boatMin= 1, boatMax = 10;
    public int boatSpawnRange = 100; //range boats spawn within
    
    //list of subs in the the server
    public ArrayList <SubmarineServer> submarines = new ArrayList();
        
    protected boolean spawnPointOcuppied(int x, int y, int z){
        //check if something is at that position
        int numSubs = submarines.size();
        for(int i = 0; i < numSubs; i++){
            SubmarineServer sub = submarines.get(i);
            if(sub.posX == x && sub.posY == y && sub.posY == z)
                return true;
            numSubs = submarines.size();
        }
        
        return false;
    }    
}
