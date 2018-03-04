import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * @author Josh
 * The game settings window.
 */
public class GameWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Group 17 Game");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25));

        Label rows = new Label("Rows:");
        gridPane.add(rows, 0, 0);

        TextField rowsField = new TextField();
        gridPane.add(rowsField, 1, 0);

        Label cols = new Label("Columns:");
        gridPane.add(cols, 0, 1);

        TextField colsField = new TextField();
        gridPane.add(colsField, 1, 1);

        Label difficulty = new Label("Difficulty:");
        gridPane.add(difficulty, 0, 2);

        TextField difficultyField = new TextField();
        gridPane.add(difficultyField, 1, 2);

        Button launchGame = new Button("Launch!");
        launchGame.setAlignment(Pos.BOTTOM_RIGHT);
        launchGame.setOnAction(event -> {
            int rowNum = Integer.parseInt(rowsField.getText());
            int colNum = Integer.parseInt(colsField.getText());
            int difficultyNum = Integer.parseInt(difficultyField.getText());

            Player player = new Player(rowNum - 1, colNum / 2, 1, 1, "");
            Board board = new Board(rowNum, colNum, difficultyNum, player);

            BoardWindow window = new BoardWindow(board, player);

            window.start(new Stage());
        });

        gridPane.add(launchGame, 1, 3);

        Scene scene = new Scene(gridPane, 550, 700);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}