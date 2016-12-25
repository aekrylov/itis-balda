package ru.kpfu.itis.aekrylov.balda.server;

import java.sql.*;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 12/25/16 4:13 PM
 */
public class WordsDAO {

    //todo
    private static java.sql.Connection connection;

    public static boolean wordExists(String word)  {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement("SELECT * FROM words WHERE word = ?");
            st.setString(1, word);
            return st.executeQuery().getFetchSize() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static String getRandomWord(int length) {
        try {
            PreparedStatement st = connection.prepareStatement("SELECT word from words WHERE LENGTH(word) = ? ORDER BY random()");
            st.setInt(1, length);
            ResultSet rs = st.executeQuery();
            rs.next();
            return rs.getString("word");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
