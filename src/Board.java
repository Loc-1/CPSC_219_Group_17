import java.util.Arrays;

/**
 * This class is used to generate and store the board object. An obstacle map is automatically generated upon
 * instantiating a member of this class. Calls must include a valid frame width and height in units of 25x25 pixel
 * blocks, a valid difficulty, and at least one valid player object.
 */
public class Board {
    private int rows;
    private int columns;
    private int difficulty;
    private char[][] board;
    private ObstacleMap obstacleMap;
    private Player playerOne;
    private Player playerTwo;

    /**
     * Constructor creates a single player board.
     *
     * @param setRows       desired number of rows.
     * @param setColumns    desired number of columns.
     * @param setDifficulty desired difficulty (0 = easy; 1 = medium; 2 = hard)
     * @param setPlayer     the player.
     */
    public Board(int setRows, int setColumns, int setDifficulty, Player setPlayer) {
        this.rows = setRows;
        this.columns = setColumns;
        this.difficulty = setDifficulty;
        this.board = new char[rows][columns];
        this.obstacleMap = new ObstacleMap(this.rows, this.columns, this.difficulty);
        this.playerOne = setPlayer;


        if (this.rows < 0 || this.columns < 0) { // Temp error catching if.
            System.out.println("Width: " + setColumns);
            System.out.println("Height: " + setRows);
            System.out.println("Width and height arguments must be greater than 0."); // :TODO: not this.
        } else {
            for (int row = 0; row < rows; row++) { // Fill board with 0's
                for (int col = 0; col < columns; col++) {
                    this.board[row][col] = '.';
                    if (this.obstacleMap.isObstacle(row, col)) {
                        this.board[row][col] = 'X';
                    }
                }

                // This is only temporary.

            }
        }

    }

    /**
     * Constructor creates a two-player board.
     *
     * @param setRows       desired number of rows.
     * @param setColumns    desired number of columns.
     * @param setDifficulty desired difficulty.
     * @param setPlayerOne  player one.
     * @param setPlayerTwo  player two.
     */
    public Board(int setRows, int setColumns, int setDifficulty, Player setPlayerOne, Player setPlayerTwo) {
        // :TODO: write code.
    }

    public static void main(String[] args) {
        final int rows = 15;
        final int cols = 25;

        Player testPlayer = new Player(rows - 1, cols / 2, 1, 1, "test");
        Board testBoard = new Board(rows, cols, 1, testPlayer);

        // This tests if the player can be moved.
        testBoard.printBoard();
        testPlayer.moveUp();
        testPlayer.moveLeft();
        testBoard.refresh();
        System.out.println("");
        testBoard.printBoard();
        testBoard.setBoard(12, 11, 'X');
        testPlayer.moveUp();
        testBoard.refresh();
        System.out.println("");
        testBoard.printBoard();
        System.out.println(testPlayer.getAlive());

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
        Boolean isValid = true;
        if (this.obstacleMap.isObstacle(xEnd, yEnd)) {
            isValid = false;
        }

        return isValid;
    }

    /**
     * Calling this method updates the player's location on the board.
     */
    public void refresh() {
        for (int row = 0; row < rows; row++) { // Fill board with 0's
            for (int col = 0; col < columns; col++) {
                if (this.obstacleMap.isObstacle(row, col)) {
                    this.board[row][col] = 'X';
                } else {
                    this.board[row][col] = '.';
                }
            }
        }
        if (!this.isValidMove(this.playerOne.getxLocation(), this.playerOne.getyLocation())) {
            this.playerOne.kill();
        } else {
            this.board[this.playerOne.getxLocation()][this.playerOne.getyLocation()] = 'P';

        }

    }

    public char[][] getBoard() {
        return board;
    }

    public void setBoard(int x, int y, char setChar) {
        this.board[x][y] = setChar;
    }

    public void setObstacleMap(int row, int col, boolean setObstacle) {
        this.obstacleMap.setObstacle(row, col, setObstacle);
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
