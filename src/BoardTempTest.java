public class BoardTempTest {
    public static void main(String[] args) {
        ObstacleAndEnemyMap obstacleAndEnemyMap = new ObstacleAndEnemyMap(8, 20, 2);
        obstacleAndEnemyMap.printBoard();

        System.out.println("\n-----------------\n");

        obstacleAndEnemyMap.extendBoard();
        obstacleAndEnemyMap.printBoard();

    }
}
