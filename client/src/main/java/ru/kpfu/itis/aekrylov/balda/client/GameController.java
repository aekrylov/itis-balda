package ru.kpfu.itis.aekrylov.balda.client;

import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import ru.kpfu.itis.aekrylov.balda.Common;
import ru.kpfu.itis.aekrylov.balda.Word;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 12/29/16 12:03 PM
 */
public class GameController implements Initializable, Client {

    @FXML
    GridPane game_table;
    @FXML
    Button submit;

    @FXML
    TextField your_score;

    @FXML
    TextField opponent_score;

    @FXML
    Label turn;

    private ClientConnection connection;

    private static int GRID_SIZE = Common.FIELD_SIZE;
    private static int CENTER_ROW = Common.FIELD_SIZE/2;
    private char letter;

    private WordComposer composer = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //init pane
        System.out.println("initializing");
        System.out.println("game_table = " + game_table.getChildren().size());
        game_table.getChildren().clear();
        game_table.setGridLinesVisible(true);

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Label label = new Label("");

                label.setPrefWidth(56);
                label.setPrefHeight(56);
                label.setFont(Font.font(39));

                label.setOnMouseClicked((e) -> {
                    if(composer == null) {
                        //not in set letter phase, skipping
                        return;
                    }
                    composer.handle(label);
                });
                game_table.add(label, i, j);
            }
        }

        //connect to server
        try {
            Socket inputSocket = new Socket(Params.HOST, Common.PORT_IN);
            Socket outputSocket = new Socket(Params.HOST, Common.PORT_OUT);

            connection = new ClientConnection(inputSocket, outputSocket, this);
            connection.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onGameStart(Word word) {
        String s = word.getWord();

        //write init word
        for(Node node: game_table.getChildren()) {
            if(GridPane.getRowIndex(node) == CENTER_ROW) {
                ((Label) node).setText(String.valueOf(s.charAt(GridPane.getColumnIndex(node))));
            }
        }
    }

    @Override
    public void onGameEnd(int result) {
        //todo
        turn.setText("Игра окончена, итог "+result);
    }

    @Override
    public void setScore(int score) {
        your_score.setText(String.valueOf(score));
    }

    @Override
    public void setOpponentScore(int score) {
        opponent_score.setText(String.valueOf(score));
    }

    @Override
    public void onOpponentMove(Word word) {
        //set letter
        Word.CharLocation location = word.getLocations().get(word.getInsertIndex());
        for(Node node: game_table.getChildren()) {
            if(GridPane.getRowIndex(node) == location.y && GridPane.getColumnIndex(node) == location.x) {
                ((Label) node).setText(""+location.c);
            }
        }
    }

    @Override
    public void getWord(Function<Word, Void> callback) {
        turn.setText("Ваш ход");
        if(composer != null) {
            turn.setText("Снова ваш ход");
            ((Label) composer.insertNode).setText("");
        }
        composer = new WordComposer();
        submit.setOnMouseClicked((event) -> {
            if(composer.insertNode == null) {
                System.out.println("no insert node");
                return;
            }
            System.out.println("submitting");
            System.out.println("word = " + composer.getWord().getWord());
            callback.apply(composer.getWord());
            submit.setOnMouseClicked(null);
        });
    }

    @Override
    public void onWordCorrect() {
        ((Label) composer.insertNode).setText(String.valueOf(letter));
        composer = null;
        turn.setText("Слово принято");
    }

    @FXML
    public void chooseLetter(Event e) throws IOException {
        if(composer == null) {
            return;
        }

        Label label;
        if (e.getTarget().getClass().equals(LabeledText.class)) {
            label = (Label) ((LabeledText)e.getTarget()).getParent();
        }
        else {
            label = (Label) e.getTarget();
        }
        letter = label.getText().toLowerCase().charAt(0);
        System.out.println("letter = " + letter);
    }

    @FXML
    public void mark(Event e) {
        Label marked = (Label) e.getTarget();
        marked.setId("founded_word");
    }

    @FXML
    public void marking(Event e) {
        Label marked = (Label) e.getTarget();
        marked.setId("founded_word");
    }


    class WordComposer {

        private List<Word.CharLocation> locations = new ArrayList<>();
        private String s = "";
        private int insertIndex = -1;
        private Node insertNode;

        public void handle(Label node) {
            System.out.println("node.getText() = " + node.getText());
            String text = node.getText();
            if(text.equals("")){
                if(insertIndex >= 0) {
                    //insertable letter is already set
                    return;
                }
                insertIndex = s.length();
                insertNode = node;
                node.setText(String.valueOf(letter));
                locations.add(new Word.CharLocation(letter, GridPane.getColumnIndex(node), GridPane.getRowIndex(node)));
                s += letter;
            } else {
                s += text;
                locations.add(new Word.CharLocation(text.charAt(0), GridPane.getColumnIndex(node), GridPane.getRowIndex(node)));
            }
        }

        public Word getWord() {
            return new Word(s, locations, insertIndex);
        }

        public Node getInsertNode() {
            return insertNode;
        }
    }

}
