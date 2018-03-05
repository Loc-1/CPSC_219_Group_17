import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    private Sprite playerSprite;

    private Pane floorPane;
    private Pane wallPane;
    private Pane scorePane;

    private Image playerImage;
    private Image backgroundImage;
    private Image wallImage;
    private Image enemyImage;

    private final int tileWidthHeight = 32;

    /**
     * Custom constructor.
     *
     * @param board  the board to render.
     * @param player the player to render.
     */
    BoardWindow(Board board, Player player) {
        this.board = board;
        this.player = player;

    }

    /**
     * Default constructor renders a 32 * 26 tile board. Makes debugging a lot easier. Can be removed once debugging
     * is finished--if you so desire.
     */
    public BoardWindow() {
        this.player = new Player(32 - 1, 26 / 2, 1, 1, "");
        this.board = new Board(32, 26, 1, player);
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

        // Set the scene size and add it to the primary stage.
        Scene scene = new Scene(root, this.board.getColumns() * tileWidthHeight,
                this.board.getRows() * tileWidthHeight);

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
        this.scorePane.setLayoutY(5);
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
        this.playerSprite.setImage(playerImage);

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
                    }
                    break;
                case RIGHT:
                    if (this.board.isValidMove(player.getRow(), player.getCol() + 1)) {
                        player.moveRight();
                    }
                    break;
                case ESCAPE:
                    primaryStage.close();
            }

        });

        AnimationTimer gameLoop = new AnimationTimer() {
            int scoreCount = 0; // set score counter to 0 (each frame represents 1/60 of a second).

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
        playerImage = new Image("player/base/deep_elf_m.png");
        backgroundImage = new Image("dc-dngn/floor/dirt0.png");
        wallImage = new Image("dc-dngn/wall/brick_dark0.png");
        enemyImage = new Image("dc-mon/centaur.png");
    }

}