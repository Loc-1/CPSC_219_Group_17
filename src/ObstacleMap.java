import java.util.Random;

/**
 * Class is a procedurally generated map of interesting and traversable 'obstacles' represented as a 2d array of
 * primitive booleans.
 */
public class ObstacleMap {
    private int difficulty;
    private boolean[][] obstacleMap;
    private final int startingSafeZone = 2; // :TODO: Once I change this up I will not need it anymore. DEBUG
    private int minGap; // This will set the minimum distance between obstacle objects. So many obstacles may be placed.
    private double difficultyModifier = 0.35f; // :TODO: Remove this after testing. DEBUG

    public ObstacleMap(int setRows, int setColumns, int difficulty) {
        this.difficulty = difficulty;
        this.minGap = calculateMinGap();
        this.obstacleMap = new boolean[setRows][setColumns];

        for (int row = 0; row < setRows - startingSafeZone; row++) {
            for (int col = 0; col < setColumns; col++) {
                if (generateRandomDouble() < difficultyModifier) {
                    this.obstacleMap[row][col] = true;
                }
            }
        }
    }

    /**
     * This is only temporary...
     *
     * @return an integer defining the minimum spacing between obstacles.
     */
    private int calculateMinGap() {
        this.minGap = this.difficulty * 3;
        return this.minGap;
    }

    private double generateRandomDouble() {
        Random randomNum = new Random();
        return randomNum.nextDouble();
    }

    public boolean isObstacle(int row, int col) {
        return this.obstacleMap[row][col];
    }

    public boolean[][] getObstacleMap() {
        return obstacleMap;
    }

}
