package logicPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

class SubmarineServer extends Thread{
    Socket client = null;
    //subs state
    private boolean subAlive = true, enginesWorking = true, 
            steeringWorking = true, diveControlWorking = true;
    protected int posX, posY, depth;
    protected int angleFacing = 0; //angle (360) sub is pointing
    //radar canection
    Radar radar;
    private ArrayList<Coordenates> radarCheck = new ArrayList();
    
    //target position
    protected int targetDepth, targetAngle;
    
    //Ocean reference object
    Ocean ocean;
            
    //connection stuff
    DataInputStream incomming = null;
    DataOutputStream outgoing = null;
    
    SubmarineServer(DataOutputStream outgoing, DataInputStream incomming, Socket client, Ocean ocean, LoggingServer log){
        this.ocean = ocean;  
        this.incomming = incomming;
        this.outgoing = outgoing;
        
        //spawn sub in ocean
        spawnSub();
        this.client = client;
        
        ocean.submarines.add((SubmarineServer)this);
        radar = new Radar(ocean);
        
    }
    
    //sub's server thread
    @Override
    public void run(){
                
        while(this.subAlive){
          
            try { 
                pushSubAtributes();
            } catch (IOException ex){
                Logger.getLogger(SubmarineServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            //scan the area around and recieve the list
            radar.scan(posX, posY, ocean.radarRange);
            radarCheck = radar.returnCoor();
            
            
            
            //if sub is too deap explodes
            if(this.depth <= 0){
                this.subAlive = false;
            }
 
            try {
                sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(SubmarineServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //sub dead
    }
    
    //spawn players sub in ocean within defined spawn area
    void spawnSub(){
        Random rand = new Random();
        int maxPos = ocean.centerXY + ocean.spawnRange / 2;
        int minPos = ocean.centerXY - ocean.spawnRange / 2;
        
        posX = rand.nextInt(maxPos - minPos) + minPos;
        posY = rand.nextInt(maxPos - minPos) + minPos;
        depth = rand.nextInt(ocean.oceanDepth - 10) + 10;
        
        //if position is already occuppied try again
        if(ocean.spawnPointOcuppied(posX, posX, posX)){
            spawnSub();
            System.out.println("Position occupied re-spawning");
        }
           
        System.out.println("posX spawn is " + posX +  " and posZ is " + depth);
    }
    
    private void pushSubAtributes() throws IOException{
        //sub statcs
        outgoing.writeBoolean(subAlive);
        outgoing.writeBoolean(enginesWorking);
        outgoing.writeBoolean(steeringWorking);
        outgoing.writeBoolean(diveControlWorking);

        //position in ocean
        outgoing.writeInt(posX);
        outgoing.writeInt(posY);
        outgoing.writeInt(depth);
        outgoing.writeInt(angleFacing);
    }
    
    /* return ocuppied points within the sonar degree line
    and sonar range */
    public int[][] sonar(int degree){
        int[][] occupied = new int[ocean.radarRange / 2][2];
        int[] pos;
        ArrayList ocuppiedPositions = new ArrayList();
        
        int numSubs = ocean.submarines.size();
        for(int i =0; i < numSubs; i++){
            int posXotherSub = ocean.submarines.get(i).posX;
            int posYotherSub = ocean.submarines.get(i).posY;
            //if other sub is in radar range
            if(Math.abs(this.posX - posXotherSub) < ocean.radarRange  && 
                    Math.abs(this.posY - posYotherSub) < ocean.radarRange){
                pos = new int[2];
                pos[0] = posXotherSub;
                pos[1] = posYotherSub;
                ocuppiedPositions.add(pos);
            }
            numSubs = ocean.submarines.size();
        }
        
        int uncertainty = 4; //degrees of acceptable error
        int circleCuadrant = 0;       
        
        if(degree > 0)
            circleCuadrant = 1;
        else if(degree > 90)
            circleCuadrant = 2;
        else if(degree > 180){
            circleCuadrant = 3;
        }else if(degree > 270)
            circleCuadrant = 4;
        
        if(circleCuadrant == 4 || circleCuadrant == 0 || circleCuadrant == 1){
            
        }else{
            
        }
                
        return occupied;        
    }
}
