package ru.kpfu.itis.aekrylov.balda.server;

import ru.kpfu.itis.aekrylov.balda.Common;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 12/25/16 4:04 PM
 */
public class Main {
    private static List<Room> rooms = new ArrayList<>();

    public static void main(String[] args) throws IOException, SQLException {
        ServerSocket toServer = new ServerSocket(Common.PORT_OUT);
        ServerSocket toClients = new ServerSocket(Common.PORT_IN);

        while (true) {
            //get two clients
            Socket is1 = toServer.accept();
            Socket os1 = toClients.accept();

            Socket is2 = toServer.accept();
            Socket os2 = toClients.accept();

            PlayerConnection c1 = new PlayerConnection(is1, os1);
            PlayerConnection c2 = new PlayerConnection(is2, os2);

            Room room = new Room(WordsDAO.getRandomWord(Common.FIELD_SIZE), c1, c2);
            rooms.add(room);
        }
    }


}
