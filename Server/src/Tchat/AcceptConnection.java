package Tchat;

import Concurrent.UDP;

import java.net.SocketException;

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
                try {
                    receive();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                System.out.println("Someone is establishing a connection : " + getHostAddress() + ":" + getHostPort());
                // TODO: 16/05/2017 Faire liste clients 
                TchatConnection connection = new TchatConnection(getHostAddress(), getHostPort(), server, getBuffer());

                new Thread(connection).start();
                server.addClient(connection);
                server.sendAll(connection.getPseudo()  + " is now connected");
            }

            server.stop();
    }
}
