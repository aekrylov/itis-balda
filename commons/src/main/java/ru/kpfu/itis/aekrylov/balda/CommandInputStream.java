package ru.kpfu.itis.aekrylov.balda;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 12/28/16 1:04 PM
 */
public class CommandInputStream {

    private ObjectInputStream oin;

    public CommandInputStream(InputStream in) throws IOException {
        oin = new ObjectInputStream(in);
    }

    public Command readCommand() throws IOException {
        Command command = null;
        try {
            command = (Command) oin.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return command;
    }


}
