import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Class Owner: Josh / Lachlan
 * <p>
 * Renders the board into an awt GridBagLayout. Class must be instantiated with a board object.
 */
class BoardWindow {
    private TileMap tileMap;
    private Board board;
    private final JFrame frame;

    /**
     * Constructor builds out a new render from the board passed. Also adds a listener to the frame to manage keystroke
     * handling.
     *
     * @param setBoard the board to render.
     */
    BoardWindow(Board setBoard) {
        this.tileMap = new TileMap(setBoard);
        this.board = setBoard;
        this.frame = new JFrame("Group 17 Game");

        // This adds the key listener and moves the player. It also ensures the player isn't trying to move onto an
        // obstacle or outside the Board array.
        this.frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int key = e.getKeyCode();

                if (key == 39 || key == 68) {
                    if (board.isValidMove(board.getPlayerOne().getRow(), board.getPlayerOne().getCol() + 1)) {
                        EventQueue.invokeLater(() -> board.getPlayerOne().moveRight());
                    }
                } else if (key == 37 || key == 65) {
                    if (board.isValidMove(board.getPlayerOne().getRow(), board.getPlayerOne().getCol() - 1)) {
                        EventQueue.invokeLater(() -> board.getPlayerOne().moveLeft());
                    }
                } else if (key == 38 || key == 87) {
                    if (board.getPlayerOne().getRow() != 0 && board.isValidMove(
                            board.getPlayerOne().getRow() - 1, board.getPlayerOne().getCol())) {
                        EventQueue.invokeLater(() -> board.getPlayerOne().moveUp());
                    }
                } else if (key == 40 || key == 83) {
                    if (board.getPlayerOne().getRow() != setBoard.getRows() - 1 && board.isValidMove(
                            board.getPlayerOne().getRow() + 1, board.getPlayerOne().getCol())) {
                        EventQueue.invokeLater(() -> board.getPlayerOne().moveDown());
                    }
                }
            }

        });

        EventQueue.invokeLater(() -> {
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.add(tileMap);
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
            frame.remove(tileMap);
            this.tileMap = new TileMap(setBoard);
            frame.add(tileMap);
            frame.pack();
            frame.setVisible(true);
        });

    }

    /**
     * Ends the game. :TODO: Make this nice.
     */
    void endGame() {
        frame.remove(tileMap);
        JOptionPane.showConfirmDialog(null, "You Died", "Close",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        frame.dispose();
    }

    /**
     * Kills the GameWindow frame.
     */
    void trash() {
        this.frame.dispose();
    }


    /**
     * Class sets the TileMap params by creating a bunch of JPanel objects with different colours. When this is improved
     * to use tiles / sprites, this class can be updated to handle them.
     */
    private class TileMap extends JPanel {
        private final int tileSize = 25; // In pixels.
        final GridBagConstraints gbc = new GridBagConstraints();

        private TileMap(Board setBoard) {
            setLayout(new GridBagLayout());

            gbc.gridy = 0;
            for (int row = 0; row < setBoard.getRows(); row++) {
                gbc.gridx = 0;
                for (int col = 0; col < setBoard.getColumns(); col++) {
                    JPanel cell = new JPanel() {
                        @Override
                        public Dimension getPreferredSize() {
                            return new Dimension(tileSize, tileSize);
                        }
                    };
                    cell.setBackground(setColour(setBoard, row, col));
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
        private Color setColour(Board setBoard, int row, int col) {
            Color color = Color.BLUE;

            if (setBoard.getTile(row, col) == 'P') {
                color = Color.RED;
            } else if (setBoard.getTile(row, col) == 'X') {
                color = Color.BLACK;
            } else if (setBoard.getTile(row, col) == '.') {
                color = Color.WHITE;
            } else if (setBoard.getTile(row, col) == 'E') {
                color = Color.ORANGE;
            }

            return color;
        }
    }

}
