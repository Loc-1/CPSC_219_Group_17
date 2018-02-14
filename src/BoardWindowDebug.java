import javax.swing.*;
import java.awt.*;

/**
 * Class Owner - Josh
 * <p>
 * Debug tool class for debugging the board window headlessly.
 */
public class BoardWindowDebug extends JPanel {

    private BoardWindowDebug(Board setBoard, Player setPlayer, BoardWindow setWindow) {
        EventQueue.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            JFrame frame = new JFrame("Debug Tools");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(1, 4));

            JButton up = new JButton("UP");
            up.addActionListener(e -> {
                setPlayer.moveUp();
                if (setPlayer.isAlive()) {
                    setWindow.refresh(setBoard);
                } else {
                    frame.dispose();
                    setWindow.endGame();
                }
            });

            JButton down = new JButton("DOWN");
            down.addActionListener(e -> {
                setPlayer.moveDown();
                if (setPlayer.isAlive()) {
                    setWindow.refresh(setBoard);
                } else {
                    frame.dispose();
                    setWindow.endGame();
                }
            });

            JButton left = new JButton("LEFT");
            left.addActionListener(e -> {
                setPlayer.moveLeft();
                if (setPlayer.isAlive()) {
                    setWindow.refresh(setBoard);
                } else {
                    frame.dispose();
                    setWindow.endGame();
                }
            });

            JButton right = new JButton("RIGHT");
            right.addActionListener(e -> {
                setPlayer.moveRight();
                if (setPlayer.isAlive()) {
                    setWindow.refresh(setBoard);
                } else {
                    frame.dispose();
                    setWindow.endGame();
                }
            });

            frame.add(up);
            frame.add(down);
            frame.add(left);
            frame.add(right);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setAlwaysOnTop(true);
            frame.setVisible(true);
        });

    }

    public static void main(String[] args) {
        Player testPlayer = new Player(25, 32 / 2, 1, 1, "");
        Board testBoard = new Board(26, 32, 1, testPlayer);
        BoardWindow testWindow = new BoardWindow(testBoard);
        new BoardWindowDebug(testBoard, testPlayer, testWindow);
    }

}

