package ru.kpfu.itis.aekrylov.balda.server;

import ru.kpfu.itis.aekrylov.balda.Command;
import ru.kpfu.itis.aekrylov.balda.CommandInputStream;
import ru.kpfu.itis.aekrylov.balda.CommandOutputStream;
import ru.kpfu.itis.aekrylov.balda.Word;

import java.io.*;
import java.net.Socket;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 12/25/16 4:06 PM
 */
class PlayerConnection {

    private CommandInputStream objectIn;
    private CommandOutputStream objectOut;

    public PlayerConnection(Socket inputSocket, Socket outputSocket) throws IOException {
        objectOut = new CommandOutputStream(outputSocket.getOutputStream());

        objectIn = new CommandInputStream(inputSocket.getInputStream());
    }

    public Word getWord() {
        try {
            sendCommand(new Command(Command.CommandType.GET_WORD));
            return (Word) objectIn.readCommand().getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    void sendScore(int score) {
        sendCommand(new Command(Command.CommandType.SET_SCORE, score));
    }

    void sendCommand(Command command) {
        try {
            objectOut.writeCommand(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
