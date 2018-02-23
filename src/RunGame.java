import java.util.concurrent.TimeUnit;

/**
 * This is the primary game loop, it constructs a new game (Board, Player, and BoardWindow.)
 */
public class RunGame {
    private Board board;
    private Player playerOne;
    private Boolean isRunning = true;

    /**
     * Constructs a new board.
     *
     * @param rows       the desired number of rows.
     * @param cols       the desired number of columns.
     * @param difficulty the desired difficulty.
     * @param handle     the player's handle. :TODO: Add a second player.
     * @throws InterruptedException if the while loop is interrupted.
     */
    public RunGame(int rows, int cols, int difficulty, String handle) throws InterruptedException {
        this.playerOne = new Player(rows - 1, cols / 2, 1, 1, handle);
        this.board = new Board(rows, cols, difficulty, this.playerOne);

        BoardWindow boardWindow = new BoardWindow(this.board);

        while (this.playerOne.isAlive()) {
            boardWindow.refresh(this.board);
            TimeUnit.MILLISECONDS.sleep(15);
        }

        this.isRunning = false;
        boardWindow.trash();

    }

    public static void main(String[] args) throws InterruptedException {
        RunGame game = new RunGame(26, 32, 1, "Noone");
    }

    public Boolean isRunning() {
        return this.isRunning;
    }

}