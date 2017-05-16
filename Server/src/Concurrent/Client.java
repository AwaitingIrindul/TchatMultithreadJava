package Concurrent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Scanner;

/**
 * Created by Irindul on 02/05/2017.
 */
public class Client extends UDP {
    protected int port;
    protected InetAddress adr;
    public Client() {
        super();
    }

    public void run()
    {
        Scanner sc = new Scanner(System.in);
        String str = "";

        send(str, ia, PORT_HOST);
        receive();

        System.out.println(getBuffer());
        emptyBuffer();



        while(str.compareTo("logout") != 0) {

            System.out.println("Send your message :");
            str = sc.nextLine();
            send(str, adr, port);

            receive();
            System.out.println("Server : " + getBuffer());
            emptyBuffer();

        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    @Override
    public void receive() {
        dp = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(dp);
            adr = dp.getAddress();
            port = dp.getPort();
        } catch (SocketTimeoutException e){
            System.out.println("Timeout expired client receive");

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
