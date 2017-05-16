package Tchat;

import Concurrent.Client;

import java.net.SocketException;

/**
 * Created by Irindul on 09/05/2017.
 */
public class TchatClient extends Client {
    String pseudo;

    MessageListener listener;
    Thread t;

    public TchatClient(MessageListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {

        //connexion establishment


        send(pseudo, ia, PORT_HOST);

        try {
            receive();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        System.out.println(getBuffer());
        emptyBuffer();


        t = new Thread(() -> {
            while (true) {

                try {
                    receive();
                } catch (SocketException e) {
                    break;
                }
                System.out.println(getBuffer());
                if (getBuffer().startsWith("/list")) {
                    listener.onListReception(getBuffer().substring(5));
                } else {
                    listener.onReception(getBuffer());
                }

                //When we receive a message, we notify the view

            }

        });
        t.start();

    }

    @Override
    public void stop() {
        super.stop();
        listener.logout();
    }

    public void sendMessage(String message) {

        send(message, adr, port);


        if (message.equals("/logout")) {
            stop();
        }
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

}
