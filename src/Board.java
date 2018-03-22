import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Class Owner: Ashton / Lincoln
 * <p>
 * This class is used to generate and store the board object. An obstacle map is automatically generated upon
 * instantiating a member of this class. Calls must include a valid frame width and height in units of 25x25 pixel
 * blocks, a valid difficulty, and at least one valid player object.
 */
@SuppressWarnings("WeakerAccess") // This is for the auto code inspector. Please ignore. They cannot be resolved now.
public class Board {
    private int rows;
    private int columns;
    private int difficulty;
    private char[][] board;
    private ObstacleMap obstacleMap;
    private Player playerOne;
    private Enemy[] enemies;
    private int numOfEnemies;
    private int visibleRows;
    @SuppressWarnings("unused")
    private AStarMap traversability;

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
        this.traversability = new AStarMap();
        this.playerOne = setPlayer;
        this.numOfEnemies = 2;
        this.enemies = new Enemy[numOfEnemies];

        this.visibleRows = 32;

        //makes sure map is traversable before acting on it
        while (AStarMap.test(this.rows, this.columns, playerOne.getRow(), playerOne.getCol(), 0, this.columns / 2, this.obstacleMap.obstacleLocations()) < 0) {
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
            for (int visibleBoardIndex = 0; visibleBoardIndex < numOfVisibleBoards; visibleBoardIndex++) {
                int visibleBoardStart = visibleBoardIndex * visibleRows;
                int visibleBoardEnd = (visibleBoardIndex + 1) * visibleRows;

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
     *
     * @param setRows        Desired number of rows
     * @param setColumns     Desired number of columns
     * @param setDifficulty  Desired difficulty (0 - easy, 1 - medium, 2 - hard)
     * @param setPlayer      the player.
     * @param setVisibleRows the displayed rows
     */
    public Board(int setRows, int setColumns, int setDifficulty, Player setPlayer, int setVisibleRows) {
        this.rows = setRows;
        this.columns = setColumns;
        this.difficulty = setDifficulty;
        this.board = new char[rows][columns];
        this.obstacleMap = new ObstacleMap(this.rows, this.columns, this.difficulty);
        this.traversability = new AStarMap();
        this.playerOne = setPlayer;

        // I want to set the total rows as a constant multiple of visible rows to make looping through each visible board easier. 
        // Would be better to not have setVisible rows as a param. Just checking for now.
        int numOfVisibleBoards = (setRows / setVisibleRows);
        this.visibleRows = setRows / numOfVisibleBoards;
        int numOfVisibleEnemies = setDifficulty * 5;
        this.numOfEnemies = numOfVisibleEnemies * numOfVisibleBoards; // Creates 1, 2, or 3 enemies for each visible board, times a scaling factor
        List<Enemy> enemies = new ArrayList<>();

        //makes sure map is traversable before acting on it
        while (AStarMap.test(this.rows, this.columns, playerOne.getRow(), playerOne.getCol(), 0, this.columns / 2, this.obstacleMap.obstacleLocations()) < 0) {
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
            for (int visibleBoardIndex = 0; visibleBoardIndex < numOfVisibleBoards; visibleBoardIndex++) {
                int visibleBoardStart = visibleBoardIndex * visibleRows;
                int visibleBoardEnd = (visibleBoardIndex + 1) * visibleRows;

                for (int enemyNum = 0; enemyNum < numOfVisibleEnemies; enemyNum++) {
                    Enemy aEnemy = new Enemy();
                    enemyStart(aEnemy, visibleBoardStart, visibleBoardEnd);
                    enemyEnd(aEnemy, visibleBoardStart, visibleBoardEnd);
                    enemyPath(aEnemy);
                    enemies.add(enemyNum, aEnemy);

                    this.board[enemies.get(enemyNum).getCurrentCoords()[0]][enemies.get(enemyNum).getCurrentCoords()[1]] = 'E';
                }
            }

            Enemy[] enemyArr = new Enemy[enemies.size()];
            this.enemies = enemies.toArray(enemyArr);
        }

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
     * @return a random double.
     */
    private static double generateRandomDouble() {
        Random randomNum = new Random();
        return randomNum.nextDouble();
    }

    // Testing
    //Testing
    public static void main(String[] args) {
        Player player = new Player(32 - 1, 26 / 2, "");
        Board board = new Board(32, 26, 1, player, 32);

        board.printBoard();
        Enemy[] allEnemies = board.getEnemies();

        for (int i = 0; i < board.getNumOfEnemies(); i++) {
            List<int[]> paths = allEnemies[i].getPath();

            System.out.println("Enemy #" + Integer.toString(i) + "'s Path: " + Arrays.deepToString(paths.toArray()));
        }

        System.out.println("The above shows the initial board");
        System.out.println("---------------------------------");

        // Trying to move the enemies
        for (int move = 0; move < 5; move++) {

            board.refresh();
            board.printBoard();
            System.out.println("\n");
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
     * Check if the board is traversable
     * Changes to the threshold modifier modify leniency on map traversability
     *
     * @return True if the map is deemed traversable
     */
    @SuppressWarnings("unused")
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
     *
     * @param aEnemy    The enemy to set the start positions
     * @param startRows The minimum row where the enemy should spawn
     * @param endRows   The maximum row where the enemy should spawn
     */
    public void enemyStart(Enemy aEnemy, int startRows, int endRows) {
        int[][] freeTiles = this.obstacleMap.nonObstacleLocations(startRows, endRows);
        int[] randomStartCoords = freeTiles[(int) (generateRandomDouble() * freeTiles.length)];

        aEnemy.setStartLocation(randomStartCoords[0], randomStartCoords[1]);
        aEnemy.setCurrentLocation(aEnemy.getStartCoords()[0], aEnemy.getStartCoords()[1]);

    }

    /**
     * Updates the Boards ObstacleMap with new traversable map on top and chops off the bottom part of the board
     * also updates locations of copied enemies and the player
     *
     */
    public void updateBoard() {
        char[][] oldBoard = Arrays.copyOf(this.board, this.board.length - 50);
        System.out.println(Arrays.deepToString(oldBoard));
    }


    /**
     * flags tiles according to their surrounding tiles for map art application
     * Chars markers are based on the following:
     * char markers are compass directions. E is substituted for T since enemy uses E
     * if char marker is CAPITAL the letter is indicating the only side that is connected to an obstacle
     * if char marker is lowercase the letter is indicating the only side that is not connected to an obstacle
     *
     * a = N & E sides exposed
     * b = S & E sides exposed
     * c = W & S sides exposed
     * d = N & W sides exposed
     * e = N & S sides exposed
     * f = W & E sides exposed
     */
    public void markTiles() {

        for(int i = 0; i < this.board.length; i++){
            for(int j = 1; j < this.board[0].length - 1; j++) {
                if (this.obstacleMap.isObstacle(i,j)) {

                    if(this.obstacleMap.isObstacle(i,j-1) &&
                            !this.obstacleMap.isObstacle(i,j+1) &&
                            !this.obstacleMap.isObstacle(i-1,j) &&
                            !this.obstacleMap.isObstacle(i+1,j) ) {
                        this.board[i][j] = 'W';
                    }

                    if(this.obstacleMap.isObstacle(i,j+1) &&
                            !this.obstacleMap.isObstacle(i,j-1) &&
                            !this.obstacleMap.isObstacle(i-1,j) &&
                            !this.obstacleMap.isObstacle(i+1,j) ) {
                        this.board[i][j] = 'T';
                    }

                    if(this.obstacleMap.isObstacle(i+1,j) &&
                            !this.obstacleMap.isObstacle(i,j+1) &&
                            !this.obstacleMap.isObstacle(i,j-1) &&
                            !this.obstacleMap.isObstacle(i-1,j) ) {
                        this.board[i][j] = 'S';
                    }

                    if(this.obstacleMap.isObstacle(i-1,j) &&
                            !this.obstacleMap.isObstacle(i,j+1) &&
                            !this.obstacleMap.isObstacle(i,j-1) &&
                            !this.obstacleMap.isObstacle(i+1,j) ) {
                        this.board[i][j] = 'N';
                    }

                    if(!this.obstacleMap.isObstacle(i,j-1) &&
                            this.obstacleMap.isObstacle(i,j+1) &&
                            this.obstacleMap.isObstacle(i-1,j) &&
                            this.obstacleMap.isObstacle(i+1,j) ) {
                        this.board[i][j] = 'w';
                    }

                    if(!this.obstacleMap.isObstacle(i,j+1) &&
                            this.obstacleMap.isObstacle(i,j-1) &&
                            this.obstacleMap.isObstacle(i-1,j) &&
                            this.obstacleMap.isObstacle(i+1,j) ) {
                        this.board[i][j] = 't';
                    }

                    if(!this.obstacleMap.isObstacle(i+1,j) &&
                            this.obstacleMap.isObstacle(i,j+1) &&
                            this.obstacleMap.isObstacle(i,j-1) &&
                            this.obstacleMap.isObstacle(i-1,j) ) {
                        this.board[i][j] = 's';
                    }

                    if(!this.obstacleMap.isObstacle(i-1,j) &&
                            this.obstacleMap.isObstacle(i,j+1) &&
                            this.obstacleMap.isObstacle(i,j-1) &&
                            this.obstacleMap.isObstacle(i+1,j) ) {
                        this.board[i][j] = 'n';
                    }

                    if(!this.obstacleMap.isObstacle(i-1,j) &&
                            !this.obstacleMap.isObstacle(i,j+1) &&
                            this.obstacleMap.isObstacle(i,j-1) &&
                            this.obstacleMap.isObstacle(i+1,j) ) {
                        this.board[i][j] = 'a';
                    }

                    if(!this.obstacleMap.isObstacle(i,j+1) &&
                            !this.obstacleMap.isObstacle(i+1,j) &&
                            this.obstacleMap.isObstacle(i,j-1) &&
                            this.obstacleMap.isObstacle(i-1,j) ) {
                        this.board[i][j] = 'b';
                    }

                    if(!this.obstacleMap.isObstacle(i+1,j) &&
                            !this.obstacleMap.isObstacle(i,j-1) &&
                            this.obstacleMap.isObstacle(i,j+1) &&
                            this.obstacleMap.isObstacle(i-1,j) ) {
                        this.board[i][j] = 'c';
                    }

                    if(!this.obstacleMap.isObstacle(i-1,j) &&
                            !this.obstacleMap.isObstacle(i,j-1) &&
                            this.obstacleMap.isObstacle(i,j+1) &&
                            this.obstacleMap.isObstacle(i+1,j) ) {
                        this.board[i][j] = 'd';
                    }

                    if(!this.obstacleMap.isObstacle(i+1,j) &&
                            !this.obstacleMap.isObstacle(i-1,j) &&
                            this.obstacleMap.isObstacle(i,j+1) &&
                            this.obstacleMap.isObstacle(i,j-1) ) {
                        this.board[i][j] = 'e';
                    }

                    if(!this.obstacleMap.isObstacle(i,j-1) &&
                            !this.obstacleMap.isObstacle(i,j+1) &&
                            this.obstacleMap.isObstacle(i+1,j) &&
                            this.obstacleMap.isObstacle(i-1,j) ) {
                        this.board[i][j] = 'f';
                    }
                }
            }
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
     * Determines a valid end position for enemies based off the A* algorithm
     * Changing the constraints of minTravelCost and maxTravelCost allow enemy to choose
     * further or closer end points
     *
     * @param aEnemy    The enemy to set the end position
     * @param startRows The minimum rows where the enemy should spawn
     * @param endRows   The maximum rows where the enemy should spawn
     */
    public void enemyEnd(Enemy aEnemy, int startRows, int endRows) {
        int minTravelCost = 150;
        int maxTravelCost = 400;

        int[] endCoords = pathfinding.AStar.chooseDestination(this.rows, this.columns, aEnemy.getStartCoords()[0],
                aEnemy.getStartCoords()[1], this.getPlayerOne().getRow(), this.getPlayerOne().getCol(),
                this.getObstacleMap().obstacleLocations(), minTravelCost, maxTravelCost);

        // If there are no possible end coordinates to choose, reselect the start coordinates.
        if (endCoords == null) {
            enemyStart(aEnemy, startRows, endRows);
            enemyEnd(aEnemy, startRows, endRows);
        } else {
            aEnemy.setEndLocation(endCoords[0], endCoords[1]);
        }

    }

    /**
     * Determines a valid path for the enemy based off start and end points and the board.
     *
     * @param aEnemy The enemy to set the path
     */
    public void enemyPath(Enemy aEnemy) {
        List<int[]> enemyPath = pathfinding.AStar.pathfinding(this.getRows(), this.getColumns(), aEnemy.getStartCoords()[0],
                aEnemy.getStartCoords()[1], aEnemy.getEndCoords()[0], aEnemy.getEndCoords()[1],
                this.getObstacleMap().obstacleLocations());
        aEnemy.setPath(enemyPath);

    }

    int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    private ObstacleMap getObstacleMap() {
        return obstacleMap;
    }

    private Player getPlayerOne() {
        return playerOne;
    }

    Enemy[] getEnemies() {
        return enemies;
    }

    private int getNumOfEnemies() {
        return numOfEnemies;
    }

}
