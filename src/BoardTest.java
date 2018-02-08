import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    @Test
    public void isValidMove() throws Exception {
        Player testPlayer = new Player(0, 0, 1, 1, "");
        Board testBoard = new Board(5, 5, 1, testPlayer);

        // Set an arbitrary tile to X.
        testBoard.setObstacleMap(0, 1, true);
        testBoard.setObstacleMap(4, 4, false);
        testPlayer.moveDown();
        testBoard.refresh();

        // Ensure the previous tile is now marked as a bad move.
        boolean isNotValid = testBoard.isValidMove(0, 1);
        boolean isValid = testBoard.isValidMove(4, 4);

        assertFalse("Move is not valid.", isNotValid);
        assertTrue("Move is valid.", isValid);
        assertFalse("Player is alive, player should be dead.", testPlayer.getAlive());

        testBoard.printBoard();

    }

    @Test
    public void setBoard() throws Exception {
        Player testPlayer = new Player(0, 0, 1, 1, "");
        Board testBoard = new Board(5, 5, 1, testPlayer);

        testBoard.setBoard(0, 0, '.');

        char[][] boardArr = testBoard.getBoard();

        assertEquals("Character doesn't equal '.' ", '.', boardArr[0][0]);

    }

}