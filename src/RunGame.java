import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

public class RunGame {
    private Board board;
    private Player playerOne;

    public RunGame(int rows, int cols, int difficulty, String handle) throws InterruptedException {
        this.playerOne = new Player(rows - 1, cols / 2, 1, 1, handle);
        this.board = new Board(rows, cols, difficulty, this.playerOne);

        BoardWindow boardWindow = new BoardWindow(this.board);

        while (this.playerOne.isAlive()) {
            boardWindow.refresh(this.board);
            TimeUnit.MILLISECONDS.sleep(150);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RunGame game = new RunGame(26, 32, 1, "Noone");
    }

    /**
     * Dispatches KeyEvents; makes sure the move doesn't move into an obstacle.
     *
     * @param e a KeyEvent listener.
     */
    private void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_RIGHT || key == 68) {
            if (this.board.isValidMove(this.playerOne.getxLocation(), this.playerOne.getyLocation() + 1)) {
                this.playerOne.moveRight();
            }
        } else if (key == KeyEvent.VK_LEFT || key == 65) {
            if (this.board.isValidMove(this.playerOne.getxLocation(), this.playerOne.getyLocation() - 1)) {
                this.playerOne.moveLeft();
            }
        } else if (key == KeyEvent.VK_UP || key == 83) {
            System.out.println("UP");
            if (this.board.isValidMove(this.playerOne.getxLocation() + 1, this.playerOne.getyLocation())) {
                this.playerOne.moveUp();
            }
        } else if (key == KeyEvent.VK_DOWN || key == 87) {
            if (this.board.isValidMove(this.playerOne.getxLocation() - 1, this.playerOne.getyLocation())) {
                this.playerOne.moveDown();
            }
        }
    }
}

