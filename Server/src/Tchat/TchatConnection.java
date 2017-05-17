package Tchat;

import Concurrent.Connection;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

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
            }

            if(!sendPersonal(s)) {

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

    private boolean sendPersonal(String s){
        List<String> pseudos = new ArrayList<>();
        String message = s;

        while(message.startsWith("/")){
            String pseudoOther = message.substring(1);
            if(message.length() > 1){
                pseudoOther = pseudoOther.split(" ")[0];
                pseudos.add(pseudoOther);

                //Length of pseudo + 1 for the / and 1 for the space
                int length = pseudoOther.length() + 2;

                message = message.substring(length);
            }
        }

        for(String pseudo : pseudos){
            String send = getPseudo() + " whispers " + message;
            server.sendPersonnal(send, pseudo);
        }

        if(pseudos.size() > 0){
            StringBuilder send = new StringBuilder("you whispered " + message + " to ");
            for (int i = 0; i < pseudos.size(); i++) {
                send.append(pseudos.get(i));
                if(i != pseudos.size()- 1){
                    send.append(", ");
                }
            }
            sendToClient(send.toString());
        }


        //If we send a whispers or not
        return pseudos.size() > 0;

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
