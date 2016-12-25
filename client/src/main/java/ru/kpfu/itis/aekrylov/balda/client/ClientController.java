package ru.kpfu.itis.aekrylov.balda.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 12/25/16 5:27 PM
 */
public class ClientController implements Initializable {

    private MediaPlayer player;

    @FXML
    TextArea input;

    @FXML
    TextArea history;

    @FXML
    Button sendButton;

    private PrintWriter out;

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    @FXML
    public void onButtonPressed() {
        String message = input.getText();
        out.println(message);

        input.setText("");
    }

    public void onMessageReceived(String message) {
        String text = history.getText();
        text += "\n" + message;

        history.setText(text);
        player.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Media media = new Media(getClass().getResource("spooky_scary_barks.m4a").toExternalForm());
        player = new MediaPlayer(media);
    }
}
