import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
public class BoggleGui extends Application
{
    private Game g; // = new Game();
    private GridPane gp; // = new GridPane();
    private BorderPane bp = new BorderPane();

    private ArrayList<ArrayList<TilePane>> tilePanes = new ArrayList<ArrayList<TilePane>>();

    private Text selectedText = new Text("");


    public void start(Stage stage)
    {
        HBox header = new HBox();
        HBox score = new HBox();
        VBox words = new VBox();
        VBox selected = new VBox();

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Object clicked = e.getSource();
                if(clicked instanceof TilePane) {
                    // Cast the Object as a TilePane
                    TilePane tile = (TilePane)clicked;
                    // If not selected yet, select it and change the color
                    TilePane currentTile = tilePanes.get(tile.getTile().getRow()).get(tile.getTile().getCol());
                    int currentRow = currentTile.t.getRow();
                    int currentCol = currentTile.t.getCol();
                    String selectedLetters = "";
                    if(g.isValidSelection(currentRow,currentCol)) {
                        g.addToSelected(currentRow,currentCol);
                        currentTile.setSelected();
                        for(Tile tiles : g.getSelectedTiles()) {
                            selectedLetters += tiles.getLetter();
                        }
                    }
                    // If the last Tile selected, unselect it, removing it from the selected list]
                    else {
                        Tile lastTile = g.getSelectedTiles().get(g.getSelectedTiles().size()-1);
                        if(currentTile.getTile().equals(lastTile)) {
                            g.removeFromSelected(currentRow, currentCol);
                            currentTile.setUnselected();
                        }
                        for(Tile tiles : g.getSelectedTiles()) {
                            selectedLetters += tiles.getLetter();
                        }
                    }
                    selectedText = new Text(selectedLetters);
                    selected.getChildren().remove(0,2);
                    selected.getChildren().addAll(new Text("Selected:"),selectedText);
                }
            }
        };

        setUpGrid(eventHandler);

        Button wordTester = new Button("Test Word");
        Button newGame = new Button("New Game");

        newGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //g = new Game();
                    bp.setCenter(new GridPane());
                    setUpGrid(eventHandler);
                }
            }
        );

        wordTester.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent event) {
                   g.testSelected();
                   String testedWords = "";
                   //System.out.println(g.getWords());
                   for(Word words : g.getWords()) {
                        testedWords += words.toString() + "\n";
                   }
                   Text testedWordsText = new Text(testedWords);
                   words.getChildren().remove(0,2);
                   words.getChildren().addAll(new Text("Words:"),testedWordsText);

                   selected.getChildren().remove(0,2);
                   selected.getChildren().addAll(new Text("Selected:"),new Text(""));
                   for(ArrayList<TilePane> r : tilePanes) {
                        for(TilePane c : r) {
                            c.setUnselected();
                        }
                   }

                   score.getChildren().remove(0,4);
                   score.getChildren().addAll(wordTester,new Text("Score: "),new Text(""+g.getScore()),newGame);
               }
           }
        );


        header.getChildren().add(new Text("John Urbani's Boggle"));
        header.setAlignment(Pos.CENTER);

        score.getChildren().addAll(wordTester,new Text("Score: "),new Text(""+g.getScore()),newGame);
        score.setAlignment(Pos.CENTER);

        words.getChildren().addAll(new Text("Words:"),new Text(""));
        words.setAlignment(Pos.CENTER);

        selected.getChildren().addAll(new Text("Selected:"),new Text(""));
        selected.setAlignment(Pos.CENTER);

        //bp.setCenter(gp);
        bp.setTop(header);
        bp.setBottom(score);
        bp.setLeft(words);
        bp.setRight(selected);
        bp.setPadding(new Insets(10,10,10,10));

        /*for(int r = 0; r < 4; r++) {
            ArrayList<TilePane> row = new ArrayList<TilePane>();
            for(int c1 = 0; c1 < 4; c1++) {
                row.add(new TilePane(g.getTile(r,c1)));
            }
            tilePanes.add(row);
            for(int c2 = 0; c2 < 4; c2++) {
                gp.add(tilePanes.get(r).get(c2),c2,r);
                tilePanes.get(r).get(c2).addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            }
        }*/

        Scene scene = new Scene(bp);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String [] args)
    {
        launch(args);
    }

    public void setUpGrid(EventHandler eventHandler) {
        g = new Game();
        gp = new GridPane();
        for(int r = 0; r < 4; r++) {
            ArrayList<TilePane> row = new ArrayList<TilePane>();
            for(int c1 = 0; c1 < 4; c1++) {
                row.add(new TilePane(g.getTile(r,c1)));
            }
            tilePanes.add(row);
            for(int c2 = 0; c2 < 4; c2++) {
                System.out.println(c2);
                gp.add(tilePanes.get(r).get(c2),c2,r);
                tilePanes.get(r).get(c2).addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            }
        }
        bp.setCenter(gp);
    }
}