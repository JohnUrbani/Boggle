/**
 * John Urbani
 * CS110 Boggle Phase 1: Dictionary Class
 * An ArrayList of Strings that contains all words from a
 * dictionary file. Constructor will take a filename and
 * fill the ArrayList with the words in that file.
 * Also have a method to check if a word is valid or not.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary {
    ArrayList<String> dict = new ArrayList<String>();

    /**
     * Creates a Dictionary object using a filename. This will
     * read in a dictionary file with the given filename and
     * add all of its words into an ArrayList.
     * @param filename the filename for a dictionary text file
     */
    public Dictionary(String filename) {
        try {
            // Read in dictionary file
            Scanner file = new Scanner(new File(filename));
            // Add all words into an ArrayList
            while(file.hasNext()) {
                dict.add(file.nextLine().toUpperCase());
            }
            file.close();
        } catch(FileNotFoundException e) {
            // If the file doesn't exist, print an error message
            System.out.println(e);
        }
    }

    /**
     * Checks if a given list of Tile objects create a valid
     * word that is contained in the dictionary list.
     * @param tiles an ArrayList of Tiles which are used to
     *              create a word String
     * @return a boolean value which is true if the word String
     * is contained in the dictionary ArrayList, otherwise false.
     */
    public boolean isValidWord(ArrayList<Tile> tiles) {
        String word = "";

        // Create a word using the given ArrayList of Tiles
        for(Tile tile : tiles) {
            word = word + tile.getLetter();
        }

        word = word.toUpperCase();

        // Check if the word is contained in the dictionary ArrayList
        if(dict.contains(word)) {
            // If it is contained, the word is valid
            return true;
        } else {
            // Otherwise the word is invalid
            return false;
        }
    }
}
