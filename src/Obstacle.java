import java.util.Random;

public class Obstacle {
    private int numColumns;
    private int numRows;
    private boolean[][] obstacleMap = new boolean[numRows][numColumns];
    private double difficulty;
    private double mapModifier = 0.45f; // :TODO: Remove this after testing.
    private int minGap; // This will set the minimum distance between obstacle objects. So many obstacles may be placed.

    public Obstacle(Board board, double difficulty) {
        this.difficulty = difficulty;
        this.minGap = calculateMinGap();
        this.numColumns = board.getColumns();
        this.numRows = board.getRows();

        for (int row = 0; row <= numRows; row++) {
            for (int col = 0; col <= numColumns; col++) {
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

}
