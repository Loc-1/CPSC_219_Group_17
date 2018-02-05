import java.util.Arrays;

/**
 * This class is used to generate and store the board object. Calls to this class must include a valid frame width
 * and height.
 */
public class Board {
    private int rows;
    private int columns;
    private char[][] board;

    public static void main(String[] args) {
        Board newBoard = new Board(10, 10);
        newBoard.setBoard(0, 0, 'a');
        System.out.println(Arrays.deepToString(newBoard.getBoard()));
    }

    /**
     * Constructor builds a new board.
     *
     * @param setRows    desired number of rows.
     * @param setColumns desired number of columns.
     */
    public Board(int setRows, int setColumns) {
        this.rows = setRows;
        this.columns = setColumns;
        this.board = new char[rows][columns];

        if (this.rows < 10 || this.columns < 10) { // Temp error catching if.
            System.out.println("Width: " + setColumns);
            System.out.println("Height: " + setRows);
            System.out.println("Width and height arguments must be greater than 10."); // :TODO: not this.
        } else {
            for (int row = 0; row <= rows - 1; row++) {
                for (int col = 0; col <= columns - 1; col++) {
                    this.board[row][col] = '.';
                }
            }
        }
    }

    /**
     * :TODO: This.
     *
     * @param xPlayer player's x-coord.
     * @param yPlayer player's y-coord.
     * @return True if move is valid.
     */
    public Boolean isValidMove(int xPlayer, int yPlayer) {
        return false;
    }

    public char[][] getBoard() {
        return board;
    }

    public void setBoard(int x, int y, char setChar) {
        this.board[x][y] = setChar;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

}
