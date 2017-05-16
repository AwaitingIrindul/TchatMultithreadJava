package Concurrent;

import java.net.SocketException;

/**
 * Created by Irindul on 02/05/2017.
 */
    public class ConcurrentServer extends UDP {
        public ConcurrentServer(int port) {
            super(port);
            System.out.println("Server opened on port : " + PORT_HOST);
        }

        public void run() {
            while(true) {

                try {
                    receive();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                System.out.println("New client : " + getHostName() + ":" + getHostPort());
                new Thread(new Connection(getHostAddress(), getHostPort())).start();
            }
        }

        public static void main(String[] args) {
            ConcurrentServer sc = new ConcurrentServer(PORT_HOST);
            sc.run();
            sc.stop();
        }
    }