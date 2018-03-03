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

    public BoardWindow(Board board, Player player) {
        this.board = board;
        this.player = player;
    }

    public BoardWindow() {
        this.player = new Player(32 - 1, 26 / 2, 1, 1, "");
        this.board = new Board(32, 26, 1, player);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        scene = new Scene(root, this.board.getColumns() * 32, this.board.getRows() * 32);
        primaryStage.setScene(scene);

        loadGame();

        this.floorPane = new Pane();
        this.wallPane = new Pane();

        this.fillBackground();

        root.getChildren().add(this.floorPane);
        root.getChildren().add(this.wallPane);

        Canvas canvas = new Canvas(this.board.getColumns() * 32, this.board.getRows() * 32);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        playerSprite = new Sprite();
        playerSprite.setX(this.player.getCol());
        playerSprite.setY(this.player.getRow());
        playerSprite.setImage(playerImage);

        playerSprite.render(gc);

        primaryStage.show();

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

            }
        });

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, board.getColumns() * 32, board.getRows() * 32);
                playerSprite.setY(player.getRow());
                playerSprite.setX(player.getCol());
                playerSprite.render(gc);
            }
        };

        gameLoop.start();
    }

    private void fillBackground() {
        final int tileWidthHeight = 32;

        for (int col = 0; col < this.board.getColumns(); col++) {
            for (int row = 0; row < this.board.getRows(); row++) {
                ImageView i = new ImageView();
                switch (this.board.getTile(row, col)) {
                    case '.':
                        i.setImage(this.backgroundImage);
                        i.setX(col * tileWidthHeight);
                        i.setY(row * tileWidthHeight);
                        this.floorPane.getChildren().add(i);
                        break;

                    case 'X':
                        i.setImage(this.wallImage);
                        i.setX(col * tileWidthHeight);
                        i.setY(row * tileWidthHeight);
                        this.wallPane.getChildren().add(i);
                        break;

                    case 'P':
                        i.setImage(this.backgroundImage);
                        i.setX(col * tileWidthHeight);
                        i.setY(row * tileWidthHeight);
                        this.floorPane.getChildren().add(i);
                        break;

                    case 'E':
                        i.setImage(this.backgroundImage);
                        i.setX(col * tileWidthHeight);
                        i.setY(row * tileWidthHeight);
                        this.floorPane.getChildren().add(i);
                        break;
                }
            }
        }


    }

    private void loadGame() {
        playerImage = new Image("file:assets/player/base/deep_elf_m.png");
        backgroundImage = new Image("file:assets/dc-dngn/floor/dirt0.png");
        wallImage = new Image("file:assets/dc-dngn/wall/brick_dark0.png");
        enemyImage = new Image("file:assets/dc-mon/centaur.png");
    }

}
