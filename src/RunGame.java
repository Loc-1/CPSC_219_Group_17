import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class RunGame extends Application {
    private int difficulty;
    private double run_time;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage primaryStage) {
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

    public void newSinglePlayer() {
    }

    public void newMultiPlayer() {
    }

    public void runTimer() {
    }

    public double getRunTime() {
        return 0.0;
    }

    public void end() {
    }
}
