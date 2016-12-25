package ru.kpfu.itis.aekrylov.balda.server;

import ru.kpfu.itis.aekrylov.balda.Command;
import ru.kpfu.itis.aekrylov.balda.Word;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 12/25/16 4:17 PM
 */
public class Player {

    private PlayerConnection conn;

    private int score = 0;

    public Player(PlayerConnection conn) {
        this.conn = conn;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
        conn.sendScore(score);
    }

    public Word getWord() {
        return conn.getWord();
    }

    public void onGameStart() {
        conn.sendCommand(new Command(Command.CommandType.GAME_START));
    }

    public void onGameEnd() {
        conn.sendCommand(new Command(Command.CommandType.GAME_END));
    }

}
