import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    @Test
    public void isValidMove() throws Exception {
        Player testPlayer = new Player(0, 0, "");
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
        assertFalse("Player is alive, player should be dead.", testPlayer.isAlive());

    }

    @Test
    public void setBoard() throws Exception {
        Player testPlayer = new Player(0, 0, "");
        Board testBoard = new Board(5, 5, 1, testPlayer);

        testBoard.setBoard(0, 0, '.');
        testBoard.setBoard(1, 1, 'X');

        assertEquals("Character doesn't equal '.' ", '.', testBoard.getTile(0, 0));
        assertEquals("Character doesn't equal 'X' ", 'X', testBoard.getTile(1, 1));

    }

    @Test
    public void movePlayer() throws Exception {
        Player testPlayer = new Player(5, 3, "");
        Board testBoard = new Board(6, 6, 1, testPlayer);

        // Clear all obstacles from the board.
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j <= 5; j++) {
                testBoard.setObstacleMap(i, j, false);
            }
        }

        // Test moving Player Up.
        testPlayer.moveUp();
        testBoard.refresh();
        assertEquals("Player did not move Up.", 'P', testBoard.getTile(4, 3));

        // Test moving Player Right.
        testPlayer.moveRight();
        testBoard.refresh();
        assertEquals("Player did not move Right.", 'P', testBoard.getTile(4, 4));

        // Test moving Player Down.
        testPlayer.moveDown();
        testBoard.refresh();
        assertEquals("Player did not move Down.", 'P', testBoard.getTile(5, 4));

        // Test moving Player Left.
        testPlayer.moveLeft();
        testBoard.refresh();
        assertEquals("Player did not move Left.", 'P', testBoard.getTile(5, 3));

    }

}