package ru.kpfu.itis.aekrylov.balda;

import java.io.Serializable;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 12/25/16 5:53 PM
 */
public class Command implements Serializable {
    public enum CommandType {
        GAME_START, GAME_END,
        GET_WORD, SEND_WORD, WORD_CORRECT,
        SET_SCORE, OPPONENT_MOVE, SET_OPPONENT_SCORE
    }

    private CommandType type;
    private Object data;

    public Command(CommandType type) {
        this.type = type;
    }

    public Command(CommandType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public CommandType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

}
