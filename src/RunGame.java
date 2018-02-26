import java.util.concurrent.TimeUnit;

/**
 * Class Owner: Josh / Lachlan
 * <p>
 * This is the primary game loop, it constructs a new game (Board, Player, and BoardWindow.)
 */
public class RunGame {
    private Boolean isRunning;
    private BoardWindow boardWindow;

    /**
     * Constructs a new game.
     *
     * @param rows       the desired number of rows.
     * @param cols       the desired number of columns.
     * @param difficulty the desired difficulty.
     * @param handle     the player's handle. :TODO: Add a second player.
     */
    public RunGame(int rows, int cols, int difficulty, String handle) {
        Player playerOne;
        Board board;

        if (rows > 20 && cols > 20) {
            playerOne = new Player(rows - 1, cols / 2, 1, 1, handle);
            board = new Board(rows, cols, difficulty, playerOne);
            this.boardWindow = new BoardWindow(board);
        } else {
            try {
                throw new SizeException("Invalid number of rows and columns, both must be greater than 20.");
            } catch (SizeException e) {
                e.printStackTrace();
                return; // cheating to end the loop.
            }

        }

        // Main loop, also sets score. Score is loosely tracked by the second.
        try {
            int count = 0; // Counter is needed to calculate score.

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
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        this.isRunning = false;
        boardWindow.endGame();

    }

    public static void main(String[] args) {
        new RunGame(32, 26, 1, "Josh");
    }

    /**
     * Calling this method kills the BoardWindow.
     */
    public void endGame() {
        this.boardWindow.endGame();
    }

    public Boolean isRunning() {
        return this.isRunning;
    }

    /**
     * Custom exception for when the board size is too small.
     */
    class SizeException extends Exception {
        SizeException(String setMessage) {
            super(setMessage);
        }
    }
}