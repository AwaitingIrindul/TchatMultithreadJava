package Concurrent;

import java.net.InetAddress;

/**
 * Created by Irindul on 02/05/2017.
 */
public class Connection extends UDP implements Runnable {



    public Connection(InetAddress iaClient, int portClient) {
        super(getOpenPort(1024,2048));
        send("Successful connection", iaClient, portClient);
    }

    public void run() {
        String s = "";

        while(s.compareTo("logout") != 0)
        {

            receive();
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
        send(message, getHostAddress(), getHostPort());
        System.out.println(getHostPort());
        
    }


}