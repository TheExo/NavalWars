package logicPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    RadarServer radar;
    private ArrayList<CoordinatesServer> radarCheck = new ArrayList();
    
    //target position
    protected int targetDepth, targetAngle;
    
    //Ocean reference object
    Ocean ocean;
            
    //connection stuff
    DataInputStream incoming = null;
    DataOutputStream outgoing = null;
    
    SubmarineServer(DataOutputStream outgoing, DataInputStream incomming, Socket client, Ocean ocean, LoggingServer log){
        this.ocean = ocean;  
        this.incoming = incomming;
        this.outgoing = outgoing;
        
        
        //spawn sub in ocean
        spawnSub();
        this.client = client;
        
        ocean.submarines.add((SubmarineServer)this);
        radar = new RadarServer(ocean);
        
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
            
            
            try {
                pushRadar();
            } catch (IOException ex) {
                Logger.getLogger(SubmarineServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //if sub is too deap explodes
            if(this.depth <= 0){
                this.subAlive = false;
            }
 
            try {
                sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(SubmarineServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    }
    
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
    
    public void pushRadar() throws IOException{
        if (null == radarCheck)
            outgoing.writeInt(0);
        
        else {
            outgoing.writeInt(radarCheck.size());
            for(int i = 0; radarCheck.size()>i; i++){
                outgoing.writeInt(radarCheck.get(i).points.get(0));
                outgoing.writeInt(radarCheck.get(i).points.get(1));
                outgoing.writeInt(radarCheck.get(i).points.get(2));
            }
        }
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
      
}
