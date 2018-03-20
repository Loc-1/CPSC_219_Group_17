import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author Josh
 * Custom class to handle the Sprites on the board.
 */
class Sprite {
    private final int tileWidthHeight = 32;
    private Image image;
    private int x;
    private int y;

    /**
     * Initialize the sprite in the top right.
     */
    Sprite() {
        x = 0;
        y = 0;
    }

    /**
     * This method draws the sprite.
     *
     * @param gc the graphics context to draw in.
     */
    void render(GraphicsContext gc) {
        gc.drawImage(this.image, this.x * tileWidthHeight - 1, this.y * tileWidthHeight - 1);
    }

    /**
     * This method will be used for collision detection and player killing. One pixel is subtracted so the enemies
     * don't kill until they've moved on-top of the player.
     *
     * @return a Rectangle bounding box.
     */
    Bounds getBoundary() {
        return new BoundingBox(this.x * tileWidthHeight - tileWidthHeight - 1,
                this.y * tileWidthHeight - tileWidthHeight - 1, tileWidthHeight - 1, tileWidthHeight - 1);
    }

    void setImage(Image image) {
        this.image = image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
