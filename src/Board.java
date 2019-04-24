/**
 * John Urbani
 * CS110 Boggle Phase 1: Board Class
 * Represents the game board and the letters shown on it. Each
 * row is an ArrayList of Tiles which are in another ArrayList
 * representing the rows, making a 2D ArrayList.
 * The board is a 4 by 4 grid created from 16 random dice.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.List;

public class Board {
    // Create the dice letter Strings
    final private String D1 = "R,I,F,O,B,X";
    final private String D2 = "I,F,E,H,E,Y";
    final private String D3 = "D,E,N,O,W,S";
    final private String D4 = "U,T,O,K,N,D";
    final private String D5 = "H,M,S,R,A,O";
    final private String D6 = "L,U,P,E,T,S";
    final private String D7 = "A,C,I,T,O,A";
    final private String D8 = "Y,L,G,K,U,E";
    final private String D9 = "Qu,B,M,J,O,A";
    final private String D10 = "E,H,I,S,P,N";
    final private String D11 = "V,E,T,I,G,N";
    final private String D12 = "B,A,L,I,Y,T";
    final private String D13 = "E,Z,A,V,N,D";
    final private String D14 = "R,A,L,E,S,C";
    final private String D15 = "U,W,I,L,R,G";
    final private String D16 = "P,A,C,E,M,D";
    final private List<String> ALL_DICE = Arrays.asList(D1,D2,D3,D4,D5,D6,D7,D8,D9,D10,D11,D12,D13,D14,D15,D16);

    private final int numDice = 16;
    private final int numSides = 6;

    // 2D ArrayList of Tiles, representing the game board
    private ArrayList<ArrayList<Tile>> board = new ArrayList<ArrayList<Tile>>();
    // The set of 16 dice to select from
    private ArrayList<String> dice = new ArrayList<String>();
    // The dice that have already been used
    private ArrayList<String> usedDie;
    // Letters from a die that have already been used
    //private ArrayList<Integer> usedLetters;
    private Random rand = new Random();

    /**
     * Creates a Board object that contains a 4 by 4 2D ArrayList of
     * Tile objects that represent a Boggle board's 4 by 4 dice
     * grid. All of the letters in the ArrayList are randomized
     * from a set of 16 6 sided dice.
     */
    public Board() {
        // Fill the dice ArrayList with all 16 possible dice
        dice.addAll(ALL_DICE);
        // Make the usedDie ArrayList, empty as we haven't used any dice yet
        usedDie = new ArrayList<String>();

        // Create a 4 by 4 grid of Tiles
        // Get a random die, then pick a random letter from it, add it to a row/col then repeat
        for(int i = 0; i <= 3; i++) {
            // Make an empty row
            ArrayList<Tile> letterRow = new ArrayList<Tile>();
            for (int j = 0; j <= 3; j++) {
                // Get a random die and split up its letters into an array
                String[] letters = getRandomDie().split(",");
                // Get a random index value
                int letterIndex = rand.nextInt(numSides);
                // Add a letter to the row at the row/col (i,j)
                letterRow.add(new Tile(letters[letterIndex], i, j));

            }
            // Add the row on to the board
            board.add(letterRow);
        }
    }

    /**
     * Get a random String from a list of dice Strings that
     * represent the letters on each side of a die
     * @return a String of comma separated letters
     */
    public String getRandomDie() {
        boolean newDie = false;
        String die = "";

        while(!newDie && usedDie.size() < numDice) {
            die = dice.get(rand.nextInt(numDice));
            if(!usedDie.contains(die)) {
                this.usedDie.add(die);
                newDie = true;
            }
        }
        return die;
    }


    /**
     * Create and return a String that is the board ArrayList with
     * each letter in its specific row and column coordinate
     * @return a 4 by 4 grid String
     */
    @Override
    public String toString() {
        String textBoard = "";
        // Go through every row
        for(ArrayList<Tile> row : board) {
            // Print every letter in each row, the 2D ArrayList has Tile objects for the columns
            for(Tile col : row) {
                // Check for "Qu" as it is the only double letter and will have different spacing
                if(col.getLetter().equals("Qu")) {
                    textBoard = textBoard + "" + col.getLetter() + "  ";
                } else { // Add the letter to the row
                    textBoard = textBoard + " " + col.getLetter() + "  ";
                }
            }
            // Go to a new line to start a new row
            textBoard = textBoard + "\n";
        }
        return textBoard;
    }

    /**
     * Get the Tile at the given row/col
     * @param row the row of the Tile
     * @param col the column of the Tile
     * @return the Tile contained on the board at this position
     */
    public Tile getTileAtPosition(int row, int col) {
        return board.get(row).get(col);
    }
}