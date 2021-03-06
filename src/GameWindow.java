import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class Owner: Lachlan
 * <p>
 * The game settings window and main menu.
 */
public class GameWindow extends Application {
    /**
     * Launches GUI
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Group 17 Game");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(1));
        gridPane.setStyle("-fx-background-color: #2F2F2F;");

        gridPane.setOnKeyPressed(event -> {
            switch (event.getCode()) {
            case ESCAPE:
                primaryStage.close();
            }
        });

        Label nameLbl = new Label("Player Nickname:");
        nameLbl.setStyle("-fx-font-size: 14px;-fx-text-fill: #DEDEDE;");
        gridPane.add(nameLbl, 0, 0);

        TextField nameField = new TextField();
        nameField.setStyle("-fx-font-size: 14px;-fx-background-color: #666666;");
        gridPane.add(nameField, 0, 1);
        GridPane.setMargin(nameField, new Insets(0, 20, 0, 0));

        Label difficultyLbl = new Label("Difficulty:");
        difficultyLbl.setStyle("-fx-font-size: 14px;-fx-text-fill: #DEDEDE;");
        gridPane.add(difficultyLbl, 0, 2);

        ComboBox<String> difficulty = new ComboBox<>();
        difficulty.setItems(FXCollections.observableArrayList("Easy", "Normal", "Hard"));
        difficulty.setPromptText("Select a difficulty.");
        difficulty.setPrefWidth(200.0);
        difficulty.setStyle("-fx-font-size: 14px;-fx-background-color: #666666;-fx-text-fill: #DEDEDE;");
        gridPane.add(difficulty, 0, 3);
        GridPane.setMargin(difficulty, new Insets(0, 20, 0, 0));

        Label boardSizeLabel = new Label("Board Size:");
        boardSizeLabel.setStyle("-fx-font-size: 14px;-fx-text-fill: #DEDEDE;");
        gridPane.add(boardSizeLabel, 0, 4);

        ComboBox<String> boardSize = new ComboBox<>();
        boardSize.setItems(FXCollections.observableArrayList("Small (24 x 24)", "Medium (24 x 26)", "Large (24 x 28)"));
        boardSize.setPromptText("Select a board size.");
        boardSize.setPrefWidth(200);
        boardSize.setStyle("-fx-font-size: 14px;-fx-background-color: #666666;-fx-text-fill: #DEDEDE;");
        gridPane.add(boardSize, 0, 5);
        GridPane.setMargin(boardSize, new Insets(0, 20, 0, 0));

        // This generates and positions a Label for each score in
        // localHS.get(). The styling is super complex
        // and I suggest you don't change it unless you have a solid grip on procedural
        // CSS styling.
        int rowIndex = 0;
        Label[][] names = new Label[HighScores.get().size()][2];

        ArrayList<Score> reversedArray = new ArrayList<>();
        for (int i = HighScores.get().size() - 1; i >= 0; i--) {
            reversedArray.add(HighScores.get().get(i));
        }

        for (Score s : reversedArray) {
            names[rowIndex][0] = new Label(s.playerHandle + ":");
            names[rowIndex][1] = new Label(String.valueOf(s.score));
            names[rowIndex][0].setStyle("-fx-font-size: 14px;-fx-text-fill: #DEDEDE;");
            names[rowIndex][0].setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                    BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                    CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));
            names[rowIndex][0].setPadding(new Insets(0, 5, 5, 5));
            names[rowIndex][0].setAlignment(Pos.CENTER_LEFT);
            names[rowIndex][0].setPrefWidth(100.0);
            names[rowIndex][1].setStyle("-fx-font-size: 14px;-fx-text-fill: #DEDEDE;");
            names[rowIndex][1].setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                    BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                    CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));
            names[rowIndex][1].setPadding(new Insets(0, 5, 5, 5));
            names[rowIndex][1].setAlignment(Pos.CENTER_LEFT);
            names[rowIndex][1].setPrefWidth(100.0);
            rowIndex++;
        }

        Label highscores = new Label("Highscores:");
        highscores.setStyle("-fx-font-size: 14px;-fx-text-fill: #DEDEDE;");
        gridPane.add(highscores, 1, 0);

        // This adds all the score labels to the board and accounts for the offset of
        // the highscore label.
        int offset = 1;
        for (Label[] name : names) {
            gridPane.add(name[0], 1, offset);
            gridPane.add(name[1], 2, offset);
            offset++;
        }

        Button launchGame = new Button("Start!");
        gridPane.add(launchGame, 0, 11); // position rel the bottom of the high scores.
        launchGame.setPrefSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        launchGame.setAlignment(Pos.BASELINE_CENTER);
        launchGame.setStyle("-fx-font-size: 14px;-fx-text-fill: #DADADA;-fx-background-color: #44AE52;");
        launchGame.setOnAction(event -> {
            int rowNum;
            int colNum;
            int difficultyNum;
            String userDiff = difficulty.getValue();
            String userName = nameField.getText();
            try {
                switch (userDiff) {
                case "Easy":
                    difficultyNum = 1;
                    break;
                case "Normal":
                    difficultyNum = 2;
                    break;
                case "Hard":
                    difficultyNum = 3;
                    break;
                default:
                    difficultyNum = 1;
                    break;
                }
            } catch (NullPointerException e) { // if the user forgets to select a difficulty
                difficultyNum = 1;
            }

            try {
                switch (boardSize.getValue()) {
                    case "Small (24 x 24)":
                        rowNum = 24;
                        colNum = 24;
                    break;
                    case "Medium (24 x 26)":
                        rowNum = 24;
                    colNum = 26;
                    break;
                    case "Large (24 x 28)":
                        rowNum = 24;
                    colNum = 28;
                    break;
                default:
                    rowNum = 24;
                    colNum = 24;
                }
            } catch (NullPointerException e) {
                rowNum = 24;
                colNum = 24;
            }

            // Launch game.
            ObstacleAndEnemyMap obstacleAndEnemyMap = new ObstacleAndEnemyMap(rowNum, colNum, difficultyNum);
            if (Objects.equals(userName, "")) {
                obstacleAndEnemyMap.getPlayer().setAnonUserHandle();
            } else {
                obstacleAndEnemyMap.getPlayer().setUserHandle(userName);
            }

            BoardWindow window = new BoardWindow(obstacleAndEnemyMap, difficultyNum);
            primaryStage.close();
            window.start(new Stage());
        });

        launchGame.setMinWidth(100);
        Scene scene = new Scene(gridPane, 600, 450);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}