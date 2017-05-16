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



        /*new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()){
                String str;
                System.out.println("Send your message :");
                str = sc.nextLine();

            }

        }).start();*/


        new Thread(() -> {
            while (true) {
                System.out.println("En attente");
                receive();
                String test = getBuffer();

                System.out.print("Reçu : ");
                System.out.println(getBuffer());
                listener.onReception(getBuffer());
            }


        }).start();


    }

    public void sendMessage(String message) {
        send(message, adr, port);
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
