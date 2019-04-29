/**
 * John Urbani
 * CS110 Boggle Phase 3: BoggleGui
 * A GUI replacement of the Phase 2 BoggleText.
 * Launches a fully functional graphical user interface
 * game of Boggle that let's the user select and unselect letters,
 * test words, restart the game, and end the game showing the
 * final score obtained.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.util.ArrayList;

public class BoggleGui extends Application {
    // The Game, main GridPane, and TilePane list are made during game setup
    private Game g;
    private GridPane gp;
    private ArrayList<ArrayList<TilePane>> tilePanes;

    // Create a BorderPane to contain all other Panes
    private BorderPane bp = new BorderPane();

    // Text to display as the selected letters, updated each time a Tile is selected or deselected
    private Text selectedText = new Text("");

    // Message text to tell the player if they make an invalid selection
    private String messageText = "";

    // Countdown timer using a JavaFX Timeline, set to 90 seconds
    private final int MAX_TIME = 90;
    private int seconds;
    Timeline time = new Timeline();

    /**
     * Launch and keep track of the Boggle game GUI.
     * All user interaction handled by event handlers
     * set up within start().
     * @param stage the JavaFX stage that contains a scene, in which all GUI Objects are contained
     */
    public void start(Stage stage) {
        // Create each Pane object to be displayed
        HBox title = new HBox();
        HBox timerPane = new HBox();
        VBox header = new VBox();
        HBox options = new HBox();
        VBox words = new VBox();
        VBox selected = new VBox();
        HBox message = new HBox();
        VBox bottom = new VBox();

        // Set up the top and bottom Panes, each containing 2 Panes
        bottom.getChildren().addAll(message, options);
        header.getChildren().addAll(title,timerPane);

       // Create a new MouseEvent EventHandler
        EventHandler<MouseEvent> tileEventHandler = new EventHandler<MouseEvent>() {
            @Override
            /**
             * When a Pane is clicked, if it is a TilePane check if this would
             * be a valid selection. If it is a valid selection, change the Pane's
             * color and add this letter to the selection list. If this Pane was
             * already selected, check instead if it was the most recent selection.
             * If it was then deselect it, removing it from the selection list and
             * putting it back to the default color. Otherwise tell the user that
             * they made an invalid selection.
             */
            public void handle(MouseEvent e) {
                // Confirm the Object clicked was a TilePane
                Object clicked = e.getSource();
                if (clicked instanceof TilePane) {
                    // Cast the Object as a TilePane
                    TilePane tile = (TilePane) clicked;
                    // If not selected yet, select it and change the color
                    TilePane currentTile = tilePanes.get(tile.getTile().getRow()).get(tile.getTile().getCol());
                    int currentRow = currentTile.t.getRow();
                    int currentCol = currentTile.t.getCol();
                    String selectedLetters = "";
                    // Confirm the selection is valid
                    if (g.isValidSelection(currentRow, currentCol)) {
                        g.addToSelected(currentRow, currentCol);
                        currentTile.setSelected();
                        messageText = "";
                        for (Tile tiles : g.getSelectedTiles()) {
                            selectedLetters += tiles.getLetter();
                        }
                    }
                    // If the last Tile selected, unselect it, removing it from the selected list]
                    else {
                        Tile lastTile = g.getSelectedTiles().get(g.getSelectedTiles().size() - 1);
                        if (currentTile.getTile().equals(lastTile)) {
                            g.removeFromSelected(currentRow, currentCol);
                            currentTile.setUnselected();
                            messageText = "";
                        } else {
                            messageText = "Invalid Selection!";
                        }
                        for (Tile tiles : g.getSelectedTiles()) {
                            selectedLetters += tiles.getLetter();
                        }
                    }
                    // Update the selectedText then update the GUI
                    selectedText = new Text(selectedLetters);
                    selected.getChildren().remove(0, 2);
                    selected.getChildren().addAll(new Text("Selected:"), selectedText);
                    message.getChildren().remove(0, 1);
                    message.getChildren().add(new Text(messageText));
                }
            }
        };

        // Setup the Boggle game for the first time
        startNewGame(tileEventHandler);

        // Create buttons for the player to use
        Button wordTester = new Button("Test Word");
        Button newGame = new Button("New Game");
        Button endGame = new Button("End Game");

        // Set an action when the newGame button is clicked
        newGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                /**
                 * When newGame is clicked, create a new Boggle
                 * game and reset everything in the game, including
                 * the score and time remaining.
                 */
                public void handle(ActionEvent event) {
                    bp.setCenter(new GridPane());
                    startNewGame(tileEventHandler);
                    // Update the GUI with the new board, updated time, empty selection, and empty words
                    selected.getChildren().remove(0, 2);
                    selected.getChildren().addAll(new Text("Selected:"), new Text(""));
                    words.getChildren().remove(0, 2);
                    words.getChildren().addAll(new Text("Words:"), new Text(""));
                    timerPane.getChildren().remove(0,3);
                    timerPane.getChildren().addAll(new Text("Score:"), new Text("" + g.getScore()), new Text("Time: " + seconds));

                }
            }
        );

        // Set an action when the wordTester button is clicked
        wordTester.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               /**
                * When wordTester is clicked, check if the current
                * selection of letters creates a valid word or not.
                * If it does then add this word to the words list and
                * add to the player's score, otherwise don't add anything.
                * Afterwards deselect all currently selected Tiles.
                */
               public void handle(ActionEvent event) {
                   // Test the selected letters, either doing nothing or adding
                   // to the words list and adding to the score
                   g.testSelected();
                   String testedWords = "";
                   // Create a String with all words found so far
                   for (Word words : g.getWords()) {
                       testedWords += words.toString() + "\n";
                   }
                   Text testedWordsText = new Text(testedWords);
                   // Update the GUI with the words list String and the empty selection
                   words.getChildren().remove(0, 2);
                   words.getChildren().addAll(new Text("Words:"), testedWordsText);
                   selected.getChildren().remove(0, 2);
                   selected.getChildren().addAll(new Text("Selected:"), new Text(""));
                   // Unselect all TilePanes
                   for (ArrayList<TilePane> r : tilePanes) {
                       for (TilePane c : r) {
                           c.setUnselected();
                       }
                   }
                    // Update the score Pane in the GUI
                   timerPane.getChildren().remove(0,3);
                   timerPane.getChildren().addAll(new Text("Score:"), new Text("" + g.getScore()), new Text("Time: " + seconds));
               }
           }
        );

        // Set an action when the endGame button is clicked
        endGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                /**
                 * Call the endGame() function
                 */
                public void handle(ActionEvent event) {
                    endGame();
                }
            }
        );

        // Setting up each Pane, this includes adding default text, changing text alignment,
        // adding buttons, and adding padding, spacing, or sizes to make each Pane look better together.

        // Set up title Pane
        title.getChildren().add(new Text("John Urbani's Boggle"));
        title.setAlignment(Pos.CENTER);

        // Set up timer and score Pane
        timerPane.getChildren().addAll(new Text("Score:"), new Text("" + g.getScore()), new Text("Time: " + MAX_TIME));
        timerPane.setAlignment(Pos.CENTER);
        timerPane.setSpacing(10);

        // Set up options Pane
        options.getChildren().addAll(wordTester, newGame, endGame);
        options.setAlignment(Pos.CENTER);
        options.setSpacing(10);

        // Set up message Pane
        message.setPadding(new Insets(10, 10, 10, 10));
        message.getChildren().add(new Text(messageText));
        message.setAlignment(Pos.CENTER);

        // Set up words Pane
        words.getChildren().addAll(new Text("Words:"), new Text(""));
        words.setAlignment(Pos.CENTER);
        words.setMinWidth(130);

        // Set up selected Pane
        selected.getChildren().addAll(new Text("Selected:"), new Text(""));
        selected.setAlignment(Pos.CENTER);
        selected.setMinWidth(120);

        // Set up header Pane
        header.setMinHeight(60);
        header.setSpacing(10);
        bottom.setMinHeight(100);

        // Set up main BorderPane
        bp.setPadding(new Insets(10, 10, 10, 10));

        // Set background colors and font sizes using JavaFX CSS
        header.setStyle("-fx-background-color: 'CCE5FF';" +
                        "-fx-font-size: 30px;");
        bottom.setStyle("-fx-background-color: 'CCE5FF';" +
                "-fx-font-size: 20px;");
        words.setStyle("-fx-background-color: '99CCFF';" +
                "-fx-font-size: 20px;");
        selected.setStyle("-fx-background-color: '99CCFF';" +
                "-fx-font-size: 20px;");
        bp.setStyle("-fx-background-color: '003366';");

        // Set each other Pane to the main BorderPane
        bp.setTop(header);
        bp.setBottom(bottom);
        bp.setLeft(words);
        bp.setRight(selected);

        // Start the countdown timer
        startTime(timerPane);

        // Create a new Scene containing the main BorderPane
        Scene scene = new Scene(bp);
        stage.setResizable(false);
        stage.setTitle("John Urbani's Really Cool Boggle");
        // Set the scene then display it
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the Boggle game and its GUI
     * @param args unused arguments, passed to the launch function
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start the countdown timer, updating the timer in
     * the GUI and handling ending the game when the timer
     * runs to 0. Uses the JavaFX TimeLine and KeyFrames to
     * count down in 1 second intervals, decrementing a variable
     * that keeps track of the seconds remaining.
     * @param timerPane the HBox pane that displays the time remaining
     */
    private void startTime(HBox timerPane) {
        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                seconds--;
                timerPane.getChildren().remove(0,3);
                timerPane.getChildren().addAll(new Text("Score:"), new Text("" + g.getScore()), new Text("Time: " + seconds));
                if(seconds == 0) {
                    endGame();
                }
            }
        });

        time.setCycleCount(Timeline.INDEFINITE);
        time.getKeyFrames().add(frame);
        time.play();
    }

    /**
     * Set up and start a new Boggle game. Set the countdown
     * timer to max, set score to 0, empty any selections
     * and tested words, create a randomized Boggle game board,
     * then display the new game state.
     * @param event a mouse event handler to determine Tile selection
     */
    public void startNewGame(EventHandler event) {
        // Set or reset the seconds remaining
        seconds = MAX_TIME;
        // Create a new Game object with a randomized board of Tiles
        g = new Game();
        // Create an empty GridPane
        gp = new GridPane();
        // Transfer contents of the Game's randomized board to a list of TilePanes
        tilePanes = new ArrayList<ArrayList<TilePane>>();
        for (int r = 0; r < 4; r++) {
            ArrayList<TilePane> row = new ArrayList<TilePane>();
            for (int c1 = 0; c1 < 4; c1++) {
                // Create a new TilePane for each Tile in the Game's board
                row.add(new TilePane(g.getTile(r, c1)));
            }
            tilePanes.add(row);
            // Add mouse event handlers to each TilePane and add each TilePane to the GridPane
            for (int c2 = 0; c2 < 4; c2++) {
                //System.out.println(c2);
                gp.add(tilePanes.get(r).get(c2), c2, r);
                tilePanes.get(r).get(c2).addEventFilter(MouseEvent.MOUSE_CLICKED, event);
            }
        }
        // Update the GridPane's background color and padding
        gp.setStyle("-fx-background-color: '66B2FF';");
        gp.setPadding(new Insets(10, 0, 0, 10));
        // Set the new GridPane to the center position of the BorderPane,
        // replacing any existing GridPanes
        bp.setCenter(gp);
    }

    /**
     * Pop up a message showing the player's final score then
     * end the game when this message is closed. No other user
     * interaction can happen while this message is displayed.
     */
    public void endGame() {
        // Stop the countdown timer
        time.stop();
        // Create a new pop up message, called an alert
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle("Game Over");
        message.setHeaderText("Thanks for Playing!");
        message.setContentText("Final Score: " + g.getScore());
        message.setGraphic(new ImageView());
        message.initStyle(StageStyle.UTILITY);
        // Display this message to the player
        message.show();
        // Once the message is closed, exit the program
        message.setOnHidden(evt -> System.exit(0));

    }
}