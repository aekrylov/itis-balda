package ru.kpfu.itis.aekrylov.balda.server;

import ru.kpfu.itis.aekrylov.balda.Command;
import ru.kpfu.itis.aekrylov.balda.Word;

import java.io.*;
import java.net.Socket;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 12/25/16 4:06 PM
 */
class PlayerConnection {

    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;

    public PlayerConnection(Socket inputSocket, Socket outputSocket) throws IOException {
        objectIn = new ObjectInputStream(inputSocket.getInputStream());

        objectOut = new ObjectOutputStream(outputSocket.getOutputStream());
    }

    public Word getWord() {
        try {
            sendCommand(new Command(Command.CommandType.GET_WORD));
            return (Word) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    void sendScore(int score) {
        sendCommand(new Command(Command.CommandType.SET_SCORE, score));
    }

    void sendCommand(Command command) {
        try {
            objectOut.writeObject(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
