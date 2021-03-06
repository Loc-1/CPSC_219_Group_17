import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * @author Josh + Lachlan. Renders the BoardWindow in JavaFX.
 */
public class BoardWindow extends Application {
    private final int tileWidthHeight = 32; // Changing this number will have serious consequences. CHANGE WITH CAUTION.
    private final PlayerSprite playerSprite;

    private final int difficulty;
    private final ArrayList<EnemySprite> enemySprites;
    private ObstacleAndEnemyMap board;

    private Pane floorPane;
    private Pane wallPane;
    private Pane scorePane;
    private Pane countdownPane;

    private Image backgroundImage;
    private Image wallImage;
    private Image endImage;

    private Label countdownLabel;
    private int countdownTimer = 3; // The amount of time to delay before the game starts.
    private double moveRate;

    /**
     * Custom constructor.
     *
     * @param board  the board to render.
     */
    BoardWindow(ObstacleAndEnemyMap board, int difficulty) {
        this.board = board;
        this.playerSprite = new PlayerSprite(this.board.getPlayer());
        this.difficulty = difficulty;
        this.enemySprites = new ArrayList<>();
        this.setCameraMoveRate();
    }

    /**
     * Default constructor renders a 32 * 26 tile board. Makes debugging a lot
     * easier. Can be removed once debugging is finished--if you so desire.
     */
    @SuppressWarnings("unused")
    public BoardWindow() {
        this.difficulty = 1;
        this.board = new ObstacleAndEnemyMap(24, 24, this.difficulty);
        this.playerSprite = new PlayerSprite(this.board.getPlayer());
        this.enemySprites = new ArrayList<>();
        this.setCameraMoveRate();

    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The actual render code.
     *
     * @param primaryStage can be passed, if not constructor creates one.
     */
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        ParallelCamera parallelCamera = new ParallelCamera();

        // Set the scene size and add it to the primary stage.
        Scene scene = new Scene(root, this.board.getColumns() * tileWidthHeight, this.board.getVisibleRows() * tileWidthHeight);

        // Add the camera and position it in the start zone.
        scene.setCamera(parallelCamera);
        parallelCamera
                .setClip(new Rectangle(this.board.getColumns() * tileWidthHeight, this.board.getVisibleRows() * tileWidthHeight));
        parallelCamera.relocate(0, (board.getRows() * tileWidthHeight) - (this.board.getVisibleRows() * tileWidthHeight));

        primaryStage.setScene(scene);
        primaryStage.setTitle("Ascend");

        loadGame(); // Loads all the images.

        this.floorPane = new Pane(); // Create panes to store the background (i.e. things that don't need to update.)
        this.wallPane = new Pane();
        this.scorePane = new Pane();
        this.countdownPane = new Pane();

        this.fillBackground(); // Fills the background tiles with images.

        Label scoreLabel = new Label(String.valueOf(this.board.getPlayer().getScore()));
        scoreLabel.setStyle("-fx-background-color: rgba(150, 150, 150, 0.55); -fx-background-radius: 10;");
        scoreLabel.setFont(Font.font("Verdana", 25));
        scoreLabel.setTextFill(Color.BLACK);
        scoreLabel.setPadding(new Insets(5));

        // Add the padding to the background div.
        this.scorePane.setStyle("-fx-padding: 5;");
        this.scorePane.setLayoutY(parallelCamera.getLayoutY() + 5);
        this.scorePane.getChildren().add(scoreLabel);

        // Center the countdown pane.
        this.countdownPane.setLayoutY(
                parallelCamera.getLayoutY() + this.board.getVisibleRows() * tileWidthHeight / 2 - this.countdownPane.getHeight());
        this.countdownPane.setLayoutX(this.board.getColumns() * tileWidthHeight / 2 + this.countdownPane.getWidth());
        this.countdownPane.setStyle("-fx-padding: 5;");

        this.countdownLabel = new Label();
        this.countdownLabel.setText(String.valueOf(this.countdownTimer));
        this.countdownLabel.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        this.countdownLabel.setFont(Font.font("Verdana", 45));
        this.countdownLabel.setPadding(new Insets(5));
        this.countdownPane.getChildren().add(countdownLabel);

        // Add everything to the root group of nodes.
        root.getChildren().add(this.floorPane);
        root.getChildren().add(this.wallPane);
        root.getChildren().add(this.scorePane);
        root.getChildren().add(this.countdownPane);

        // Create a canvas and add it to the root group. This canvas is where the
        // sprites are drawn.
        Canvas canvas = new Canvas(this.board.getColumns() * tileWidthHeight, this.board.getRows() * tileWidthHeight);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Render the playerSprite for the initial render.
        this.playerSprite.render(gc);

        // Create an enemy Sprite for all enemies on the board.
        for (Enemy e : this.board.getEnemies()) {
            EnemySprite sprite = new EnemySprite(difficulty, e);
            this.enemySprites.add(sprite);
        }

        primaryStage.show();

        // Adds the key listeners and move validators. Player can't move until the game
        // begins.
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    if (this.board.isValidMove(this.board.getPlayer().getRow() - 1, this.board.getPlayer().getCol()) && countdownTimer <= 0) {
                        this.playerSprite.moveUp(this.board.getPlayer());
                    }
                    break;
                case W:
                    if (this.board.isValidMove(this.board.getPlayer().getRow() - 1, this.board.getPlayer().getCol()) && countdownTimer <= 0) {
                        this.playerSprite.moveUp(this.board.getPlayer());
                    }
                    break;
                case DOWN:
                    if (this.board.isValidMove(this.board.getPlayer().getRow() + 1, this.board.getPlayer().getCol()) && countdownTimer <= 0) {
                        this.playerSprite.moveDown(this.board.getPlayer());
                    }
                    break;
                case S:
                    if (this.board.isValidMove(this.board.getPlayer().getRow() + 1, this.board.getPlayer().getCol()) && countdownTimer <= 0) {
                        this.playerSprite.moveDown(this.board.getPlayer());
                    }
                    break;
                case LEFT:
                    if (this.board.isValidMove(this.board.getPlayer().getRow(), this.board.getPlayer().getCol() - 1) && countdownTimer <= 0) {
                        this.playerSprite.moveLeft(this.board.getPlayer());
                    }
                    break;
                case A:
                    if (this.board.isValidMove(this.board.getPlayer().getRow(), this.board.getPlayer().getCol() - 1) && countdownTimer <= 0) {
                        this.playerSprite.moveLeft(this.board.getPlayer());
                    }
                    break;
                case RIGHT:
                    if (this.board.isValidMove(this.board.getPlayer().getRow(), this.board.getPlayer().getCol() + 1) && countdownTimer <= 0) {
                        this.playerSprite.moveRight(this.board.getPlayer());
                    }
                    break;
                case D:
                    if (this.board.isValidMove(this.board.getPlayer().getRow(), this.board.getPlayer().getCol() + 1) && countdownTimer <= 0) {
                        this.playerSprite.moveRight(this.board.getPlayer());
                    }
                    break;
                case ESCAPE:
                    primaryStage.close();
                    for (EnemySprite e : enemySprites) {
                        e.stop();
                    }
            }
        });

        AnimationTimer gameLoop = new AnimationTimer() {
            int scoreCount = 0; // set score counter to 0 (each frame represents 1/60 of a second).

            /**
             * This handler handles the continuous drawing of player sprites. Refresh is at
             * (about) 60 fps.
             *
             * @param now the time.
             */
            @Override
            public void handle(long now) {
                if (!board.getPlayer().isAlive()) { // Kills the gameLoop and closes the stage when the player dies.
                    this.stop();
                    primaryStage.close();
                    for (EnemySprite e : enemySprites) {
                        e.stop();
                    }
                    endGamePopup();

                }

                if (board.getPlayer().getRow() == 0) { // Starts a new map when player reaches row 0.
                    this.stop();
                    primaryStage.close();
                    for (EnemySprite e : enemySprites) {
                        e.stop();
                    }

                    enemySprites.clear();
                    winGamePopup();

                }

                // Clears the graphics context before drawing the new positions.
                gc.clearRect(0, 0, board.getColumns() * tileWidthHeight, board.getRows() * tileWidthHeight);
                playerSprite.refresh(board.getPlayer());
                playerSprite.render(gc);
                renderEnemySprites(gc);

                if (countdownTimer == -1 && parallelCamera.getBoundsInParent().getMaxY() > 0) {
                    moveCameraUp(parallelCamera, root);
                }

                // setLayoutX is needed to keep the label from falling off the side of the
                // board. Five is subtracted to account for the CSS padding already in place.
                scorePane.setLayoutX((board.getColumns() * tileWidthHeight) - (scorePane.getWidth() - 5));

                // This uses the score counter to manage the countdown timer. Pauses the game
                // for ~4 seconds.
                if (countdownTimer != -1 && scoreCount == 60) {
                    scoreCount = 0;
                    countdownLabel.setText(String.valueOf(countdownTimer - 1));
                    if (countdownTimer == 0 || countdownTimer == 1) { // Recenter when the width changes.
                        countdownLabel.setText("GO!");
                        if (countdownTimer == 1) { // Prevents flickering.
                            countdownPane.setLayoutX(countdownPane.getLayoutX() - countdownPane.getWidth() / 2);
                        }
                    }
                    countdownTimer--;
                }

                // This adds one to the player score (roughly) every second and checks for
                // enemy/player collisions.
                if (scoreCount == 61) {
                    scoreCount = 0;
                    board.getPlayer().setScore(board.getPlayer().getScore() + 1);
                    scoreLabel.setText(String.valueOf(board.getPlayer().getScore()));
                    // This increases the move rate every ~1 min.
                    if (board.getPlayer().getScore() % 30 == 0 && moveRate != 0) {
                        moveRate += .25;
                    }
                } else {
                    scoreCount++;
                }
            }
        };
        gameLoop.start();

    }

    /**
     * Varies the starting camera move rate by difficulty.
     */
    private void setCameraMoveRate() {
        switch (difficulty) {
            case 1:
                this.moveRate = 0.75;
                break;
            case 2:
                this.moveRate = 1.00;
                break;
            case 3:
                this.moveRate = 1.25;
                break;
        }
    }

    /**
     * Resets all timers and generates a new map.
     */
    private void nextGame() {
        this.countdownTimer = 3;
        this.board = new ObstacleAndEnemyMap(this.board.getVisibleRows(), this.board.getColumns(),
                this.board.getDifficulty(), this.board.getPlayer());
        this.board.getPlayer().setCoords(this.board.getRows() - 1, this.board.getColumns() / 2);
        this.start(new Stage());
    }

    /**
     * The win game pop up. Button initiates a new game map.
     */
    private void winGamePopup() {
        final Stage winGame = new Stage();

        VBox dialogBox = new VBox(10);
        dialogBox.setAlignment(Pos.CENTER);
        dialogBox.setStyle("-fx-background-color: #2F2F2F;");

        Label message = new Label("You win! \n\nClick to continue to the next stage.");
        message.setStyle("-fx-font-size: 16px;-fx-text-fill: #DEDEDE; -fx-text-alignment: center;");
        dialogBox.getChildren().add(message);

        Region separator = new Region();
        separator.setMinHeight(10);
        dialogBox.getChildren().add(separator);

        Button next = new Button("Continue");
        next.setStyle("-fx-font-size: 16px;-fx-text-fill: #DADADA;-fx-background-color: #44AE52;");
        next.setPrefWidth(100);
        next.setOnAction(event -> {
            winGame.close();
            this.nextGame();
        });
        dialogBox.getChildren().add(next);

        Scene winGameScene = new Scene(dialogBox, 300, 200);
        winGame.setScene(winGameScene);
        winGame.show();
    }

    /**
     * Snazzy pop-up shows player score and handle, used to not jarringly just close
     * the game. Adds score to high scores (it only sticks if the score is actually
     * a high score.)
     */
    private void endGamePopup() {
        final Stage endGame = new Stage();

        HighScores.add(this.board.getPlayer().getUserHandle(), this.board.getPlayer().getScore());

        VBox dialogBox = new VBox(10);
        dialogBox.setAlignment(Pos.CENTER);
        dialogBox.setStyle("-fx-background-color: #2F2F2F;");

        Label playerName = new Label("Sorry, " + this.board.getPlayer().getUserHandle() + ", you died.");
        playerName.setStyle("-fx-font-size: 16px;-fx-text-fill: #DEDEDE;");
        dialogBox.getChildren().add(playerName);

        Label playerScore = new Label("Your score was: " + this.board.getPlayer().getScore());
        playerScore.setStyle("-fx-font-size: 16px;-fx-text-fill: #DEDEDE;");
        dialogBox.getChildren().add(playerScore);

        Label isHighScore = new Label();
        if (HighScores.isHighScore(this.board.getPlayer().getScore())) {
            isHighScore.setText("You set a new high score!");
        } else {
            isHighScore.setText("Sorry, that's not a new high score.");
        }

        isHighScore.setStyle("-fx-font-size: 16px; -fx-text-fill: #DEDEDE");
        dialogBox.getChildren().add(isHighScore);

        Region separator = new Region();
        separator.setMinHeight(10);
        dialogBox.getChildren().add(separator);

        Button close = new Button("Try Again");
        close.setStyle("-fx-font-size: 16px;-fx-text-fill: #DADADA;-fx-background-color: #44AE52;");
        close.setPrefWidth(100);
        close.setOnAction(event -> {
            endGame.close();
            for (EnemySprite e : enemySprites) {
                e.stop();
            }
            GameWindow gameWindow = new GameWindow();
            gameWindow.start(new Stage());
        });
        dialogBox.getChildren().add(close);

        Scene endGameScene = new Scene(dialogBox, 300, 200);
        endGame.setScene(endGameScene);
        endGame.show();
    }

    /**
     * Renders all the enemy sprites from the Enemy[] on the board. Kills the player when the player touches an enemy's
     * boundary.
     *
     * @param gc the canvas GraphicsContext where the Sprites are drawn.
     */
    private void renderEnemySprites(GraphicsContext gc) {
        for (EnemySprite s : this.enemySprites) {
            s.render(gc);
            if (s.getBoundary().intersects(playerSprite.getBoundary())) {
                this.board.getPlayer().kill();
                for (EnemySprite s2 : this.enemySprites) {
                    s2.stop();
                }
            }
        }

    }

    /**
     * Moves the camera up one pixel. Keeps the score pane centered and also deals
     * with the player kill logic for falling of the board.
     *
     * @param camera the camera to move up.
     */
    private void moveCameraUp(Camera camera, Group root) {
        try {
            root.getChildren().remove(this.countdownPane);
            Translate translate = new Translate();
            translate.setY(camera.getClip().getLayoutY() - this.moveRate);
            double maxY = camera.localToScene(camera.getBoundsInLocal()).getMinY();

            // the number of rows is subtracted by one so the player dies when the Sprite is
            // 100% off the board.
            double minY = camera.localToScene(camera.getLayoutBounds()).getMaxY()
                    + (this.board.getVisibleRows() - 1) * tileWidthHeight;
            this.scorePane.setLayoutY(maxY + 5); // 5 is used to add the padding.

            camera.getTransforms().add(translate);

            // Yes, this is the easiest kill logic ever.
            if (this.playerSprite.getBoundary().getMinY() > minY || this.playerSprite.getBoundary().getMaxY() < maxY) {
                this.board.getPlayer().kill();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Fills the background with tile images that can be traversed. (i.e. they show
     * 'under' the sprites.)
     */
    private void fillBackground() {
        for (int col = 0; col < this.board.getColumns(); col++) {
            for (int row = 0; row < this.board.getRows(); row++) {
                ImageView i = new ImageView();
                i.setX(col * tileWidthHeight);
                i.setY(row * tileWidthHeight);

                switch (this.board.getTile(row, col)) {
                    case '.': // Floor
                        i.setImage(this.backgroundImage);
                        this.floorPane.getChildren().add(i);
                        break;
                    case 'X': // Wall
                        i.setImage(this.wallImage);
                        this.wallPane.getChildren().add(i);
                        break;
                    case 'P': // Player tiles still have floor backgrounds.
                        i.setImage(this.backgroundImage);
                        this.floorPane.getChildren().add(i);
                        break;
                    case 'E': // Enemy tiles still have floor backgrounds.
                        i.setImage(this.backgroundImage);
                        this.floorPane.getChildren().add(i);
                        break;
                    case 'F':
                        i.setImage(this.endImage);
                        this.floorPane.getChildren().add(i);
                        break;
                }
            }
        }

    }

    /**
     * Loads all the images into their instance vars. This speeds up the loads of
     * subsequent background painting calls.
     */
    private void loadGame() {
        this.backgroundImage = new Image("floor.png");
        this.wallImage = new Image("brick_dark0.png");
        this.endImage = new Image("CheckerBoardImage.png");

    }

}