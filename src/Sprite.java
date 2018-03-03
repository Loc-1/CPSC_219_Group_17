import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
    private final int tileWidthHeight = 32;
    private Image image;
    private int x;
    private int y;
    private boolean isColliadable;

    public Sprite() {
        x = 0;
        y = 0;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(this.image, this.x * tileWidthHeight, this.y * tileWidthHeight);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(this.x * tileWidthHeight, this.y * tileWidthHeight, tileWidthHeight, tileWidthHeight);
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