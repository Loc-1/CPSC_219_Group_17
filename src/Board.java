import java.awt.*;
import java.util.Arrays;

/**
 * This class is used to generate and store the board object. Calls to this class must include a valid frame width
 * and height.
 */
public class Board {
    private int width;
    private int height;
    private char[][] board; // This could be an array of ints, chars, whatever.

    /**
     * Constructor for new boards.
     *
     * @param setWidth  the desired board width.
     * @param setHeight the desired board height.
     */
    public Board(int setWidth, int setHeight) {
        this.width = setWidth;
        this.height = setHeight;
        if (this.width < 50 || this.height < 50) { // Temp error catching if.
            System.out.println("Width: " + setWidth);
            System.out.println("Height: " + setHeight);
            System.out.println("Width and height arguments must be greater than 50."); // :TODO: not this.
        } else {
            generate(); // If board dimensions are correct the constructor will call the generator.
        }
    }

    /**
     * The generator is called via the Board constructor. :TODO: integrate obstacle generation into this constructor.
     */
    private void generate() {
        int numRows = this.height / 50;
        int numColumns = this.width / 50;

        for (int row = 0; row <= numRows; row++) {
            for (int col = 0; col <= numColumns; col++) {
                this.board[row][col] = 'A';
            }
        }
    }

    public char[][] getBoard() { // :NOTE: DON'T ADD JAVADOCS TO GETTER/SETTER METHODS.
        return board;
    }

}
