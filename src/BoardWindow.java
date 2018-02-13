import javax.swing.*;
import java.awt.*;

/**
 * Class Owner: Josh
 *
 * Renders the board into an awt GridBagLayout. Class must be instantiated with a board object.
 */
public class BoardWindow {
    JFrame frame;
    /**
     * Constructor builds out a new render from the board passed.
     *
     * @param setBoard the board to render.
     */
    public BoardWindow(Board setBoard) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame = new JFrame("Group 17");
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.add(new Tile(setBoard));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        Player testPlayer = new Player(39, 75 / 2, 1, 1, "");
        Board testBoard = new Board(40, 75, 1, testPlayer);
        new BoardWindow(testBoard);
    }

    public void refresh(Board setBoard) {
        setBoard.refresh();
        frame.repaint();
    }

    /**
     * Class sets the tile params by creating a bunch of JPanel objects with different colours. When this is improved
     * to use tiles / sprites, this class can be updated to handle them.
     */
    private class Tile extends JPanel {
        private final int tileSize = 25; // In pixels.

        private Tile(Board board) {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridy = 0;

            for (int row = 0; row < board.getRows(); row++) {
                gbc.gridx = 0;
                for (int col = 0; col < board.getColumns(); col++) {
                    JPanel cell = new JPanel() {
                        @Override
                        public Dimension getPreferredSize() {
                            return new Dimension(tileSize, tileSize);
                        }
                    };
                    cell.setBackground(setColour(board, row, col));
                    add(cell, gbc);
                    gbc.gridx++;
                }
                gbc.gridy++;
            }

        }

        /**
         * @param row row to check.
         * @param col col to check.
         * @return a color object based on the char located at the row, col on the Board.
         */
        private Color setColour(Board board, int row, int col) {
            Color color = Color.BLUE;

            if (board.getTile(row, col) == 'P') {
                color = Color.RED;
            } else if (board.getTile(row, col) == 'X') {
                color = Color.BLACK;
            } else if (board.getTile(row, col) == '.') {
                color = Color.WHITE;
            }

            return color;
        }
    }

}
