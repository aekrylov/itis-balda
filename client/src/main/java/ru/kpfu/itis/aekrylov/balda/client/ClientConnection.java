package ru.kpfu.itis.aekrylov.balda.client;

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
                Word word;
                switch (command.getType()) {
                    case GAME_START:
                        client.onGameStart((Word) command.getData());
                        break;
                    case GAME_END:
                        client.onGameEnd((Integer) command.getData());
                        break;
                    case SET_SCORE:
                        client.setScore((Integer) command.getData());
                        break;
                    case GET_WORD:
                        word = client.getWord();
                        objectOut.writeCommand(new Command(Command.CommandType.SEND_WORD, word));
                        break;
                    case OPPONENT_MOVE:
                        word = (Word) command.getData();
                        client.onOpponentMove(word);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
