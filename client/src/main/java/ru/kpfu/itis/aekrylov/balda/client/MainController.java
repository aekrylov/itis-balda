package ru.kpfu.itis.aekrylov.balda.client;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 12/29/16 11:56 AM
 */
public class MainController {
    @FXML
    Button start;
    @FXML
    Button exit;

    @FXML
    public void startGame() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/game.fxml"));
        Scene scene = new Scene(root);
        redirectScene(scene, "Game", (Stage)start.getScene().getWindow());
    }

    private void redirectScene(Scene scene, String name, Stage window) {
        window.setTitle(name);
        window.setScene(scene);
        window.show();
    }
}
