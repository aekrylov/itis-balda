package ru.kpfu.itis.aekrylov.balda.client;

import ru.kpfu.itis.aekrylov.balda.Common;
import ru.kpfu.itis.aekrylov.balda.Word;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Function;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 12/25/16 5:34 PM
 */
public class ConsoleClient implements Client {

    private int score;
    private Scanner sc = new Scanner(System.in);

    private void start() throws IOException {
        Socket inputSocket = new Socket(Params.HOST, Common.PORT_IN);
        Socket outputSocket = new Socket(Params.HOST, Common.PORT_OUT);

        ClientConnection connection = new ClientConnection(inputSocket, outputSocket, this);
        connection.start();
    }

    public static void main(String[] args) throws IOException {
        new ConsoleClient().start();
    }

    @Override
    public void onGameStart(Word word) {
        System.out.println("Game started!");
        System.out.println("Initial word: "+word.getWord());
    }

    @Override
    public void onGameEnd(int result) {
        System.out.println("Game ended!");
        System.out.println(result > 0 ? "You won" : result < 0 ? "You lose" : "Tie");
    }

    @Override
    public void setScore(int score) {
        this.score = score;
        System.out.println("score = " + score);
    }

    @Override
    public void setOpponentScore(int score) {
        //todo
    }

    @Override
    public void onOpponentMove(Word word) {
        System.out.println("Opponent move:");
        System.out.println("word.getWord() = " + word.getWord());
    }

    @Override
    public void onWordCorrect() {

    }

    @Override
    public void getWord(Function<Word, Void> callback) {
        System.out.println("Enter word: ");
        String word = sc.nextLine().trim();
        callback.apply(new Word(word, null, 0));
    }
}
