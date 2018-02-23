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

}