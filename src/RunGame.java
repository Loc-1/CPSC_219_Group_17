import java.util.concurrent.TimeUnit;

/**
 * Class Owner: Josh / Lachlan
 * <p>
 * This is the primary game loop, it constructs a new game (Board, Player, and BoardWindow.)
 */
public class RunGame {
    private Boolean isRunning;

    /**
     * Constructs a new board.
     *
     * @param rows       the desired number of rows.
     * @param cols       the desired number of columns.
     * @param difficulty the desired difficulty.
     * @param handle     the player's handle. :TODO: Add a second player.
     */
    public RunGame(int rows, int cols, int difficulty, String handle) {
        Player playerOne = new Player(rows - 1, cols / 2, 1, 1, handle);
        Board board = new Board(rows, cols, difficulty, playerOne);
        BoardWindow boardWindow = new BoardWindow(board);

        int count = 0;

        // Main loop, also sets score. Score is loosely tracked by the second.
        try {
            while (playerOne.isAlive()) {
                this.isRunning = true;
                boardWindow.refresh(board);
                TimeUnit.MILLISECONDS.sleep(15);
                count++;
                if (count < 70) {
                    if (count == 69) {
                        playerOne.setScore(playerOne.getScore() + 1);
                        System.out.println(playerOne.getScore());
                        count = 0;
                    }
                }
            }
        } catch (InterruptedException e) {
            this.isRunning = false;
            e.printStackTrace();
        }

        this.isRunning = false;
        boardWindow.endGame();
    }

    public static void main(String[] args) {
        RunGame game = new RunGame(32, 26, 1, "Josh");
    }

    public Boolean isRunning() {
        return this.isRunning;
    }

}