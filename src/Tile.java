/**
 * John Urbani
 * CS110 Boggle Phase 1: Tile Class
 * Represents one tile on the board. Stores the shown letter,
 * the row, the column, and a flag to show if it was selected.
 */

public class Tile {
    private String letter;
    private int row;
    private int col;
    private boolean selected = false;

    /**
     * Create a Tile at given position with given letter facing up
     * @param letter a char that is a given letter
     * @param row the row in which the tile exists on the board
     * @param col the column in which the tile exists on the board
     */
    public Tile(char letter, int row, int col) {
        // Convert the char into a String
        this.letter = ""+letter;
        this.row = row;
        this.col = col;
    }

    /**
     * Create a Tile at given position with given letter facing up
     * @param letter a String that is a given letter, used for double letters like "Qu"
     * @param row the row in which the tile exists on the board
     * @param col the column in which the tile exists on the board
     */
    public Tile(String letter, int row, int col) {
        this.letter = letter;
        this.row = row;
        this.col = col;
    }

    /**
     * Get the letter of this tile
     * @return the tile's letter as a String
     */
    public String getLetter() {
        return letter;
    }

    // Might have to change this later to just swap
    // to !selected, but this works for now
    /**
     * Set if the current tile is selected or not
     * @param selected true if selected, false if unselected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    // These getters were used for testing, but I imagine
    // this will be helpful information for later phases.
    /**
     * Get the Tile's row on the game board
     * @return the Tile's row
     */
    public int getRow() {
        return row;
    }

    /**
     * Get the Tile's column on the game board
     * @return the Tile's column
     */
    public int getCol() {
        return col;
    }

    // Currently unused, however I feel like this could
    // also be used in later phases when checking tiles
    // against each other.
    /**
     * Compare this Tile with another Tile to see if they
     * have the same letter, row, and column.
     * @param other another Tile object.
     * @return true if the compared object has the same
     * letter, row, and column, otherwise false.
     */
    @Override
    public boolean equals(Object other) {
        Tile otherTile;
        // Make sure other isn't empty
        if(other == null) {
            return false;
            // Check if they share the same space in memory
        } else if(this == other) {
            return true;
            // Make sure they have the same class
        } else if(!other.getClass().equals(this.getClass())) {
            return false;
        } else {
            // We know they have the same class, so make it easier
            // by casting and turning other into a Tile object
            otherTile = (Tile)other;
            // Make sure they have the same letter, row, and column
            if(this.getLetter().equals((otherTile.getLetter())) &&
               this.getRow() == (otherTile.getRow()) &&
               this.getCol() == ((otherTile.getCol()))) {
                return true;
            } else {
                // Otherwise they are not equal
                return false;
            }
        }
    }

    /**
     * Create a String that shows the Tile's letter and its location
     * @return a String in the format of "letter:(row,col)"
     */
    @Override
    public String toString() {
        return getLetter() + ":(" + getRow() + "," + getCol() + ")";
    }
}
