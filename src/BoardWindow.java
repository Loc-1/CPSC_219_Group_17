import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.util.ArrayList;


/**
 * @author Josh
 * Renders the BoardWindow in JavaFX.
 */
public class BoardWindow extends Application {
    private final Board board;
    private final Player player;
    private final ArrayList<Sprite> enemySprites = new ArrayList<>();

    private int viewRows = 0;

    private Sprite playerSprite;

    private Pane floorPane;
    private Pane wallPane;
    private Pane scorePane;

    private Image playerLeftImage;
    private Image playerRightImage;
    private Image backgroundImage;
    private Image wallImage;
    private Image enemyImage;

    private final int tileWidthHeight = 32;
    private boolean firstRun = true;

    /**
     * Custom constructor.
     *
     * @param board  the board to render.
     * @param player the player to render.
     */
    BoardWindow(Board board, Player player, int setViewRows) {
        this.viewRows = setViewRows;
        this.board = board;
        this.player = player;

    }

    /**
     * Default constructor renders a 32 * 26 tile board. Makes debugging a lot easier. Can be removed once debugging
     * is finished--if you so desire.
     */
    public BoardWindow() {
        this.viewRows = 32;
        this.player = new Player(this.viewRows + 99, 26 / 2, 1, 1, "");
        this.board = new Board(this.viewRows + 100, 26, 1, player);

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
        Scene scene = new Scene(root, this.board.getColumns() * tileWidthHeight,
                this.viewRows * tileWidthHeight);

        // Add the camera and position it in the start zone.
        scene.setCamera(parallelCamera);
        parallelCamera.setClip(new Rectangle(this.board.getColumns() * tileWidthHeight,
                this.viewRows * tileWidthHeight));

        parallelCamera.relocate(0, (board.getRows() * tileWidthHeight) - (viewRows * tileWidthHeight));

        primaryStage.setScene(scene);
        primaryStage.setTitle("Run!!");

        loadGame(); // Loads all the images.

        this.floorPane = new Pane(); // Create panes to store the background (i.e. things that don't need to update.)
        this.wallPane = new Pane();
        this.scorePane = new Pane(); // Create the score pane.

        this.fillBackground(); // Fills the background tiles with images.

        // You can use CSS to style things! There IS a God!
        Label scoreLabel = new Label(String.valueOf(this.player.getScore()));
        scoreLabel.setStyle("-fx-background-color: rgba(150, 150, 150, 0.55); -fx-background-radius: 10;");
        scoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        scoreLabel.setTextFill(Color.BLACK);
        scoreLabel.setPadding(new Insets(5));

        // Add the padding to the background div.
        this.scorePane.setStyle("-fx-padding: 5;");
        this.scorePane.setLayoutY(parallelCamera.getLayoutY() + 5);
        this.scorePane.getChildren().add(scoreLabel);

        // Add everything to the root group of nodes.
        root.getChildren().add(this.floorPane);
        root.getChildren().add(this.wallPane);
        root.getChildren().add(this.scorePane);

        // Create a canvas and add it to the root group. This canvas is where the sprites are drawn.
        Canvas canvas = new Canvas(this.board.getColumns() * tileWidthHeight,
                this.board.getRows() * tileWidthHeight);

        root.getChildren().add(canvas);

        // Add the 2D graphicsContext to the canvas. Used to keep all the Sprites in the same context.
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Create a playerSprite and position it.
        this.playerSprite = new Sprite();
        this.playerSprite.setX(this.player.getCol());
        this.playerSprite.setY(this.player.getRow());
        this.playerSprite.setImage(playerRightImage);

        // Draw the player sprite on the initial board.
        this.playerSprite.render(gc);

        // Create an enemy Sprite for all enemies on the board.
        for (Enemy e : this.board.getEnemies()) {
            Sprite sprite = new Sprite();
            int[] coords = e.getStartCoords();
            sprite.setX(coords[0]);
            sprite.setY(coords[1]);
            sprite.setImage(this.enemyImage);
            this.enemySprites.add(sprite);
        }

        this.renderEnemySprites(gc);

        primaryStage.show();

        // Adds the key listeners and move validators.
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    if (this.board.isValidMove(player.getRow() - 1, player.getCol())) {
                        player.moveUp();
                    }
                    break;
                case DOWN:
                    if (this.board.isValidMove(player.getRow() + 1, player.getCol())) {
                        player.moveDown();
                    }
                    break;
                case LEFT:
                    if (this.board.isValidMove(player.getRow(), player.getCol() - 1)) {
                        player.moveLeft();
                        this.playerSprite.setImage(playerLeftImage);
                    }
                    break;
                case RIGHT:
                    if (this.board.isValidMove(player.getRow(), player.getCol() + 1)) {
                        player.moveRight();
                        this.playerSprite.setImage(playerRightImage);
                    }
                    break;
                case ESCAPE:
                    primaryStage.close();
            }

        });

        AnimationTimer gameLoop = new AnimationTimer() {
            int scoreCount = 0; // set score counter to 0 (each frame represents 1/60 of a second).
            int moveCount = 0;

            /**
             * This handler handles the continuous drawing of player sprites. Refresh is at (about) 60 fps.
             *
             * @param now the time.
             */
            @Override
            public void handle(long now) {
                // Clears the graphics context before drawing the new positions.
                gc.clearRect(0, 0, board.getColumns() * tileWidthHeight,
                        board.getRows() * tileWidthHeight);
                playerSprite.setY(player.getRow());
                playerSprite.setX(player.getCol());
                playerSprite.render(gc);
                renderEnemySprites(gc);

                // :TODO: vary this by difficulty.
                if (moveCount == 6) {
                    moveCameraUp(parallelCamera, primaryStage);
                    moveCount = 0;
                } else {
                    moveCount++;
                }

                // setLayoutX is needed to keep the label from falling off the side of the board. Five is subtracted
                // to account for the CSS padding already in place.
                scorePane.setLayoutX((board.getColumns() * tileWidthHeight) - (scorePane.getWidth() - 5));

                // This adds one to the player score (roughly) every second.
                if (scoreCount == 61) {
                    player.setScore(player.getScore() + 1);
                    scoreLabel.setText(String.valueOf(player.getScore()));
                    scoreCount = 0;

                    // Move the enemy sprites AFTER they've been rendered.
                    for (Enemy e : board.getEnemies()) {
                        e.move();
                    }

                }
                scoreCount++;
            }

        };

        gameLoop.start();
    }

    /**
     * Renders all the enemy sprites from the Enemy[] on the board.
     *
     * @param gc the canvas GraphicsContext where the Sprites are drawn.
     */
    private void renderEnemySprites(GraphicsContext gc) {
        for (Enemy e : this.board.getEnemies()) {
            for (Sprite s : this.enemySprites) {
                int[] coords = e.getCurrentCoords();
                s.setY(coords[0]);
                s.setX(coords[1]);
            }
        }

        // This is separate to prevent the 'flickering' of the enemy sprites.
        for (Sprite s : this.enemySprites) {
            s.render(gc);
        }
    }

    /**
     * Moves the camera up one pixel. Keeps the score pane centered and also deals with the player kill logic for
     * falling of the board.
     *
     * @param camera the camera to move up.
     * @param stage  the stage to close when the player dies.
     */
    private void moveCameraUp(Camera camera, Stage stage) {
        Translate translate = new Translate();
        translate.setY(camera.getClip().getLayoutY() - 1);
        double maxY = camera.localToScene(camera.getBoundsInLocal()).getMaxY();

        // the number of rows is subtracted by one so the player dies when the Sprite is 100% off the board.
        double minY = camera.localToScene(camera.getLayoutBounds()).getMaxY() + (this.viewRows - 1) * tileWidthHeight;
        this.scorePane.setLayoutY(maxY + 5); // 5 is used to add the padding.

        // Gives the player 4 seconds to orient before the board starts moving.
        if (this.firstRun) {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.firstRun = false;
            }
        }

        camera.getTransforms().add(translate);

        if (this.playerSprite.getBoundary().getMinY() > minY) {
            this.player.kill();
            stage.close();
        }

    }

    /**
     * Fills the background with tile images that can be traversed. (i.e. they show 'under' the sprites.)
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
                }
            }
        }

    }

    /**
     * Loads all the images into their instance vars. This speeds up the loads of subsequent background painting calls.
     */
    private void loadGame() {
        playerLeftImage = new Image("ch_left.png");
        playerRightImage = new Image("ch_right.png");
        backgroundImage = new Image("dc-dngn/floor/dirt0.png");
        wallImage = new Image("dc-dngn/wall/brick_dark0.png");
        enemyImage = new Image("dc-mon/centaur.png");
    }

}