import java.util.Arrays;

/**
 * This class is used to generate and store the board object. An obstacle map is automatically generated upon
 * instantiating a member of this class. Calls must include a valid frame width and height in units of 25x25 pixel
 * blocks.
 */
public class Board {
    private int rows;
    private int columns;
    private int difficulty;
    private char[][] board;
    private ObstacleMap obstacleMap;
    private Player player1;

    /**
     * Constructor builds a new board.
     *
     * @param setRows       desired number of rows.
     * @param setColumns    desired number of columns.
     * @param setDifficulty desired difficulty (0 = easy; 1 = medium; 2 = hard)
     */
    public Board(int setRows, int setColumns, int setDifficulty, Player setPlayer) {
        this.rows = setRows;
        this.columns = setColumns;
        this.difficulty = setDifficulty;
        this.board = new char[rows][columns];
        this.obstacleMap = new ObstacleMap(this.rows, this.columns, this.difficulty);

        if (this.rows < 0 || this.columns < 0) { // Temp error catching if.
            System.out.println("Width: " + setColumns);
            System.out.println("Height: " + setRows);
            System.out.println("Width and height arguments must be greater than 10."); // :TODO: not this.
        } else {
            for (int row = 0; row <= rows - 1; row++) { // Fill board with 0's
                for (int col = 0; col <= columns - 1; col++) {
                    this.board[row][col] = '.';
                    if (this.obstacleMap.isObstacle(row, col)) {
                        this.board[row][col] = 'X';
                    }
                }

                // This is only temporary.

            }
            this.board[setPlayer.getxLocation()][setPlayer.getyLocation()] = 'P';

        }

    }

    public static void main(String[] args) {
        final int rows = 50;
        final int cols = 51;

        Player testPlayer = new Player(rows - 1, cols / 2, 1, 1, "test");
        Board newBoard = new Board(rows, cols, 1, testPlayer);

        for (char[] row : newBoard.getBoard()) {
            System.out.println(Arrays.toString(row).replace(", ", " "));
        }
    }

    /**
     * Prints the board to console in a nicely formatted fashion.
     */
    public void printBoard() {
        for (char[] row : this.getBoard()) {
            System.out.println(Arrays.toString(row).replace(", ", " "));
        }
    }

    /**
     * @param xEnd destination x coord.
     * @param yEnd destination y coord.
     * @return true if move is valid.
     */
    public Boolean isValidMove(int xEnd, int yEnd) {
        Boolean isValid = false;
        if (!this.obstacleMap.isObstacle(xEnd, yEnd)) {
            isValid = true;
        }

        return isValid;
    }

    public char[][] getBoard() {
        return board;
    }

    public void setBoard(int x, int y, char setChar) {
        this.board[x][y] = setChar;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

}
