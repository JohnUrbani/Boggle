/**
 * John Urbani
 * CS110 Boggle Phase 3: TilePane
 * A child of the HBox class that is a HBox containing
 * a Tile object and whether the object is selected or
 * not. This tile will display its letter in the center
 * and change colors based on selection.
 */

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class TilePane extends HBox {
    Tile t;
    boolean selected = false;

    /**
     * Create a TilePane using a Tile object
     * @param t the Tile to be contained
     */
    public TilePane(Tile t) {
        this.t = t;
        Text letter = new Text();
        setAlignment(Pos.CENTER);
        String tileLetter = t.getLetter();
        // Make sure Qu stays capital Q lowercase u
        if(tileLetter.equals("Qu")) {
            letter.setText(tileLetter);
        } else {
            // Every other letter should be uppercase
            letter.setText(tileLetter.toUpperCase());
        }

        getChildren().add(letter);
        setMaxSize(75,75);
        setMinSize(75, 75);
        setStyles();

    }

    /**
     * Set the CSS Style of the Pane, making the Pane lightgray if
     * unselected or yellow if selected
     */
    public void setStyles() {
        String style = "-fx-font-size: 25px;" +
                       "-fx-border-width: 1px;" +
                       "-fx-border-color: black;";
        if(selected) {
            style += "-fx-background-color: linear-gradient(to bottom right, yellow, gold);;";
        } else {
            style += "-fx-background-color: linear-gradient(to bottom right, white, darkgray);";
        }
        setStyle(style);
    }

    /**
     * Set the selected boolean to true and update the color
     */
    public void setSelected() {
        selected = true;
        setStyles();
    }

    /**
     * Set the selected boolean to false and update the color
     */
    public void setUnselected() {
        selected = false;
        setStyles();
    }

    /**
     * Get the Tile contained in this Pane
     * @return a Tile object
     */
    public Tile getTile() {
        return t;
    }
}
