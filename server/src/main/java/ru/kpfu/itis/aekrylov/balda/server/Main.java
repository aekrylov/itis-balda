package ru.kpfu.itis.aekrylov.balda.server;

import ru.kpfu.itis.aekrylov.balda.Common;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 12/25/16 4:04 PM
 */
public class Main {

    public static void main(String[] args) throws IOException, SQLException {
        ServerSocket toServer = new ServerSocket(Common.PORT_OUT);
        ServerSocket toClients = new ServerSocket(Common.PORT_IN);

        while (true) {
            //get two clients
            Socket is1 = toServer.accept();
            Socket os1 = toClients.accept();
            PlayerConnection c1 = new PlayerConnection(is1, os1);

            Socket is2 = toServer.accept();
            Socket os2 = toClients.accept();
            PlayerConnection c2 = new PlayerConnection(is2, os2);

            new Room(WordsDAO.getRandomWord(Common.FIELD_SIZE), c1, c2).start();
        }
    }


}
