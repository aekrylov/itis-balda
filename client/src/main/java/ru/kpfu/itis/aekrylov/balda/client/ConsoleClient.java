package ru.kpfu.itis.aekrylov.balda.client;

import ru.kpfu.itis.aekrylov.balda.Common;
import ru.kpfu.itis.aekrylov.balda.Word;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 12/25/16 5:34 PM
 */
public class ConsoleClient implements Client {

    private ClientConnection connection;
    private int score;
    Scanner sc = new Scanner(System.in);

    private ConsoleClient() throws IOException {
        Socket inputSocket = new Socket(Common.HOST, Common.PORT_IN);
        Socket outputSocket = new Socket(Common.HOST, Common.PORT_OUT);

        connection = new ClientConnection(inputSocket, outputSocket, this);
    }

    public static void main(String[] args) throws IOException {
        new ConsoleClient();
    }

    @Override
    public void setScore(int score) {
        this.score = score;
        System.out.println("score = " + score);
    }

    @Override
    public void opponentMove(Word word) {
        System.out.println("Opponent move:");
        System.out.println("word.getWord() = " + word.getWord());
    }

    @Override
    public Word getWord() {
        System.out.println("Enter word: ");
        String word = sc.nextLine().trim();
        return new Word(word, null, 0);
    }
}
