package ru.kpfu.itis.aekrylov.balda.client;

import ru.kpfu.itis.aekrylov.balda.Command;
import ru.kpfu.itis.aekrylov.balda.Word;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 12/25/16 5:26 PM
 */
public class ClientConnection implements Runnable {

    private Scanner in;
    private ObjectInputStream objectIn;
    private PrintWriter out;
    private ObjectOutputStream objectOut;

    private Client client;

    public ClientConnection(Socket inputSocket, Socket outputSocket, Client client) throws IOException {
        BufferedInputStream in = new BufferedInputStream(inputSocket.getInputStream());
        this.in = new Scanner(in);
        this.objectIn = new ObjectInputStream(in);

        BufferedOutputStream out = new BufferedOutputStream(outputSocket.getOutputStream());
        this.objectOut = new ObjectOutputStream(out);
        this.out = new PrintWriter(out);

        this.client = client;

        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            while (objectIn.available() > 0) {
                Word word;
                Command command = (Command) objectIn.readObject();
                switch (command.getType()) {
                    case SET_SCORE:
                        client.setScore((Integer) command.getData());
                        break;
                    case GET_WORD:
                        word = client.getWord();
                        objectOut.writeObject(word);
                        break;
                    case OPPONENT_MOVE:
                        word = (Word) command.getData();
                        client.opponentMove(word);
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
