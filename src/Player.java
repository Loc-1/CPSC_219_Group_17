/**
 * Class Owner: Ashton / Lincoln
 * <p>
 * The player class handles the player's location on the board, as well as tracking the player's id, handle, and
 * current score. Player movement is handled via move methods defined in this class.
 */
public class Player {
    private Boolean isAlive;
    private String userHandle;

    private int col;
    private int row;
    private int score = 0;

    /***
     * Constructor to add player sprite to the board.
     * @param setRow player's starting x coordinate.
     * @param setCol player's starting y coordinate.
     * @param setUserHandle player's chosen handle.
     */
    Player(int setRow, int setCol, String setUserHandle) {
        this.isAlive = true; // New players start off alive.
        this.row = setRow;
        this.col = setCol;
        this.userHandle = setUserHandle;
    }

    /**
     * A call to this method will 'kill' the player.
     */
    void kill() {
        this.isAlive = false;
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

    @SuppressWarnings("unused")
    public void setCol(int col) {
        this.col = col;
    }

    int getRow() {
        return row;
    }

    @SuppressWarnings("unused")
    public void setRow(int row) {
        this.row = row;
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

    String getUserHandle() {
        return userHandle;
    }

}
