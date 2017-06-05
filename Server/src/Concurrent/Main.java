package Concurrent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Main {

    public static void main(String[] args) throws SocketException {
        getOpenPorts(80,1200);
        DatagramSocket ds = new DatagramSocket(1234);
        byte[] result = new byte[100];


        DatagramPacket dp = new DatagramPacket(result, result.length);
        try {
            ds.receive(dp);
            System.out.println(new String(result));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void getOpenPorts(int startPort, int endPort)
    {
        for(int i=startPort; i<=endPort; i++)
        {
            try {
                new DatagramSocket(i);
            } catch (SocketException e) {
                System.out.println("Port " + i + " fermé");
            }
        }
    }
}

