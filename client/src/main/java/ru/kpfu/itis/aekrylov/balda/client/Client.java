package ru.kpfu.itis.aekrylov.balda.client;

import ru.kpfu.itis.aekrylov.balda.Word;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 12/25/16 6:00 PM
 */
public interface Client {

    void setScore(int score);
    void opponentMove(Word word);
    Word getWord();
}
