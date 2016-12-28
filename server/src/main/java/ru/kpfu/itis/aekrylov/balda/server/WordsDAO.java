package ru.kpfu.itis.aekrylov.balda.server;

import java.sql.*;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 12/25/16 4:13 PM
 */
public class WordsDAO {

    private static java.sql.Connection connection = DB.getInstance().getConnection();

    public static boolean wordExists(String word)  {
        try {
            PreparedStatement st = connection.prepareStatement("SELECT exists(SELECT * FROM words WHERE word = ?)");
            st.setString(1, word);
            ResultSet rs = st.executeQuery();
            rs.next();
            return rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static String getRandomWord(int length) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT word from words WHERE LENGTH(word) = ? ORDER BY random() LIMIT 1");
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
