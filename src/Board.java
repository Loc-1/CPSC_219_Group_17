import java.awt.*;
import java.util.Arrays;

public class Board {
    private int width;
    private int height;
    private char[][] board;

    public Board(int setWidth, int setHeight) {
        this.width = setWidth;
        this.height = setHeight;
        if (this.width < 50 || this.height < 50) {
            System.out.println("Width: " + setWidth);
            System.out.println("Height: " + setHeight);
            System.out.println("Width and height arguments must be greater than 50."); // :TODO: not this.
        } else {
            generate(); // If board dimensions are correct the constructor will call the generator.
        }
    }

    private void generate() {
        int numRows = this.height / 50;
        int numColumns = this.width / 50;

        for (int row = 0; row <= numRows; row++) {
            for (int col = 0; col <= numColumns; col++) {
                this.board[row][col] = 'A';
            }
        }
    }

    public char[][] getBoard() {
        return board;
    }

}
