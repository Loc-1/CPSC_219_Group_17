import java.util.Arrays;

/**
 * This class is used to generate and store the board object. Calls to this class must include a valid frame width
 * and height.
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
    public Board(int setRows, int setColumns, int setDifficulty) {
        this.rows = setRows;
        this.columns = setColumns;
        this.difficulty = setDifficulty;
        this.board = new char[rows][columns];
        this.obstacleMap = new ObstacleMap(this.rows, this.columns, this.difficulty);
        this.player1 = new Player(setRows - 1, setColumns / 2, 0, 0, "test"); // Put player in middle of bottom row.

        if (this.rows < 0 || this.columns < 0) { // Temp error catching if.
            System.out.println("Width: " + setColumns);
            System.out.println("Height: " + setRows);
            System.out.println("Width and height arguments must be greater than 10."); // :TODO: not this.
        } else {
            for (int row = 0; row <= rows - 1; row++) { // Fill board with 0's
                for (int col = 0; col <= columns - 1; col++) {
                    this.board[row][col] = '0';
                    if (this.obstacleMap.isObstacle(row, col)) {
                        this.board[row][col] = 'X';
                    }
                }

                // This is only temporary.
                this.board[player1.getxLocation()][player1.getyLocation()] = 'P';
            }

        }

    }

    public static void main(String[] args) {
        Board newBoard = new Board(5, 10, 1);
        newBoard.setBoard(0, 0, 'a');
        System.out.println(Arrays.deepToString(newBoard.getBoard()));
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
