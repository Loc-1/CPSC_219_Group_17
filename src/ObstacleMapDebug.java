import javax.swing.*;
import java.awt.*;


public class ObstacleMapDebug extends JPanel{


    private ObstacleMapDebug(Board setBoard,BoardWindow setWindow, Player setPlayer, ObstacleMap testObstacleMap){

        EventQueue.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            JFrame frame = new JFrame("Debug Tools");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(1, 5));

            JButton regenerate = new JButton("REGENERATE");
            regenerate.addActionListener(e -> {
                if (setPlayer.isAlive()) {
                    setBoard.resetObstacleMap(testObstacleMap, 1);
                    setBoard.refresh();
                    setWindow.refresh(setBoard);
                } else {
                    frame.dispose();
                    setWindow.endGame();
                }
            });

            JButton clear = new JButton("CLEAR");
            clear.addActionListener(e -> {
                if (setPlayer.isAlive()) {
                    setBoard.resetObstacleMap(testObstacleMap, 0);
                    setBoard.refresh();
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

            frame.add(regenerate);
            frame.add(clear);
            frame.add(exit);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setAlwaysOnTop(true);
            frame.setVisible(true);
        });

    }


    public static void main(String[] args) {

        Player testPlayer = new Player(25,32/2,1,1,"");
        Board testBoard = new Board(26,32,1, testPlayer);
        BoardWindow testWindow = new BoardWindow(testBoard);
        ObstacleMap testerObstacleMap = new ObstacleMap(26,32,1);
        new ObstacleMapDebug(testBoard, testWindow, testPlayer, testerObstacleMap);


    }




    }











