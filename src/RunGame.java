import java.util.concurrent.TimeUnit;

/**
 * Class Owner: Josh / Lachlan
 * <p>
 * This is the primary game loop, it constructs a new game (Board, Player, and BoardWindow.)
 */
public class RunGame {
    private Boolean isRunning = true;
    private BoardWindow boardWindow;
    private Board board;
    private Player playerOne;

    /**
     * Constructs a new game.
     *
     * @param rows       the desired number of rows.
     * @param cols       the desired number of columns.
     * @param difficulty the desired difficulty.
     * @param handle     the player's handle. :TODO: Add a second player.
     */
    public RunGame(int rows, int cols, int difficulty, String handle) {
        this.playerOne = new Player(rows-1,cols/2,0,0,handle);
        this.board = new Board(rows, cols, difficulty, this.playerOne);
        this.boardWindow = new BoardWindow(this.board);
    }

    public static void main(String[] args) {
        new RunGame(32, 32, 1, "");
    }

    public Boolean isRunning() {
        return this.isRunning;
    }

    public void runGame() {
        // Main loop, also sets score. Score is loosely tracked by the second.
        try {
            int count = 0; // Counter is needed to calculate score.
            
            while (this.playerOne.isAlive()) {
                this.isRunning = true;
                this.boardWindow.refresh();
                TimeUnit.MILLISECONDS.sleep(15);
                count++;
                if (count < 70) {
                    if (count == 69) {
                        this.playerOne.setScore(this.playerOne.getScore() + 1);
                        System.out.println(this.playerOne.getScore());
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
    }

    /**
     * Custom exception for when the board size is too small.
     */
    class SizeException extends Exception {
        SizeException(String setMessage) {
            super(setMessage);
        }
    }

    /**
     * Calling this method kills the BoardWindow.
     */
    public void endGame() {
        this.boardWindow.endGame();
    }

}