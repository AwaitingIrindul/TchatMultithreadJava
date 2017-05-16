package Tchat;

/**
 * Created by Irindul on 14/05/2017.
 */
public interface MessageListener {

    void onReception(String message);

    void logout();
}
