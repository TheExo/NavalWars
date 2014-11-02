/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logicPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread{
    //server's port
    private static final int serverPort = 1995;
    private boolean serverAlive = true;
    
    Socket client;
    Ocean ocean = new Ocean();
    
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
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            SubmarineServer sub = new SubmarineServer(client, ocean);
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
