package ru.kpfu.itis.aekrylov.balda;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 12/28/16 1:07 PM
 */
public class CommandOutputStream {

    private ObjectOutputStream oout;

    public CommandOutputStream(OutputStream out) throws IOException {
        oout = new ObjectOutputStream(out);
        oout.flush();
    }

    public void writeCommand(Command command) throws IOException {
        oout.writeObject(command);
        oout.flush();
    }
}
