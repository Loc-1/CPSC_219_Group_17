import java.util.Arrays;
import pathfinding.*;


/**
 * Class Owner: Ashton / Lincoln
 *
 * This class is used to generate and store the board object. An obstacle map is automatically generated upon
 * instantiating a member of this class. Calls must include a valid frame width and height in units of 25x25 pixel
 * blocks, a valid difficulty, and at least one valid player object.
 */
public class Board {
    private int rows;
    private int columns;
    private int difficulty;
    private char[][] board;
    private ObstacleMap obstacleMap;
    private Player playerOne;
    private Player playerTwo;
    private Enemy enemies;

    /**
     * Constructor creates a single player board.
     *
     * @param setRows       desired number of rows.
     * @param setColumns    desired number of columns.
     * @param setDifficulty desired difficulty (0 = easy; 1 = medium; 2 = hard)
     * @param setPlayer     the player.
     */
    public Board(int setRows, int setColumns, int setDifficulty, Player setPlayer) {
        this.rows = setRows;
        this.columns = setColumns;
        this.difficulty = setDifficulty;
        this.board = new char[rows][columns];
        this.obstacleMap = new ObstacleMap(this.rows, this.columns, this.difficulty);
        this.playerOne = setPlayer;
        this.enemies = new Enemy(this.rows, this.columns);
        
        if (this.rows < 0 || this.columns < 0) { // Temp error catching if.
            System.out.println("Width: " + setColumns);
            System.out.println("Height: " + setRows);
            System.out.println("Width and height arguments must be greater than 0."); // :TODO: not this.
        } else {
            for (int row = 0; row < rows; row++) { // Fill board with 0's
                for (int col = 0; col < columns; col++) {
                    this.board[row][col] = '.';
                    if (this.obstacleMap.isObstacle(row, col)) {
                        this.board[row][col] = 'X';
                    }
                }
            }
            
            // Place the player
            this.board[playerOne.getxLocation()][playerOne.getyLocation()] = 'P';
            
            // :TODO: Try multiple times to place an enemy, check that end coordinates are also not on an obstacle
            //Place the enemies ensure they are not on an obstacle or the player
            if (Arrays.asList(this.obstacleMap.obstacleLocations()).contains(this.enemies.getstartCoords())) {
            	this.enemies = new Enemy(this.rows, this.columns);
            } else {
            	this.board[this.enemies.getstartCoords()[0]][this.enemies.getstartCoords()[1]] = 'E';
            }
        }

    }

    /**
     * Constructor creates a two-player board.
     *
     * @param setRows       desired number of rows.
     * @param setColumns    desired number of columns.
     * @param setDifficulty desired difficulty.
     * @param setPlayerOne  player one.
     * @param setPlayerTwo  player two.
     */
    public Board(int setRows, int setColumns, int setDifficulty, Player setPlayerOne, Player setPlayerTwo) {
        // :TODO: write code.
    }

    /**
     * Prints the board to console in a nicely formatted fashion.
     */
    public void printBoard() {
        for (char[] row : this.getBoard()) {
            System.out.println(Arrays.toString(row).replace(", ", " "));
        }
    }

    /**
     * @param xEnd destination x coord.
     * @param yEnd destination y coord.
     * @return true if move is valid.
     */
    public Boolean isValidMove(int xEnd, int yEnd) {
        Boolean isValid = true;
        if (this.obstacleMap.isObstacle(xEnd, yEnd)) {
            isValid = false;
        }

        return isValid;
    }
    
    /**
     * @param player specified player
     * @return true if player is dead.
     */
    public Boolean isPlayerDead(Player player) {
    	Boolean isDead = false;
    	return isDead;
    	// :TODO: Write some code about if player coordinates are same as enemy coordinates.
    }

    /**
     * Calling this method updates the player's location on the board. If the player moves into an obstacle, this
     * method sets the players status to isAlive = false.
     */
    public void refresh() {
        for (int row = 0; row < rows; row++) { // Fill board with 0's
            for (int col = 0; col < columns; col++) {
                if (this.obstacleMap.isObstacle(row, col)) {
                    this.board[row][col] = 'X';
                } else {
                    this.board[row][col] = '.';
                }
            }
        // :TODO: Replace enemies start coordinates with current coordinates once enemy has a path
        this.board[this.enemies.getstartCoords()[0]][this.enemies.getstartCoords()[1]] = 'E';
            
        }
        if ((this.enemies.getstartCoords()[0] == this.playerOne.getxLocation()) && (this.enemies.getstartCoords()[1] == this.playerOne.getyLocation())) {
        	this.playerOne.kill();
        } else {
            this.board[this.playerOne.getxLocation()][this.playerOne.getyLocation()] = 'P';

        }

    }

    /**
     * @param row the row to check.
     * @param col the col to check.
     * @return the symbol at (row, col).
     */
    public char getTile(int row, int col) {
        return this.board[row][col];
    }

    /**
     * @return the board as an char[][].
     */
    public char[][] getBoard() {
        return board;
    }

    /**
     * @param row     the row to set.
     * @param col     the col to set.
     * @param setChar the char to set.
     */
    public void setBoard(int row, int col, char setChar) {
        this.board[row][col] = setChar;
    }
    
    /**
     * Check if the board is traversable
     * Changes to the threshold modifier modify leniency on map traversability
     * @return True if the map is deemed traversable
     */
    public boolean isTraversable() {
    	int threshold = 900; // Decrease value to tighten restraints on traversability
    	boolean isTraversable = false;
        
        for (int i = 0; i < columns; i++) {
    	    int cost = pathfinding.AStar.aStarCost(rows, columns, playerOne.getxLocation(), playerOne.getyLocation(), 0, i, obstacleMap.obstacleLocations());
    	    if ((0 < cost) && (cost < threshold)) {
    	    	isTraversable = true;
    	    }
        }
        
        return isTraversable;
       
    }


    /**
     *
     * @param oldMap    the ObstacleMap to be overwritten
     * @param type      the type of overwrite action (0 == clear; 1 == regenerate)
     */

    public void resetObstacleMap(ObstacleMap oldMap, int type){

        if(type == 0) {
            for (int rowCount = 0; rowCount < rows; rowCount++) {
                for (int colCount = 0; colCount < columns; colCount++) {
                    this.obstacleMap.setObstacle(rowCount, colCount, false);
                }
            }
            this.refresh();
        }
        if(type == 1) {
            for (int rowCount = 0; rowCount < rows; rowCount++) {
                for (int colCount = 0; colCount < columns; colCount++) {
                    this.obstacleMap.setObstacle(rowCount, colCount, false);
                    this.obstacleMap.setObstacle(rowCount, colCount, oldMap.generateRandomDouble() < oldMap.calcDifficultyModifier(this.difficulty));
                }
            }
            this.refresh();
        }

    }











    /**
     * @param row         the row to set.
     * @param col         the col to set.
     * @param setObstacle true/false.
     */
    public void setObstacleMap(int row, int col, boolean setObstacle) {
        this.obstacleMap.setObstacle(row, col, setObstacle);
    }

    /**
     * @param difficulty the desired difficulty as an int.
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * @return the number of rows as an int.
     */
    public int getRows() {
        return rows;
    }

    /**
     * @return the number of cols as an it.
     */
    public int getColumns() {
        return columns;
    }
    
    /**
     * @return the obstacle map
     */
    public ObstacleMap getObstacleMap() {
    	return obstacleMap;
    }

}
