import java.util.Random;

public class ObstacleMap {
    private int numColumns;
    private int numRows;
    private int difficulty;
    private boolean[][] obstacleMap;
    private double mapModifier = 0.45f; // :TODO: Remove this after testing.
    private int minGap; // This will set the minimum distance between obstacle objects. So many obstacles may be placed.

    private final int startingSafeZone = 2; // :TODO: Once I change this up I will not need it anymore.

    public ObstacleMap(int setRows, int setColumns, int difficulty) {
        this.difficulty = difficulty;
        this.minGap = calculateMinGap();
        this.numColumns = setColumns;
        this.numRows = setRows;
        this.obstacleMap = new boolean[this.numRows][this.numColumns];

        for (int row = 0; row <= numRows - (1 + startingSafeZone); row++) {
            for (int col = 0; col <= numColumns - 1; col++) {
                if (generateRandomDouble() < mapModifier) {
                    this.obstacleMap[row][col] = true;
                }
            }
        }
    }

    private int calculateMinGap() {
        this.minGap = (int) this.difficulty * 3; // (int) is used to round to even numbers.
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
