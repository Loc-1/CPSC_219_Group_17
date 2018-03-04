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


/**
 * @author Josh
 * Renders the BoardWindow in JavaFX.
 */
public class BoardWindow extends Application {
    private Board board;
    private Player player;
    private Sprite playerSprite;

    private Pane floorPane;
    private Pane wallPane;
    private Pane scorePane;

    private Image playerImage;
    private Image backgroundImage;
    private Image wallImage;
    private Image enemyImage;

    private Scene scene;

    private final int tileWidthHeight = 32;

    /**
     * Custom constructor.
     *
     * @param board  the board to render.
     * @param player the player to render.
     */
    public BoardWindow(Board board, Player player) {
        this.board = board;
        this.player = player;
    }

    /**
     * Default constructor renders a 32 * 26 tile board. Makes debugging a lot easier.
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
     * @param primaryStage from javafx.application
     */
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        scene = new Scene(root, this.board.getColumns() * tileWidthHeight,
                this.board.getRows() * tileWidthHeight);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Run!!");

        loadGame(); // Loads all the images.

        this.floorPane = new Pane(); // Adds the background panes to the scene.
        this.wallPane = new Pane();
        this.scorePane = new Pane();

        this.fillBackground(); // Fills the background tiles with images.

        // You can use CSS to style things! There IS a God!
        Label scoreLabel = new Label(String.valueOf(this.player.getScore()));
        scoreLabel.setStyle("-fx-background-color: rgba(150, 150, 150, 0.55); -fx-background-radius: 10;");
        scoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        scoreLabel.setTextFill(Color.BLACK);
        scoreLabel.setPadding(new Insets(10));

        // Add the padding to the background div.
        this.scorePane.setStyle("-fx-padding: 5;");
        this.scorePane.setLayoutY(5);
        this.scorePane.getChildren().add(scoreLabel);

        // Add everything to the root node.
        root.getChildren().add(this.floorPane);
        root.getChildren().add(this.wallPane);
        root.getChildren().add(this.scorePane);

        // Create a canvas and add all the root groups nodes to it.
        Canvas canvas = new Canvas(this.board.getColumns() * tileWidthHeight,
                this.board.getRows() * tileWidthHeight);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Create a playerSprite and position it.
        playerSprite = new Sprite();
        playerSprite.setX(this.player.getCol());
        playerSprite.setY(this.player.getRow());
        playerSprite.setImage(playerImage);

        playerSprite.render(gc);

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

        // Sets the gameLoop as an Animation Timer. Only repaints the player tiles. Once the enemies are added
        // they will be refreshed too. :TODO: add enemies once they're fixed up.
        AnimationTimer gameLoop = new AnimationTimer() {
            int scoreCount = 0;

            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, board.getColumns() * tileWidthHeight,
                        board.getRows() * tileWidthHeight);
                playerSprite.setY(player.getRow());
                playerSprite.setX(player.getCol());
                playerSprite.render(gc);

                // setLayoutX is needed to keep the label from falling off the side of the board. Five is subtracted
                // to account for the CSS padding already in place.
                scorePane.setLayoutX((board.getColumns() * tileWidthHeight) - (scorePane.getWidth() - 5));
                scoreLabel.setText(String.valueOf(player.getScore()));

                // This adds one to the player score (roughly) every second.
                if (scoreCount == 60) {
                    player.setScore(player.getScore() + 1);
                    scoreLabel.setText(String.valueOf(player.getScore()));
                    scoreCount = 0;
                }

                scoreCount++;
            }

        };

        gameLoop.start();
    }

    /**
     * Fills the background with tile images that can be traversed. (i.e. they show 'under' the sprites.)
     */
    private void fillBackground() {
        for (int col = 0; col < this.board.getColumns(); col++) {
            for (int row = 0; row < this.board.getRows(); row++) {
                ImageView i = new ImageView();
                switch (this.board.getTile(row, col)) {
                    case '.': // Floor
                        i.setImage(this.backgroundImage);
                        i.setX(col * tileWidthHeight);
                        i.setY(row * tileWidthHeight);
                        this.floorPane.getChildren().add(i);
                        break;

                    case 'X': // Wall
                        i.setImage(this.wallImage);
                        i.setX(col * tileWidthHeight);
                        i.setY(row * tileWidthHeight);
                        this.wallPane.getChildren().add(i);
                        break;

                    case 'P': // Player tiles still have floor backgrounds.
                        i.setImage(this.backgroundImage);
                        i.setX(col * tileWidthHeight);
                        i.setY(row * tileWidthHeight);
                        this.floorPane.getChildren().add(i);
                        break;

                    case 'E': // Enemy tiles still have floor backgrounds.
                        i.setImage(this.backgroundImage);
                        i.setX(col * tileWidthHeight);
                        i.setY(row * tileWidthHeight);
                        this.floorPane.getChildren().add(i);
                        break;
                }
            }
        }

    }

    /**
     * Loads all the images into their instance vars.
     */
    private void loadGame() {
        playerImage = new Image("player/base/deep_elf_m.png");
        backgroundImage = new Image("dc-dngn/floor/dirt0.png");
        wallImage = new Image("dc-dngn/wall/brick_dark0.png");
        enemyImage = new Image("dc-mon/centaur.png");
    }

}