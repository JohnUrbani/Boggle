import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BoggleGuiTesting extends Application
{
    public void start(Stage stage)
    {
        Game g = new Game();

        ArrayList<ArrayList<TilePane>> tilePanes = new ArrayList<ArrayList<TilePane>>();
        GridPane gp = new GridPane();

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Object clicked = e.getSource();
                if(clicked instanceof TilePane) {
                    // Cast the Object as a TilePane
                    TilePane tile = (TilePane)clicked;
                    // If not selected yet, select it and change the color
                    tilePanes.get(tile.getTile().getRow()).get(tile.getTile().getCol()).setSelected();
                    // If the last Tile selected, unselect it, removing it from the selected list

                    // Else don't do anything when clicked
                }
            }
        };

        for(int r = 0; r < 4; r++) {
            ArrayList<TilePane> row = new ArrayList<TilePane>();
            for(int c1 = 0; c1 < 4; c1++) {
                row.add(new TilePane(g.getTile(r,c1)));
            }
            tilePanes.add(row);
            for(int c2 = 0; c2 < 4; c2++) {
                gp.add(tilePanes.get(r).get(c2),c2,r);
                tilePanes.get(r).get(c2).addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            }
        }

        BorderPane bp = new BorderPane();
        HBox header = new HBox();
        HBox score = new HBox();
        VBox words = new VBox();
        VBox selected = new VBox();

        Text test1 = new Text("John Urbani's Boggle");
        Text test2 = new Text("Score: ");
        Text testScore = new Text("420");
        Text test3 = new Text("Words:");
        Text testWord = new Text("TEST");
        Text test4 = new Text("Selected:");
        Text testSelection = new Text("TEST");

        header.getChildren().add(test1);
        header.setAlignment(Pos.CENTER);

        score.getChildren().addAll(test2,testScore);
        score.setAlignment(Pos.CENTER);

        words.getChildren().addAll(test3,testWord);
        words.setAlignment(Pos.CENTER);

        selected.getChildren().addAll(test4,testSelection);
        selected.setAlignment(Pos.CENTER);

        bp.setCenter(gp);
        bp.setTop(header);
        bp.setBottom(score);
        bp.setLeft(words);
        bp.setRight(selected);
        bp.setPadding(new Insets(10,10,10,10));

        Scene scene = new Scene(bp);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String [] args)
    {
        launch(args);
    }
}