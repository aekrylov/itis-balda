package sample;

import com.sun.javafx.collections.ChangeHelper;
import com.sun.javafx.scene.control.skin.LabeledText;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    Button start;
    @FXML
    Button exit;
    @FXML
    GridPane game_table;
    @FXML
    Button submit;
    Parent root;
    Scene scene;
    Stage stage;

    private static String letter;
    private Label label = null;
    private Label chosen = null;
    private static boolean pressed = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void startgame(Event e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("game.fxml"));
        scene = new Scene(root);
        redirectScene(scene, "Game", (Stage)start.getScene().getWindow());
    }

    private void redirectScene(Scene scene, String name, Stage window) {
        stage = window;
        stage.setTitle(name);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void setLetter(Event e) throws IOException, InterruptedException {
        if (chosen != null) {
            chosen.setText("");
        }
        Label label = (Label) e.getTarget();
        chosen = label;
        label.setText(letter);
    }

    @FXML
    public void chooseLetter(Event e) throws IOException, InterruptedException {
        Label label;
        if (e.getTarget().getClass().equals(LabeledText.class)) {
            label = (Label) ((LabeledText)e.getTarget()).getParent();
        }
        else {
            label = (Label) e.getTarget();
        }
        letter = label.getText();
        this.label = label;
    }

    @FXML
    public void submit(Event e) {
        chosen.setId("start_word");
        chosen.setOnMouseClicked(null);
        chosen = null;
        label = null;
    }

    @FXML
    public void mark(Event e) {
        Label marked = (Label) e.getTarget();
        marked.setId("founded_word");
        pressed = true;
    }

    @FXML
    public void marking(Event e) {
        Label marked = (Label) e.getTarget();
        marked.setId("founded_word");
    }

    public void mouse_event(MouseEvent e) {
        if (e.isShiftDown()) {
            pressed = false;
        }
    }
}
