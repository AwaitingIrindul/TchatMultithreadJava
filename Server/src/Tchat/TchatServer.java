package Tchat;

import Concurrent.ConcurrentServer;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Irindul on 09/05/2017.
 */
public class TchatServer extends ConcurrentServer {
    private List<TchatConnection> connections;
    public TchatServer(int port) {
        super(port);
        connections = new ArrayList<>();
    }

    public void sendAll(String msg){
        for(TchatConnection c : connections){
            new Thread(() -> c.sendToClient(msg)).start();
        }
    }

    public void sendListToAll(){
        StringBuilder b = new StringBuilder();
        b.append("/list");
        connections.stream().map(TchatConnection::getPseudo).forEach( pseudo -> {
            b.append(pseudo);
            b.append(System.lineSeparator());
        });
        sendAll(b.toString());
    }


    public void removeClient(TchatConnection tchatConnection){
        System.out.println("Connexion stoped for : " + tchatConnection.getClientAdress() +":" + tchatConnection.getClientPort() );
        connections.remove(tchatConnection);
        System.out.println("Client number : " + connections.size());
        sendAll(tchatConnection.getPseudo() + " is disconected");
        sendListToAll();
    }

    @Override
    public void run() {
        new Thread(new AcceptConnection(this)).start();
    }

    public void addClient(TchatConnection c){
        connections.add(c);
        System.out.println("Client adeded : " + connections.size());
        sendListToAll();
        // TODO: 16/05/2017 Envoyer la liste a tout le monde
    }

    public static void main(String[] args) {
        TchatServer server = new TchatServer(PORT_HOST);
        server.run();
    }

    public void sendPersonnal(String message, String pseudoOther) {
        connections.forEach(connection -> {
            if(pseudoOther.equals(connection.getPseudo())){
                connection.sendToClient(message);
            }
        });
    }
}
