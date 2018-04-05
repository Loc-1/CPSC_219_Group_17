/**
 * Class Owner: Ashton / Lincoln
 * <p>
 * The player class handles the player's location on the board, as well as tracking the player's id, handle, and
 * current score. Player movement is handled via move methods defined in this class.
 */
public class Player {
    private Boolean isAlive = true;
    private String userHandle;

    private int col;
    private int row;
    private int score = 0;

    public Player(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Player(int col, int row, String userHandle) {
        this.userHandle = userHandle;
        this.col = col;
        this.row = row;
    }

    /**
     * A call to this method will 'kill' the player.
     */
    void kill() {
        this.isAlive = false;
    }

    void setCoords(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * This is used to set a default handle for when the player doesn't input one.
     */
    void setAnonUserHandle() {
        this.userHandle = "Anon";
    }

    int getCol() {
        return col;
    }

    int getRow() {
        return row;
    }

    Boolean isAlive() {
        return isAlive;
    }

    int getScore() {
        return score;
    }

    void setScore(int score) {
        this.score = score;
    }

    void moveUp() {
        this.row -= 1;
    }

    void moveDown() {
        this.row += 1;
    }

    void moveRight() {
        this.col += 1;
    }

    void moveLeft() {
        this.col -= 1;
    }

    void moveToZero(int setRow) {
        this.row = setRow;
    }

    String getUserHandle() {
        return userHandle;
    }

    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }
}
