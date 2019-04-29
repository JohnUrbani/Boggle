import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
public class Lab7 extends Application
{
    public void start(Stage stage)
    {
        Tile t1 = new Tile("Qu",0,0);
        Tile t2 = new Tile('a',0,1);
        Tile t3 = new Tile('x',0,2);
        Tile t4 = new Tile('e',0,3);
        Tile t5 = new Tile("Qu",1,0);
        Tile t6 = new Tile('a',1,1);
        Tile t7 = new Tile('x',1,2);
        Tile t8 = new Tile('e',1,3);
        Tile t9 = new Tile("Qu",2,0);
        Tile t10 = new Tile('a',2,1);
        Tile t11 = new Tile('x',2,2);
        Tile t12 = new Tile('e',2,3);
        Tile t13 = new Tile("Qu",3,0);
        Tile t14 = new Tile('a',3,1);
        Tile t15 = new Tile('x',3,2);
        Tile t16 = new Tile('e',3,3);

        TilePane tp1 = new TilePane(t1);
        TilePane tp2 = new TilePane(t2);
        tp2.setSelected();
        TilePane tp3 = new TilePane(t3);
        TilePane tp4 = new TilePane(t4);
        tp4.setSelected();
        TilePane tp5 = new TilePane(t5);
        TilePane tp6 = new TilePane(t6);
        TilePane tp7 = new TilePane(t7);
        TilePane tp8 = new TilePane(t8);
        TilePane tp9 = new TilePane(t9);
        TilePane tp10 = new TilePane(t10);
        TilePane tp11 = new TilePane(t11);
        TilePane tp12 = new TilePane(t12);
        TilePane tp13 = new TilePane(t13);
        TilePane tp14 = new TilePane(t14);
        TilePane tp15 = new TilePane(t15);
        TilePane tp16 = new TilePane(t16);

        GridPane gp = new GridPane();
        //remember, add takes col, row
        gp.add(tp1,0,0);
        gp.add(tp2,0,1);
        gp.add(tp3,0,2);
        gp.add(tp4,0,3);
        gp.add(tp5,1,0);
        gp.add(tp6,1,1);
        gp.add(tp7,1,2);
        gp.add(tp8,1,3);
        gp.add(tp9,2,0);
        gp.add(tp10,2,1);
        gp.add(tp11,2,2);
        gp.add(tp12,2,3);
        gp.add(tp13,3,0);
        gp.add(tp14,3,1);
        gp.add(tp15,3,2);
        gp.add(tp16,3,3);

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
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String [] args)
    {
        launch(args);
    }
}