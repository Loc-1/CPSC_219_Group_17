import javafx.scene.image.Image;

/**
 * A class for handling the PlayerSprite. Moves the player passed via method calls along the board.
 *
 * @author Josh Harkema
 */
class PlayerSprite extends Sprite {
    private final Image playerLeftImage;
    private final Image playerRightImage;

    /**
     * All player sprites MUST be constructed with existing Player instances.
     *
     * @param setPlayer the player to draw a sprite for.
     */
    PlayerSprite(Player setPlayer) {
        this.playerLeftImage = new Image("ch_left.png");
        this.playerRightImage = new Image("ch_right.png");
        super.setX(setPlayer.getCol());
        super.setY(setPlayer.getRow());
        super.setImage(this.playerLeftImage);
    }

    /**
     * Refresh the sprite's location relative the player instance's location.
     *
     * @param setPlayer the player's location in need of refresh.
     */
    void refresh(Player setPlayer) {
        super.setX(setPlayer.getCol());
        super.setY(setPlayer.getRow());
    }

    /**
     * Move the player to the right.
     *
     * @param setPlayer the player instance to move.
     */
    void moveRight(Player setPlayer) {
        setPlayer.moveRight();
        super.setImage(this.playerRightImage);
    }

    /**
     * Move the player to the left.
     *
     * @param setPlayer the player instance to move.
     */
    void moveLeft(Player setPlayer) {
        setPlayer.moveLeft();
        super.setImage(this.playerLeftImage);
    }

    /**
     * Move the player up.
     *
     * @param setPlayer the player instance to move.
     */
    void moveUp(Player setPlayer) {
        setPlayer.moveUp();
        super.setImage(this.playerRightImage);
    }

    /**
     * Move the player down.
     *
     * @param setPlayer the player instance to move.
     */
    void moveDown(Player setPlayer) {
        setPlayer.moveDown();
        super.setImage(this.playerLeftImage);
    }

}
