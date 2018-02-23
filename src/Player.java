/**
 * Class Owner: Ashton / Lincoln
 *
 * The player class handles the player's location on the board, as well as tracking the player's id, handle, and
 * current score. Player movement is handled via move methods defined in this class.
 */
public class Player {
    private Boolean isAlive;
    private int userID;
    private String userHandle;
    private int col;
    private int row;
    private int score = 0;
    private int sprite; // :TODO: Change to a sprite class once one is implemented.

    /***
     * Constructor to add player sprite to the board.
     * @param setRow player's starting x coordinate.
     * @param setCol player's starting y coordinate.
     * @param setSprite player's character sprite.
     * @param setUserID either 0 for a single player game or 1 for a two player game.
     * @param setUserHandle player's chosen handle.
     */
    public Player(int setRow, int setCol, int setSprite, int setUserID, String setUserHandle) {
        this.isAlive = true; // New players start off alive.
        this.row = setRow;
        this.col = setCol;
        this.sprite = setSprite;
        this.userID = setUserID;
        this.userHandle = setUserHandle;
    }

    /**
     * A call to this method will 'kill' the player.
     */
    public void kill() {
        this.isAlive = false;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public Boolean isAlive() {
        return isAlive;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void moveUp() {
        this.row -= 1;
    }

    public void moveDown() {
        this.row += 1;
    }

    public void moveRight() {
        this.col += 1;
    }

    public void moveLeft() {
        this.col -= 1;
    }

    public String getUserHandle() {
        return userHandle;
    }
}
