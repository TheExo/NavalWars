package logicPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread{
    //server's port
    private static final int serverPort = 1995;
    private boolean serverAlive = true;
    
    DataOutputStream outgoing;
    DataInputStream incomming;
    
    Socket client;
    Ocean ocean = new Ocean();
    public ArrayList<Team> teams = new ArrayList();
    LoggingServer log;
    
    public static void main(String[] args) throws IOException {    
        Server server = new Server();
        server.start();
    }
    
    public void run(){
        //open server socket
        ServerSocket serv = null;
        try {
            serv = new ServerSocket(serverPort);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        while(serverAlive){
            //listen for client
            try {
                client = serv.accept();
                outgoing = new DataOutputStream(client.getOutputStream());
                incomming = new DataInputStream(client.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            SubmarineServer sub = new SubmarineServer(outgoing, incomming, client, ocean, log);
            sub.start(); //start clients thread
            System.out.println("A Client Connected!");

            try {
                sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Server Stopped.") ;
        
        //close server socket
        try {
            serv.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
