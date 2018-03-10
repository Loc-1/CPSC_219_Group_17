import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author Josh
 * Custom class to handle the Sprites on the board.
 */
public class Sprite {
    private final int tileWidthHeight = 32;
    private Image image;
    private int x;
    private int y;
    private boolean isColliadable;

    /**
     * Initialize the sprite in the top right.
     */
    public Sprite() {
        x = 0;
        y = 0;
    }

    /**
     * This method draws the sprite.
     *
     * @param gc the graphics context to draw in.
     */
    public void render(GraphicsContext gc) {
        gc.drawImage(this.image, this.x * tileWidthHeight, this.y * tileWidthHeight);
    }

    /**
     * This method will be used for collision detection and player killing.
     *
     * @return a Rectangle bounding box.
     */
    public Bounds getBoundary() {
        return new BoundingBox(this.x * tileWidthHeight - tileWidthHeight, this.y * tileWidthHeight - tileWidthHeight, tileWidthHeight, tileWidthHeight);
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setColliadable(boolean colliadable) {
        isColliadable = colliadable;
    }
}
