package Tchat;

import Concurrent.UDP;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by Irindul on 16/05/2017.
 */
public class Emission extends UDP implements Runnable {


    private InetAddress adr;
    private int port;
    private String message;
    public Emission(InetAddress adr, int port, DatagramSocket socket, String message) {
        this.adr = adr;
        this.port = port;
        this.socket = socket;
        this.message = message;
    }

    @Override
    public void run() {
        send(message, adr, port);
    }
}
