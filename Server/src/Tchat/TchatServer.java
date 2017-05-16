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
            new Thread(() -> {
                c.sendToClient(msg);
            }).start();
        }
    }

    public void removeClient(TchatConnection tchatConnection){
        System.out.println("Connexion stoped for : " + tchatConnection.getClientAdress() +":" + tchatConnection.getClientPort() );
        connections.remove(tchatConnection);

    }

    @Override
    public void run() {
        new Thread(new AcceptConnection(this)).start();
    }

    public void addClient(TchatConnection c){
        connections.add(c);
        System.out.println("Client adeded : " + connections.size());
        // TODO: 16/05/2017 Envoyer la liste a tout le monde
    }

    public static void main(String[] args) {
        TchatServer server = new TchatServer(PORT_HOST);
        server.run();
    }
}
