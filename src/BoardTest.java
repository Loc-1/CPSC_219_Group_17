import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    @Test
    public void isValidMove() throws Exception {
        Player testPlayer = new Player(0, 0, 1, 1, "");
        Board testBoard = new Board(5, 5, 1, testPlayer);

        // Set an arbitrary tile to X.
        testBoard.setBoard(0, 1, 'X');

        // Ensure the previous tile is now marked as a bad move.
        boolean isValid = testBoard.isValidMove(0, 1);

        assertFalse("Move is not valid", isValid);
    }

}