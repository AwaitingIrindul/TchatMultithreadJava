package Tchat;

import Concurrent.Connection;

import java.net.InetAddress;

/**
 * Created by Irindul on 09/05/2017.
 */
public class TchatConnection extends Connection {

    private TchatServer server;
    private int port;
    private InetAddress adr;
    private String pseudo;

    public TchatConnection(InetAddress iaClient, int portClient, TchatServer server, String pseudo) {
        super(iaClient, portClient);
        port = portClient;
        adr = iaClient;
        this.server = server;
        port = portClient;
        this.pseudo = pseudo;
    }

    public void sendToClient(String message) {
        Thread t = new Thread(new Emission(adr, port, socket, message));
        t.start();
    }

    @Override
    public void run() {
        String s = "";

        while (s.compareTo("logout") != 0) {

            receive();
            s = getBuffer();

            if (s.compareTo("logout") == 0)
                s = "Logging out, bye :p";
            System.out.println(pseudo + " posted : " + s + " (" + getHostAddress() + ":" + getClientPort() + ")");

            sendMessage(pseudo + ": " + s);
            emptyBuffer();

        }
        System.out.println("Stopped thread");
        stop();
    }


    @Override
    public void stop() {
        super.stop();
        server.removeClient(this);
    }

    @Override
    protected void sendMessage(String message) {
        server.sendAll(message);
    }

    public InetAddress getClientAdress() {
        return getHostAddress();
    }

    public int getClientPort() {
        return getHostPort();
    }

    public String getPseudo() {
        return pseudo;
    }

}
