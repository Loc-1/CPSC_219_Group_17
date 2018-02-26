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
    private JFrame frame;

    /**
     * Constructor builds out a new render from the board passed. Also adds a listener to the frame to manage keystroke
     * handling.
     *
     * @param setBoard the board to render.
     */
    BoardWindow(Board setBoard) {
        tileMap = new TileMap(setBoard);
        board = setBoard;
        frame = new JFrame("Group 17 Game");

        // This adds the key listener and moves the player. It also ensures the player isn't trying to move onto an
        // obstacle or outside the Board array. Ends game when escape is pressed.
        frame.addKeyListener(new KeyAdapter() {
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
                } else if (key == 27) {
                    endGame();
                }
            }

        });

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(tileMap);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }

    /**
     * Refresh the Window after something moves. Runs in the EventQueue to keep everything moving along nicely.
     *
     */
    void refresh() {
        board.refresh();
        frame.remove(tileMap);
        tileMap = new TileMap(board);
        frame.add(this.tileMap);
        frame.pack();
        frame.setVisible(true);

    }

    /**
     * Ends the game, if the player is alive, the player is killed. A nice message showing the player's name and final
     * score is shown.
     */
    void endGame() {
        this.frame.remove(tileMap);

        // Need to kill the player if the game is escaped manually. This stops the main game loop.
        if (this.board.getPlayerOne().isAlive()) {
            this.board.getPlayerOne().kill();
        }

        // Show a message with the players score.
        JOptionPane.showConfirmDialog(null,
                this.board.getPlayerOne().getUserHandle() + " died\nScore: "
                        + this.board.getPlayerOne().getScore(), "Game Over",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        frame.dispose();
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
