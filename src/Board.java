import java.util.Arrays;
import java.util.Random;

/**
 * Class Owner: Ashton / Lincoln
 * <p>
 * This class is used to generate and store the board object. An obstacle map is automatically generated upon
 * instantiating a member of this class. Calls must include a valid frame width and height in units of 25x25 pixel
 * blocks, a valid difficulty, and at least one valid player object.
 */
abstract class Board {
    private int rows;
    private int columns;
    private int difficulty;
    private int visibleRows;
    private char[][] board;

    /**
     * Constructor creates a single player board.
     *
     * @param setRows        desired number of rows.
     * @param setColumns     desired number of columns.
     * @param setDifficulty  desired difficulty (0 = easy; 1 = medium; 2 = hard)
     * @param setVisibleRows the number of visible rows for the BoardWindow.
     */
    Board(int setRows, int setColumns, int setDifficulty, int setVisibleRows) {
        this.rows = setRows;
        this.columns = setColumns;
        this.difficulty = setDifficulty;
        this.visibleRows = setVisibleRows;
        this.board = new char[this.rows][this.columns];

        if (this.rows < 0 || this.columns < 0) { // Temp error catching if.
            System.out.println("Width: " + setColumns);
            System.out.println("Height: " + setRows);
            System.out.println("Width and height arguments must be greater than 0."); // :TODO: not this.
        }

    }

    /**
     * @return a random double.
     */
    static double generateRandomDouble() {
        Random randomNum = new Random();
        return randomNum.nextDouble();
    }

    /**
     * Prints the board to console in a nicely formatted fashion. Needed for debugging.
     */
    @SuppressWarnings("unused")
    void printBoard() {
        for (char[] row : this.getBoard()) {
            System.out.println(Arrays.toString(row).replace(", ", " "));
        }
    }

    char getTile(int row, int col) {
        return this.board[row][col];
    }

    private char[][] getBoard() {
        return board;
    }

    void setBoard(int row, int col, char setChar) {
        this.board[row][col] = setChar;
    }

    int getColumns() {
        return columns;
    }

    int getRows() {
        return rows;
    }

    int getDifficulty() {
        return difficulty;
    }

    int getVisibleRows() {
        return visibleRows;
    }

}
