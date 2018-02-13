import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Class owner: Josh
 * <p>
 * Renders the game board.
 */
public class GameRender extends JFrame {
    private BufferStrategy BS;

    public GameRender() {
        this.createBufferStrategy(2);
        BS = this.getBufferStrategy();
    }

    public void drawBoard() {
        Graphics g = BS.getDrawGraphics();

        BS.show();
    }
}
