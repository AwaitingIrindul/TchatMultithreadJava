package Tchat;

import Concurrent.Connection;

import java.net.InetAddress;
import java.net.SocketException;

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
        String s;
        boolean login = true;
        while (login) {

            try {
                receive();
            } catch (SocketException e) {
                e.printStackTrace();
            }
            s = getBuffer();

            if (s.compareTo("/logout") == 0){
                login = false;
                break;
            } if(s.startsWith("/")){
                String pseudoOther = s.substring(1);
                if(s.length() >= 2){
                    pseudoOther = pseudoOther.split(" ")[0];

                    //+1 for the / charachter
                    int length = pseudoOther.length() + 1;
                    String message = getPseudo() + " whispers " + s.substring(length);
                    server.sendPersonnal(message, pseudoOther);
                    sendToClient(message);
                }



            } else {
                System.out.println(pseudo + " posted : " + s + " (" + getHostAddress() + ":" + getClientPort() + ")");
                sendMessage(pseudo + "$ " + s);
            }

            emptyBuffer();

        }
        logout();
    }

    private void logout() {
        server.removeClient(this);
        stop();
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

    @Override
    public boolean equals(Object obj) {
        if(obj == null ){
            return false;
        }
        if(this == obj){
            return true;
        }

        if(obj instanceof TchatConnection){
            TchatConnection converted = (TchatConnection) obj;
            return converted.port == this.port;
        }

        return false;
    }


}
