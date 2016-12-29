package ru.kpfu.itis.aekrylov.balda.client;

import javafx.application.Platform;
import ru.kpfu.itis.aekrylov.balda.Command;
import ru.kpfu.itis.aekrylov.balda.CommandInputStream;
import ru.kpfu.itis.aekrylov.balda.CommandOutputStream;
import ru.kpfu.itis.aekrylov.balda.Word;

import java.io.*;
import java.net.Socket;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 12/25/16 5:26 PM
 */
public class ClientConnection extends Thread {

    private CommandInputStream objectIn;
    private CommandOutputStream objectOut;

    private Client client;

    public ClientConnection(Socket inputSocket, Socket outputSocket, Client client) throws IOException {
        BufferedOutputStream out = new BufferedOutputStream(outputSocket.getOutputStream());
        this.objectOut = new CommandOutputStream(out);

        BufferedInputStream in = new BufferedInputStream(inputSocket.getInputStream());
        this.objectIn = new CommandInputStream(in);

        this.client = client;
    }

    @Override
    public void run() {

        Command command;
        try {
            while ((command = objectIn.readCommand()) != null) {
                final Command command1 = command;
                Platform.runLater(() -> {
                    switch (command1.getType()) {
                        case GAME_START:
                            client.onGameStart((Word) command1.getData());
                            break;
                        case WORD_CORRECT:
                            client.onWordCorrect();
                            break;
                        case GAME_END:
                            client.onGameEnd((Integer) command1.getData());
                            break;
                        case SET_SCORE:
                            client.setScore((Integer) command1.getData());
                            break;
                        case GET_WORD:
                            client.getWord((word1 -> {
                                try {
                                    objectOut.writeCommand(new Command(Command.CommandType.SEND_WORD, word1));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }));
                            break;
                        case OPPONENT_MOVE:
                            Word word1 = (Word) command1.getData();
                            client.onOpponentMove(word1);
                            break;
                        case SET_OPPONENT_SCORE:
                            client.setOpponentScore((Integer) command1.getData());
                            break;
                    }

                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
