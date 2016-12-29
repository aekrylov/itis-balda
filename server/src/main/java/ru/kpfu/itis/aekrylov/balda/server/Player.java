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

    public void onGameStart(String initialWord) {
        conn.sendCommand(new Command(Command.CommandType.GAME_START, new Word(initialWord, null, 0)));
    }

    public void onGameEnd(int result) {
        conn.sendCommand(new Command(Command.CommandType.GAME_END, result));
    }

    public void onWordCorrect() {
        conn.sendCommand(new Command(Command.CommandType.WORD_CORRECT));
    }

    public void onOpponentMove(Word word, int score) {
        conn.sendCommand(new Command(Command.CommandType.OPPONENT_MOVE, word));
        conn.sendCommand(new Command(Command.CommandType.SET_OPPONENT_SCORE, score));
    }

}
