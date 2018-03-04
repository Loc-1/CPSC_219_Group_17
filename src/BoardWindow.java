import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Josh
 * Renders the BoardWindow in JavaFX.
 */
public class BoardWindow extends Application {
    private Board board;
    private Player player;
    private Sprite playerSprite;

    private Pane floorPane = new Pane();
    private Pane wallPane = new Pane();

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
     * Default constructor renders a 32 * 26 tile board.
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

        this.fillBackground(); // Fills the background tiles with images.

        root.getChildren().add(this.floorPane);
        root.getChildren().add(this.wallPane);

        Canvas canvas = new Canvas(this.board.getColumns() * tileWidthHeight,
                this.board.getRows() * tileWidthHeight);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

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
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, board.getColumns() * tileWidthHeight,
                        board.getRows() * tileWidthHeight);
                playerSprite.setY(player.getRow());
                playerSprite.setX(player.getCol());
                playerSprite.render(gc);
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
        playerImage = new Image("file:assets/player/base/deep_elf_m.png");
        backgroundImage = new Image("file:assets/dc-dngn/floor/dirt0.png");
        wallImage = new Image("file:assets/dc-dngn/wall/brick_dark0.png");
        enemyImage = new Image("file:assets/dc-mon/centaur.png");
    }

}
