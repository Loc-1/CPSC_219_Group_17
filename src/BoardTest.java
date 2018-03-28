import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    @Test
    public void isValidMove() throws Exception {
        ObstacleAndEnemyMap obstacleAndEnemyMap = new ObstacleAndEnemyMap(25, 21, 1);
        obstacleAndEnemyMap.getPlayer().setCoords(24, 15);
        // Set an arbitrary tile to X.
        obstacleAndEnemyMap.set(4, 4, true);
        obstacleAndEnemyMap.set(0, 1, false);
        obstacleAndEnemyMap.getPlayer().moveDown();
        obstacleAndEnemyMap.markTiles();

        // Ensure the previous tile is now marked as a bad move.
        boolean isNotValid = obstacleAndEnemyMap.isObstacle(0, 1);
        boolean isValid = obstacleAndEnemyMap.isObstacle(4, 4);

        assertFalse("Move is not valid.", isNotValid);
        assertTrue("Move is valid.", isValid);

    }

    @Test
    public void setBoard() throws Exception {
        ObstacleAndEnemyMap obstacleAndEnemyMap = new ObstacleAndEnemyMap(25, 21, 1);

        obstacleAndEnemyMap.setBoard(0, 0, '.');
        obstacleAndEnemyMap.setBoard(1, 1, 'X');

        assertEquals("Character doesn't equal '.' ", '.', obstacleAndEnemyMap.getTile(0, 0));
        assertEquals("Character doesn't equal 'X' ", 'X', obstacleAndEnemyMap.getTile(1, 1));

    }

    @Test
    public void movePlayer() throws Exception {
        ObstacleAndEnemyMap obstacleAndEnemyMap = new ObstacleAndEnemyMap(25, 21, 1);

        // Clear all obstacles from the board.
        for (int i = 0; i < obstacleAndEnemyMap.getRows(); i++) {
            for (int j = 0; j < obstacleAndEnemyMap.getColumns(); j++) {
                obstacleAndEnemyMap.set(i, j, false);
            }
        }

        obstacleAndEnemyMap.getPlayer().setCoords(5, 3);

        // Test moving Player Up.
        obstacleAndEnemyMap.getPlayer().moveUp();
        obstacleAndEnemyMap.markTiles();
        assertEquals("Player did not move Up.", 'P', obstacleAndEnemyMap.getTile(4, 3));

        // Test moving Player Right.
        obstacleAndEnemyMap.getPlayer().moveRight();
        obstacleAndEnemyMap.markTiles();
        assertEquals("Player did not move Right.", 'P', obstacleAndEnemyMap.getTile(4, 4));

        // Test moving Player Down.
        obstacleAndEnemyMap.getPlayer().moveDown();
        obstacleAndEnemyMap.markTiles();
        assertEquals("Player did not move Down.", 'P', obstacleAndEnemyMap.getTile(5, 4));

        // Test moving Player Left.
        obstacleAndEnemyMap.getPlayer().moveLeft();
        obstacleAndEnemyMap.markTiles();
        assertEquals("Player did not move Left.", 'P', obstacleAndEnemyMap.getTile(5, 3));

    }

}