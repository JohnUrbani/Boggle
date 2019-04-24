/**
 * John Urbani
 * CS110 Boggle Phase 2: Game Class
 * Create a Game class that will handle all backend parts of the
 * Boggle game, using BoggleText.java to interact with the game.
 * The game should be fully playable but BoggleText.java will handle
 * all player input.
 */

import java.util.ArrayList;

public class Game {
    // The Tiles currently selected
    private ArrayList<Tile> selected = new ArrayList<Tile>();
    // The tested words
    private ArrayList<Word> words = new ArrayList<Word>();
    private Board board = new Board();
    private int score = 0;
    private Dictionary dict = new Dictionary("dictionary.txt");

    /**
     * Creates a new Game with a score of 0, no Tiles selected, and no Words checked.
     */
    public Game(){
    }

    /**
     * Checks if a certain (row/col) selection is valid from the
     * previous selection.
     * @param row the selected row
     * @param col the selected column
     * @return true if the selected (row/col) is within 1 space of the
     * previous selection or is the first selection, otherwise false.
     */
    public boolean isValidSelection(int row, int col) {
        // Make sure selected Tile is on the board
        if(row >= 0 && row <= 3 && col >= 0 && col <= 3) {
            // If nothing has been selected yet, the selection is valid
            if(selected.size() == 0) {
                return true;
            } else {
                // Otherwise make sure it is adjacent to the previous Tile
                Tile previousTile = selected.get(selected.size() - 1);
                //System.out.println(previousTile.getLetter());
                int prevRow = previousTile.getRow();
                int prevCol = previousTile.getCol();
                if((prevRow == row + 1 || prevRow == row - 1 || prevRow == row) && (prevCol == col + 1 || prevCol == col - 1 || prevCol == col)) {
                    if(!selected.contains(getTile(row,col))) {
                        return true;
                    }
                }
            }
        }
        // Otherwise the selection is invalid
        return false;
    }

    /**
     * Add a Tile to the selected ArrayList from the board at the position(row/col).
     * @param row the selected row of a Tile on the board
     * @param col the selected column of a Tile on the board
     */
    public void addToSelected(int row, int col) {
        selected.add(getTile(row,col));
    }

    /**
     * Get the selected ArrayList, containing Tiles
     * @return an ArrayList of Tiles
     */
    public ArrayList<Tile> getSelectedTiles() {
        return selected;
    }

    /**
     * Get the Tile at the position(row,col) on the board
     * @param row the row to take from
     * @param col the column to take from
     * @return the Tile at position(row/col) on the board
     */
    public Tile getTile(int row, int col) {
        return board.getTileAtPosition(row,col);
    }

    /**
     * Remove the Tile at the given row/column on the board
     * from the selected ArrayList.
     * @param row the row of the Tile to be removed
     * @param col the column of the Tile to be removed
     */
    public void removeFromSelected(int row, int col) {
        int c = 0;
        int toRemove = -1;
        // Check if Tile selected to be removed is in the selected ArrayList
        for(Tile t : selected) {
            // If the row & col is the same, set this as the Tile to remove
            if(t.getRow() == row && t.getCol() == col) {
                toRemove = c;
            }
            c++;
        }
        // Remove the Tile if it exists
        if(toRemove != -1) {
            selected.remove(toRemove);
        }
    }

    /**
     * Clear the selected ArrayList of Tiles by making a new, empty, ArrayList
     */
    public void clearSelected() {
        selected = new ArrayList<Tile>();
    }

    /**
     * Test the Tiles contained in the selected ArrayList to see if they
     * create a valid word. If they do then clear the selected ArrayList,
     * add the Word to the words ArrayList, and add the Word's points to
     * the total score.
     */
    public void testSelected() {
        // Validate word with Dictionary word validating method
        if(dict.isValidWord(selected)) {
            Word goodWord = new Word(selected);
            // If this Word hasn't been used yet, add it to the words list and add the points
            boolean used = false;
            for(Word w : words) {
                //System.out.println(w.toString() + " " + goodWord.toString());
                if(w.toString().equals(goodWord.toString())) {
                    used = true;
                }
            }
            if(!used) {
                score += goodWord.getPoints();
                words.add(goodWord);
                //System.out.println(words);
            }
        }
        // Clear the selected ArrayList
        clearSelected();
    }

    /**
     * Create a String that is the game board, the selected Tiles,
     * and the tested Words.
     * @return a String representing the current game-state
     */
    @Override
    public String toString() {
        // Add the game board
        String game = board.toString() + "\nselected: [";
        String selectedLetters = "";
        // Add the list of selected Tiles
        for(Tile t : selected) {
            if(selectedLetters.length() == 0)
                selectedLetters += t.getLetter();
            else
                selectedLetters += ", " + t.getLetter();
        }
        game += selectedLetters + "]\nwords: [";
        // Add the list of tested Words
        String currentWords = "";
        for(Word w : words) {
            if(currentWords.length() == 0)
                currentWords += w.toString();
            else
                currentWords += ", " + w.toString();
        }
        // Add the current score
        game += currentWords + "]\nscore: " + score;
        return game;
    }

    /**
     * Get the tested list of words
     * @return an ArrayList of Word objects
     */
    public ArrayList<Word> getWords() {
        return words;
    }

    /**
     * Get the current score in the game
     * @return an integer which is the player's score
     */
    public int getScore() {
        return score;
    }
}
