package ru.kpfu.itis.aekrylov.balda.server;

import ru.kpfu.itis.aekrylov.balda.Common;
import ru.kpfu.itis.aekrylov.balda.Word;

import java.util.LinkedList;
import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 12/25/16 4:08 PM
 */
public class Room extends Thread {

    private Player player1;
    private Player player2;

    private List<String> wordsUsed = new LinkedList<>();
    private char[][] field = new char[Common.FIELD_SIZE][Common.FIELD_SIZE];
    private boolean gameOver = false;

    private int cellsRemaining;

    public Room(String initialWord, PlayerConnection c1, PlayerConnection c2) {
        player1 = new Player(c1);
        player2 = new Player(c2);

        field[Common.FIELD_SIZE / 2 + Common.FIELD_SIZE%2] = initialWord.toCharArray();
        wordsUsed.add(initialWord);
        cellsRemaining = Common.FIELD_SIZE * Common.FIELD_SIZE - initialWord.length();
    }

    public void run() {
        player1.onGameStart(wordsUsed.get(0));
        player2.onGameStart(wordsUsed.get(0));

        while (!gameOver) {
            playerTurn(player1, player2);
            playerTurn(player2, player1);
        }

        player1.onGameEnd(player1.getScore() - player2.getScore());
        player2.onGameEnd(player2.getScore() - player1.getScore());
    }

    private void playerTurn(Player player, Player other) {
        if(gameOver)
            return;

        Word word;
        do {
            word = player.getWord();
        } while (wordsUsed.contains(word.getWord()) || !word.valid(field) || !WordsDAO.wordExists(word.getWord()));

        //TODO
/*
        Word.CharLocation insertLocation = word.getLocations().get(word.getInsertIndex());
        int x = insertLocation.x;
        int y = insertLocation.y;
        field[x][y] = insertLocation.c;
*/
        player.setScore(player.getScore() + word.getWord().length());

        cellsRemaining--;
        if(cellsRemaining == 0) {
            gameOver = true;
        }

        player.onWordCorrect();
        wordsUsed.add(word.getWord());
        other.onOpponentMove(word, player.getScore());
    }
}
