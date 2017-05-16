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

    public TchatConnection(InetAddress iaClient, int portClient, TchatServer server) {
        super(iaClient, portClient);
        port = portClient;
        adr = iaClient;
        this.server = server;
        port = portClient;
    }

    public void sendToClient(String message) {


        Thread t = new Thread(new Emission(adr, port, socket, message));
        t.start();
    }

    @Override
    public void stop() {
        super.stop();
        server.removeClient(this);
    }

    @Override
    protected void sendMessage(String message) {
        server.sendAll(message, getHostAddress(), getHostPort());
    }

    public InetAddress getClientAdress() {
        return getHostAddress();
    }

    public int getClientPort() {
        return getHostPort();
    }


}
