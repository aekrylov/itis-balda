package ru.kpfu.itis.aekrylov.balda.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.kpfu.itis.aekrylov.balda.Common;
import ru.kpfu.itis.aekrylov.balda.Word;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 12/25/16 5:26 PM
 */
public class GuiClient extends Application implements Client {

    private ClientController controller;
    private ClientConnection connection;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layout.fxml"));
        Parent root = loader.load();

        controller = loader.getController();

        Socket inputSocket = new Socket(Params.HOST, Common.PORT_IN);
        Socket outputSocket = new Socket(Params.HOST, Common.PORT_OUT);

        PrintWriter out = new PrintWriter(outputSocket.getOutputStream(), true);

        controller.setOut(out);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Balda client");
        primaryStage.show();

        connection = new ClientConnection(inputSocket, outputSocket, this);
        connection.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onGameStart(Word word) {
        //todo
    }

    @Override
    public void onGameEnd(int result) {
        //todo
    }

    @Override
    public void setScore(int score) {
        //todo
    }

    @Override
    public void onOpponentMove(Word word) {
        //todo
    }

    @Override
    public Word getWord() {
        //todo
        return null;
    }
}
