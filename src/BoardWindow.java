import javax.swing.*;
import java.awt.*;

/**
 * Renders the board into an awt GridBagLayout. Class must be instantiated with a board object.
 */
class BoardWindow extends JPanel {

    /**
     * Constructor builds out a new render from the board passed.
     *
     * @param setBoard the board to render.
     */
    public BoardWindow(Board setBoard) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Group 17");
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.add(new Tile(setBoard));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        Player testPlayer = new Player(31, 26 / 2, 1, 1, "");
        Board testBoard = new Board(32, 26, 1, testPlayer);
        new BoardWindow(testBoard);
    }

    /**
     * Class sets the tile params by creating a bunch of JPanel objects with different colours. When this is improved
     * to use tiles / sprites, this class can be updated to handle them.
     */
    class Tile extends JPanel {

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
                            return new Dimension(25, 25);
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
