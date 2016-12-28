package ru.kpfu.itis.aekrylov.balda;

import java.io.Serializable;
import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 12/25/16 4:48 PM
 */
public class Word implements Serializable {

    private String word;

    private List<CharLocation> locations;
    private int insertIndex;

    public String getWord() {
        return word;
    }
    public List<CharLocation> getLocations() {
        return locations;
    }
    public int getInsertIndex() {
        return insertIndex;
    }

    public class CharLocation {
        public char c;
        public int x;
        public int y;
    }

    public Word(String word, List<CharLocation> locations, int insertIndex) {
        this.word = word.toLowerCase();
        this.locations = locations;
        this.insertIndex = insertIndex;
    }

    /**
     * Checks for cheating.
     * For words sent by standard client side this method should always return true
     * @param field game field
     * @return if move is valid
     */
    public boolean valid(char[][] field) {
        //TODO
/*
        if (field[locations.get(insertIndex).x][locations.get(insertIndex).y] != Common.EMPTY_CHAR) {
            return false;
        }

        //check that all cells are neighbours and chars exist

        for (int i = 0; i < locations.size(); i++) {
            if(i == insertIndex)
                continue;
            int x = locations.get(i).x;
            int y = locations.get(i).y;
            char c = locations.get(i).c;

            if(field[x][y] != c) {
                return false;
            }
        }
*/

        return true;
    }
}
