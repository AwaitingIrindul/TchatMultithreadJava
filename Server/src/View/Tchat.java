package View;/**
 * Created by Irindul on 14/05/2017.
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Tchat.*;

public class Tchat extends Application  implements MessageListener{

    private final static int WIDTH = 500;
    private final static int HEIGHT = 500;

    private TchatClient client;

    private TextArea tchatMsgs;
    private TextArea clients;
    private TextField inputMsg;

    private Scene scene;
    private Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //createHandlers(scene);
        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });



        //Creation du pane principal
        BorderPane container = new BorderPane();
        container.setPrefSize(WIDTH, HEIGHT);
        Group root = new Group();
        root.getChildren().add(container);


        //Mise en place du name
        TextField name = new TextField("Enter your pseudo");

        container.setTop(name);

        //Mise en place du bouton
        Button btn2 = new Button("Connect");
        btn2.setOnAction(event -> initClient(name.getText()));
        container.setCenter(btn2);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        //scene.getStylesheets().add("style/menu.css");



        primaryStage.setTitle("Tchat avec server RX302");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);


        primaryStage.show();
        this.primaryStage = primaryStage;
    }

    private void firstScene(){

    }

    private void changeScene() {

        scene = new Scene(createContent());

        scene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                sendAndClear();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initClient(String pseudo ){
        changeScene();
        client = new TchatClient(this);
        client.setPseudo(pseudo);
        client.run();

    }

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);


        BorderPane borderPane = new BorderPane();
        BorderPane borderPaneLeft = new BorderPane();
        BorderPane borderPaneRight = new BorderPane();

        borderPane.setLeft(borderPaneLeft);
        borderPane.setRight(borderPaneRight);

        Pane tchat = new Pane();
        Pane input = new Pane();
        Pane listClient = new Pane();

        borderPaneLeft.setTop(tchat);
        borderPaneLeft.setBottom(input);
        borderPaneRight.setCenter(listClient);



        tchat.setStyle("-fx-background-color: blue");
        tchat.setMinSize(WIDTH/2,HEIGHT/2);
        input.setStyle("-fx-background-color: lawngreen");
        input.setMinSize(WIDTH/2,HEIGHT/2);
        listClient.setStyle("-fx-background-color: blueviolet");
        listClient.setMinSize(WIDTH/2,HEIGHT);

        inputMsg = new TextField();

        tchatMsgs = new TextArea();
        tchatMsgs.setEditable(false);
        tchatMsgs.setPrefSize(WIDTH/2, HEIGHT/2);
        tchat.getChildren().add(tchatMsgs);

         clients = new TextArea();
        listClient.getChildren().add(clients);

        Button button = new Button();
        HBox hBox = new HBox();
        hBox.getChildren().addAll(inputMsg, button);

        input.getChildren().add(hBox);

        button.setOnAction(event -> {
            sendAndClear();
        });
        root.getChildren().add(borderPane);

        //root.getChildren().addAll(button, buttonPuzzle);

        return  root;

    }

    private void sendAndClear(){
        String t = inputMsg.getText();
        if(!t.equals("")){
            client.sendMessage(t);
            inputMsg.clear();
        }

    }

    @Override
    public void onReception(String message) {
        String old = tchatMsgs.getText();
        old = old + "\n" + message;
        tchatMsgs.setText(old);
    }

    @Override
    public void logout() {

    }
}
