import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

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
            frame.setLayout(new GridLayout(1, 5));

            JButton up = new JButton("UP");
            up.addActionListener(e -> {
                if (setPlayer.isAlive()) {
                    setPlayer.moveUp();
                    setWindow.refresh(setBoard);
                } else {
                    frame.dispose();
                    setWindow.endGame();
                }
            });

            JButton down = new JButton("DOWN");
            down.addActionListener(e -> {
                if (setPlayer.isAlive()) {
                    setPlayer.moveDown();
                    setWindow.refresh(setBoard);
                } else {
                    frame.dispose();
                    setWindow.endGame();
                }
            });

            JButton left = new JButton("LEFT");
            left.addActionListener(e -> {
                if (setPlayer.isAlive()) {
                    setPlayer.moveLeft();
                    setWindow.refresh(setBoard);
                } else {
                    frame.dispose();
                    setWindow.endGame();
                }
            });

            JButton right = new JButton("RIGHT");
            right.addActionListener(e -> {
                if (setPlayer.isAlive()) {
                    setPlayer.moveRight();
                    setWindow.refresh(setBoard);
                } else {
                    frame.dispose();
                    setWindow.endGame();
                }
            });

            JButton exit = new JButton("EXIT");
            exit.addActionListener(e -> {
                frame.dispose();
                setWindow.endGame();
            });

            frame.add(up);
            frame.add(down);
            frame.add(left);
            frame.add(right);
            frame.add(exit);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setAlwaysOnTop(true);
            frame.setVisible(true);
        });

    }

    public static void main(String[] args) throws InterruptedException {
        Player testPlayer = new Player(31, 26 / 2, 1, 1, "");
        Board testBoard = new Board(32, 26, 1, testPlayer);
        BoardWindow testWindow = new BoardWindow(testBoard);
        new BoardWindowDebug(testBoard, testPlayer, testWindow);

        while (true) {
            testBoard.refresh();
            TimeUnit.MILLISECONDS.sleep(15);
        }
    }

}

