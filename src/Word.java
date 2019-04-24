/**
 * John Urbani
 * CS110 Boggle Phase 1: Word Class
 * Represents a word chosen by the player. Stores the characters
 * in the word (as a String), and how many points the word is worth.
 */
import java.util.ArrayList;

public class Word {
    private String word;
    private int points;

    /**
     * Creates a Word object using a given ArrayList of Tile objects
     * and the letters contained in each Tile object. This also
     * determines the amount of points this word will be worth.
     * @param tiles an ArrayList containing Tile objects
     */
    public Word(ArrayList<Tile> tiles) {
        String word = "";

        // Create a word out of all the selected Tiles
        for (Tile tile : tiles) {
            word = word + tile.getLetter();
        }

        this.word = word;

        // Determine point value based on word length
        if(word.length() < 3) {
            points = 0;
        } else if(word.length() == 3 || word.length() == 4) {
            points = 1;
        } else if(word.length() == 5) {
            points = 2;
        } else if(word.length() == 6) {
            points = 3;
        } else if(word.length() == 7) {
            points = 5;
        } else if(word.length() >= 8) {
            points = 11;
        }
    }

    /**
     * Get the amount of points this word is worth
     * @return the int number of points the word is worth
     */
    public int getPoints() {
        return points;
    }

    /**
     * Create a String that is the word this object represents
     * @return the word String
     */
    @Override
    public String toString() {
        return word;
    }

}
