/**
 * The player class handles the player's location on the board, as well as tracking the player's id, handle, and
 * current score. Player movement is handled via move methods defined in this class.
 */
public class Player {
    private int userID;
    private String userHandle;
    private int yLocation;
    private int xLocation;
    private int score = 0;
    private int sprite; // :TODO: Change to a sprite class once one is implemented.

    /***
     * Constructor to add player sprite to the board.
     * @param setXLocation player's starting x coordinate.
     * @param setYLocation player's starting y coordinate.
     * @param setSprite player's character sprite.
     * @param setUserHandle player's chosen handle.
     */
    public Player(int setXLocation, int setYLocation, int setSprite, String setUserHandle) {
        this.xLocation = setXLocation;
        this.yLocation = setYLocation;
        this.sprite = setSprite;
        this.userHandle = setUserHandle;
    }

    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }

    public void moveUp() {
        this.xLocation += 1;
    }

    public void moveDown() {
        this.xLocation -= 1;
    }

    public void moveRight() {
        this.yLocation += 1;
    }

    public void moveLeft() {
        this.yLocation -= 1;
    }

}
