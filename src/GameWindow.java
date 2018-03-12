import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Class Owner: Lachlan
 * 
 * The game settings window and main menu.
 */
public class GameWindow extends Application {
    private final int extraRows = 200; // Adds a fixed number of rows to the top of the board array.
    private HighScores localHS = new HighScores();

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

        gridPane.setOnKeyPressed(event -> {
            switch (event.getCode()) {
            case ESCAPE:
                primaryStage.close();
            }
        });

        Label nameLbl = new Label("Name:");
        gridPane.add(nameLbl, 0, 0);

        TextField nameField = new TextField();
        nameField.setMaxWidth(300);
        gridPane.add(nameField, 0, 1);

        Label scoresLbl = new Label("High Scores:");
        gridPane.add(scoresLbl, 0, 5);

        TextArea scoresArea = new TextArea("");
        scoresArea.setText(localHS.toString());
        gridPane.add(scoresArea, 0, 6);

        Label difficultyLbl = new Label("Difficulty:");
        gridPane.add(difficultyLbl, 0, 2);

        ComboBox<String> difficulty = new ComboBox<String>();
        difficulty.setItems(FXCollections.observableArrayList("Easy", "Normal", "Hard"));
        difficulty.setPromptText("Please pick a difficulty.");
        gridPane.add(difficulty, 0, 3);

        Button launchGame = new Button("Launch!");
        launchGame.setPrefSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        launchGame.setAlignment(Pos.BASELINE_CENTER);
        launchGame.setOnAction(event -> {
            int rowNum = 32;
            int colNum;
            int difficultyNum;
            String userDiff = difficulty.getValue();
            String userName = nameField.getText();

            switch (userDiff) {
            case "Easy":
                difficultyNum = 1;
                colNum = 30;
                break;
            case "Normal":
                difficultyNum = 2;
                colNum = 25;
                break;
            case "Hard":
                difficultyNum = 3;
                colNum = 23;
                break;
            default:
                difficultyNum = 1;
                colNum = 30;
                break;
            }
            int bigRowNum = rowNum + this.extraRows;

            Player player = new Player(bigRowNum - 1, colNum / 2, 1, 1, userName);
            Board board = new Board(bigRowNum, colNum, difficultyNum, player);

            BoardWindow window = new BoardWindow(board, player, rowNum);

            window.start(new Stage());

            // localHS.addHighScore(userName, player.getScore());
            // scoresArea.setText(localHS.toString());
        });
        launchGame.setMinWidth(100);
        gridPane.add(launchGame, 0, 7);

        Scene scene = new Scene(gridPane, 550, 575);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}