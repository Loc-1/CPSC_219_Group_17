/**
 * CreateGame is the init object.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.Timer;

import java.sql.Time;

public class CreateGame extends Application {
    private double difficulty;
    private Time run_time;
    private int number_of_players;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This acts as the main method for an JavaFX program.
     * :TODO: I only added this for demonstration and compile purposes. It can be entirely removed. (JOSH)
     *
     * @param primaryStage the starting canvas/stage.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    /**
     * Init a new single-player game.
     */
    public void newSinglePlayer() {
    }

    /**
     * Init a new two-player game.
     */
    public void newMultiPlayer() {
    }

    /**
     * @param difficulty {'0': 'Easy', '1': 'Medium', '2': 'hard', }
     */
    public void setDifficulty(int difficulty) {
    }


    public double getDifficulty() {
        return this.difficulty;
    }

    /**
     * @param players ('1': 'Single-player', '2': 'Two-player'}
     */
    public void setNumber_of_players(int players) {
    }

    /**
     * @return run_time as a Time object.
     */
    public Time getRunTime() {
        return Time.valueOf(LocalTime.now());
    }

    /**
     * Ends the game.
     */
    public void end() {
    }
}
