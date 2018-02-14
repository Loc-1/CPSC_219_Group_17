import javax.swing.*;
import java.awt.*;

/**
 * Class Owner: Josh
 * <p>
 * Renders the board into an awt GridBagLayout. Class must be instantiated with a board object.
 */
class BoardWindow {
    private TimeMap timeMapMap;
    private final JFrame frame;

    /**
     * Constructor builds out a new render from the board passed.
     *
     * @param setBoard the board to render.
     */
    BoardWindow(Board setBoard) {
        this.timeMapMap = new TimeMap(setBoard);
        this.frame = new JFrame("Group 17 Game");

        EventQueue.invokeLater(() -> {
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.add(timeMapMap);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

    }

    /**
     * Refresh the Window after something moves. Runs in the EventQueue to keep everything moving along nicely.
     *
     * @param setBoard the board to re-render.
     */
    void refresh(Board setBoard) {
        EventQueue.invokeLater(() -> {
            setBoard.refresh();
            frame.remove(timeMapMap);
            this.timeMapMap = new TimeMap(setBoard);
            frame.add(timeMapMap);
            frame.pack();
            frame.setVisible(true);
        });

    }

    /**
     * Ends the game. :TODO: Make this nice.
     */
    void endGame() {
        frame.remove(timeMapMap);
        JOptionPane.showConfirmDialog(null, "You Died", "Close",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        frame.dispose();
    }

    /**
     * Class sets the TileMap params by creating a bunch of JPanel objects with different colours. When this is improved
     * to use tiles / sprites, this class can be updated to handle them.
     */
    private class TimeMap extends JPanel {
        private final int tileSize = 25; // In pixels.
        final GridBagConstraints gbc = new GridBagConstraints();

        private TimeMap(Board board) {
            setLayout(new GridBagLayout());
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
