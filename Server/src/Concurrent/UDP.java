package Concurrent;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.concurrent.TimeoutException;

/**
 * Created by Irindul
 */
public class UDP {

    protected DatagramSocket socket;
    public DatagramPacket dp;
    public InetAddress ia;

    public static int PORT_HOST = 1234;
    public static int MAX_SIZE = 512;

    public static String IP_S = "localhost";

    protected byte[] buffer = new byte[MAX_SIZE];
    public static String END = "\0";


    public UDP() {
        try{
            socket = new DatagramSocket();
            ia = InetAddress.getByName(IP_S);

        }
        catch(SocketException | UnknownHostException e) {
            e.printStackTrace();
        }

    }

    public UDP(int port) {
        try{
            socket = new DatagramSocket(port);
        }
        catch(SocketException e) {
            e.printStackTrace();
        }
    }

    public void send(String msg, InetAddress adr, int port){

        byte[] byteMsg;
        msg += END;
        try {
            byteMsg = msg.getBytes("ascii");
            dp = new DatagramPacket(byteMsg, byteMsg.length, adr, port);
            socket.send(dp);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void emptyBuffer(){
        buffer = new byte[MAX_SIZE];
    }

    public void receive() throws SocketException {
        emptyBuffer();
        dp = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(dp);
        } catch (SocketTimeoutException e){
            System.out.println("Timeout expired received");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public String getBuffer() {

        try {

             String s = new String(buffer, "utf-8");
             return s.split(END)[0];
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public InetAddress getHostAddress() {

        return dp.getAddress();
    }

    public String getHostName() {
        return dp.getAddress().getHostAddress();
    }

    public void stop() {
        socket.close();
    }

    public int getHostPort() {
        return dp.getPort();
    }

    public static int getOpenPort(int departurePort, int arrivalPort){
        if(departurePort > arrivalPort || departurePort > 65535 || arrivalPort > 65535)
            return 0;
        boolean isUsed;
        DatagramSocket ds = null;
        for(int i = departurePort; i <= arrivalPort; ++i) {
            isUsed = false;
            try {
                ds = new DatagramSocket(i);
            }
            catch(SocketException ex) {
                isUsed = true;
            }
            if(!isUsed) {
                ds.close();
                return i;
            }
        }
        return 0;
    }
}