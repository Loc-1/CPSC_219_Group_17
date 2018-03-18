import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
    private Enemy[] enemies;
    private int numOfEnemies;
    private int visibleRows;
    private AstarMap traversability;
    
    private List <char[][]> quadrants; // Divide the board up into quadrants for enemy spawns? Think about how to do it. Scrap this for me.
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
        this.traversability = new AstarMap();
        this.playerOne = setPlayer;
        this.numOfEnemies = 2;
        this.enemies = new Enemy[numOfEnemies];
        
        this.visibleRows = 32;

        //makes sure map is traversable before acting on it
        while(traversability.test(this.rows, this.columns, playerOne.getRow(), playerOne.getCol(), 0,this.columns/2, this.obstacleMap.obstacleLocations()) < 0) {
            this.obstacleMap = new ObstacleMap(this.rows, this.columns, this.difficulty);
        }
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
                this.board[playerOne.getRow()][playerOne.getCol()] = 'P';

                // Place all enemies
                
                int numOfVisibleBoards = setRows / visibleRows;
                for (int visibleBoardIndx = 0; visibleBoardIndx < numOfVisibleBoards; visibleBoardIndx++) {
                	int visibleBoardStart = visibleBoardIndx * visibleRows;
                	int visibleBoardEnd = (visibleBoardIndx+1) * visibleRows;
                	
                    for (int enemyNum = 0; enemyNum < numOfEnemies; enemyNum++) {
                        enemies[enemyNum] = new Enemy();
                        enemyStart(enemies[enemyNum], visibleBoardStart, visibleBoardEnd);
                        enemyEnd(enemies[enemyNum], visibleBoardStart, visibleBoardEnd);
                        enemyPath(enemies[enemyNum]);

                        this.board[enemies[enemyNum].getCurrentCoords()[0]][enemies[enemyNum].getCurrentCoords()[1]] = 'E';
                    }
                }
            }
            
    }
    
    /**
     * Overloaded constructor with visible rows use this once board is polished
     * @param setRows			Desired number of rows
     * @param setColumns		Desired number of columns
     * @param setDifficulty		Desired difficulty (0 - easy, 1 - medium, 2 - hard)
     * @param setPlayer			the player.
     * @param setVisibleRows	the displayed rows
     */
    public Board(int setRows, int setColumns, int setDifficulty, Player setPlayer, int setVisibleRows) {
        this.rows = setRows;
        this.columns = setColumns;
        this.difficulty = setDifficulty;
        this.board = new char[rows][columns];
        this.obstacleMap = new ObstacleMap(this.rows, this.columns, this.difficulty);
        this.traversability = new AstarMap();
        this.playerOne = setPlayer;
        this.numOfEnemies = setDifficulty; // Creates 1, 2, or 3 enemies for each visible board.
        this.enemies = new Enemy[numOfEnemies];
        
        
        // I want to set the total rows as a constant multiple of visible rows to make looping through each visible board easier. 
        // Would be better to not have setVisible rows as a param. Just checking for now.
        int numOfVisibleBoards = (setRows / setVisibleRows); 
        this.visibleRows = setRows / numOfVisibleBoards;
        


        //makes sure map is traversable before acting on it
        while(traversability.test(this.rows, this.columns, playerOne.getRow(), playerOne.getCol(), 0,this.columns/2, this.obstacleMap.obstacleLocations()) < 0) {
            this.obstacleMap = new ObstacleMap(this.rows, this.columns, this.difficulty);
        }
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
                this.board[playerOne.getRow()][playerOne.getCol()] = 'P';

                // Place all enemies
                
                
                for (int visibleBoardIndx = 0; visibleBoardIndx < numOfVisibleBoards; visibleBoardIndx++) {
                	int visibleBoardStart = visibleBoardIndx * visibleRows;
                	int visibleBoardEnd = (visibleBoardIndx+1) * visibleRows;

                    for (int enemyNum = 0; enemyNum < numOfEnemies; enemyNum++) {
                        enemies[enemyNum] = new Enemy();
                        enemyStart(enemies[enemyNum], visibleBoardStart, visibleBoardEnd);
                        enemyEnd(enemies[enemyNum], visibleBoardStart, visibleBoardEnd);
                        enemyPath(enemies[enemyNum]);

                        this.board[enemies[enemyNum].getCurrentCoords()[0]][enemies[enemyNum].getCurrentCoords()[1]] = 'E';
                    }
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
     * @param rowEnd destination x coord.
     * @param colEnd destination y coord.
     * @return true if move is valid.
     */
    public Boolean isValidMove(int rowEnd, int colEnd) {
        Boolean isValid = true;
        if (this.obstacleMap.isObstacle(rowEnd, colEnd)) {
            isValid = false;
        }

        return isValid;
    }

 

    /**
     * Calling this method updates the player's location on the board. If the player moves into an enemy, this
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
            
            

        }
        
        for (int enemyNum = 0; enemyNum < numOfEnemies; enemyNum++) {
        	enemies[enemyNum].move();
            this.board[this.enemies[enemyNum].getCurrentCoords()[0]][this.enemies[enemyNum].getCurrentCoords()[1]] = 'E';
        }
        
        // Kill conditions
        if ((this.enemies[0].getStartCoords()[0] == this.playerOne.getRow()) && (this.enemies[0].getStartCoords()[1] == this.playerOne.getCol())) {
            this.playerOne.kill();
        } else {
            this.board[this.playerOne.getRow()][this.playerOne.getCol()] = 'P';

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
            int cost = pathfinding.AStar.aStarCost(rows, columns, playerOne.getRow(), playerOne.getCol(), 0, i, obstacleMap.obstacleLocations());
            if ((0 < cost) && (cost < threshold)) {
                isTraversable = true;
            }
        }

        return isTraversable;

    }

    
    /**
     * Determines a valid start position for enemies within the start and end rows
     * @param aenemy 				The enemy to set the start positions
     * @param int set startRows		The minimum row where the enemy should spawn
     * @param int set endRows		The maximum row where the enemy should spawn
     */
    public void enemyStart(Enemy aenemy, int startRows, int endRows) {
    	int[][] freeTiles = this.obstacleMap.nonObstacleLocations(startRows, endRows);
    	
    	
    	int[] randstartCoords = freeTiles[(int) (generateRandomDouble() * freeTiles.length)];
    	
    	aenemy.setStartLocation(randstartCoords[0], randstartCoords[1]);
    	aenemy.setCurrentLocation(aenemy.getStartCoords()[0], aenemy.getStartCoords()[1]);
    }
    
    
    /**
     * Determines a valid end position for enemies based off the A* algorithm
     * Changing the constraints of minTravelCost and maxTravelCost allow enemy to choose 
     * further or closer end points
     * @param aenemy 		The enemy to set the end position 
     * @param startRows		The minimum rows where the enemy should spawn
     * @param endRows 		The maximum rows where the enemy should spawn
     */
    public void enemyEnd(Enemy aenemy, int startRows, int endRows) {
    	int minTravelCost = 90;
    	int maxTravelCost = 300;
    	

    	int[] endCoords = pathfinding.AStar.chooseDestination(this.rows, this.columns, aenemy.getStartCoords()[0], 
    			aenemy.getStartCoords()[1],this.getPlayerOne().getRow(), this.getPlayerOne().getCol(), 
    			this.getObstacleMap().obstacleLocations(), minTravelCost, maxTravelCost);
    	
    	// If there are no possible end coordinates to choose, rechoose the start coordinates.
    	if (endCoords == null) {
    		enemyStart(aenemy, startRows, endRows);
    		enemyEnd(aenemy, startRows, endRows);
    	} else {
    	aenemy.setEndLocation(endCoords[0], endCoords[1]);
    	}
    }
    
    /**
     * Determines a valid path for the enemy based off start and end points and the board. 
     * @param aenemy The enemy to set the path
     * @param board The board where the enemy will be placed
     */
    public void enemyPath(Enemy aenemy) {
    	List<int[]> enemyPath = pathfinding.AStar.pathfinding(this.getRows(), this.getColumns(), aenemy.getStartCoords()[0], 
    			aenemy.getStartCoords()[1], aenemy.getEndCoords()[0], aenemy.getEndCoords()[1], 
    			this.getObstacleMap().obstacleLocations());
    	
    	aenemy.setPath(enemyPath);
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
     * @return the number of visible rows.
     */
    public int getvisibleRows() {
    	return visibleRows; 
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

    public Player getPlayerOne() {
        return playerOne;
    }
    
    public Enemy[] getEnemies() {
        return enemies;
    }
    
    public int getNumOfEnemies() {
    	return numOfEnemies; 
    }
    
    /**
     * @return the difficulty level
     */
    public int getDifficulty() {
    	return this.difficulty;
    }
    /**
     * 
     * @return a random double.
     */
    public static double generateRandomDouble() {
        Random randomNum = new Random();
        return randomNum.nextDouble();
    }
    
    // Testing
    //Testing
    public static void main(String[] args) {
    	Player player = new Player(32- 1, 26 / 2, 1, 1, "");
    	Board board = new Board(32, 26, 1, player, 8);
    	
    	board.printBoard();
    	//System.out.println(Arrays.deepToString(board.getObstacleMap().nonObstacleLocations(2, 12)));

    	Enemy[] allEnemies = board.getEnemies();
  	
    	for (int i = 0; i < board.getNumOfEnemies(); i++) {
    		List<int[]> paths = allEnemies[i].getPath();
    		
    		System.out.println(Arrays.deepToString(paths.toArray()));
    	}
    	
    	board.refresh();
    	board.printBoard();
    	
    	// Trying to move the enemies
    	for (int i = 0; i < board.getNumOfEnemies(); i++) {
    		
    		for (int move = 0; move < 10; move++) {
        		System.out.println(Arrays.toString(allEnemies[i].getCurrentCoords()));
        		
        		//System.out.println(Arrays.deepToString(allEnemies[i].getPath().toArray()));
        		allEnemies[i].move();
        		System.out.println(allEnemies[i].getDirection());
    		}

    	}
    	
    }
}
