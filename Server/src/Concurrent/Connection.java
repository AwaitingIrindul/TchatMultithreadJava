package Concurrent;

import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by Irindul on 02/05/2017.
 */
public class Connection extends UDP implements Runnable {



    public Connection(InetAddress iaClient, int portClient) {
        super(getOpenPort(1024,2048));
        try {
            send("Successful connection", iaClient, portClient);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        String s = "";

        while(s.compareTo("logout") != 0)
        {

            try {
                receive();
            } catch (SocketException e) {
                e.printStackTrace();
            }
            s = getBuffer();

            if(s.compareTo("logout") == 0)
                s = "Logging out, bye :p";
            System.out.println(getHostAddress()  + " : " + s);
            
            sendMessage(s);
            emptyBuffer();

        }
        System.out.println("Stopped thread");
        stop();
    }

    //Will be overwritten
    protected void sendMessage(String message){
        try {
            send(message, getHostAddress(), getHostPort());
        } catch (SocketException e) {
            e.printStackTrace();
        }
        System.out.println(getHostPort());
        
    }


}