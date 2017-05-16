package Tchat;

import Concurrent.UDP;
import Tchat.TchatConnection;
import Tchat.TchatServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Irindul on 16/05/2017.
 */
public class AcceptConnection extends UDP implements Runnable {

    private TchatServer server;

    public AcceptConnection(TchatServer server) {
        this.server = server;
        this.socket = server.getSocket();
    }

    @Override
    public void run() {

            while(!Thread.currentThread().isInterrupted()){
                receive();
                System.out.println("Someone is establishing a connection : " + getHostAddress() + ":" + getHostPort());
                // TODO: 16/05/2017 Faire liste clients 
                TchatConnection connection = new TchatConnection(getHostAddress(), getHostPort(), server);
                new Thread(connection).start();
                server.addClient(connection);
            }

            server.stop();
    }
}
