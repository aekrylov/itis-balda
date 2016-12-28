package ru.kpfu.itis.aekrylov.balda.client;

import ru.kpfu.itis.aekrylov.balda.Word;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 12/25/16 6:00 PM
 */
public interface Client {

    void onGameStart(Word word);

    /**
     * Called when game ended
     * @param result 0 if tie, positive if you won, negative if you lose
     */
    void onGameEnd(int result);

    void setScore(int score);
    void onOpponentMove(Word word);
    Word getWord();
}
