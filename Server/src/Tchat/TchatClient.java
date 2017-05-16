package Tchat;

import Concurrent.Client;

/**
 * Created by Irindul on 09/05/2017.
 */
public class TchatClient extends Client {
    String pseudo;

    MessageListener listener;

    public TchatClient(MessageListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {

        //connexion establishment
        send(pseudo, ia, PORT_HOST);
        receive();

        System.out.println(getBuffer());
        emptyBuffer();


        new Thread(() -> {
            while (true) {
                receive();
                System.out.println(getBuffer());

                listener.onReception(getBuffer());
                if(getBuffer().equals(pseudo + ":" + " Logging out, bye :p")){
                    stop();
                    break;
                }
                //When we receive a message, we notify the view

            }

        }).start();


        // TODO: 16/05/2017 Prevenir view
        //listener.logout();


    }

    @Override
    public void stop() {
        super.stop();
        listener.logout();
    }

    public void sendMessage(String message) {
        send(message, adr, port);
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
