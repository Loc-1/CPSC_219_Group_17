import java.util.ArrayList;
import java.util.List;

/**
 * Class Owner: Lincoln
 * <p>
 * Class is a procedurally generated map of interesting and traversable 'obstacles' represented as a 2d array of
 * primitive booleans.
 */
public class ObstacleAndEnemyMap extends Board {
    private boolean[][] obstacleMap;
    private static final int numberOfSteps = 100; // The number of times to run the simulation step.
    private static final double difficultyModifier = 0.55f;
    private static final int safeZone = 2;

    private ArrayList<Enemy> enemies;
    private Player player;

    /**
     * Creates a new obstacle map.
     */
    ObstacleAndEnemyMap(int rows, int cols, int difficulty) {
        super(rows * 4, cols, difficulty, rows);

        try { // Ensure board size is valid.
            if (rows % 4 != 0 || cols < 24) {
                throw new IllegalStateException("Invalid number of rows or columns. Rows must be greater than 26" +
                        " must be a number greater than 24.");
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return;
        }

        this.enemies = new ArrayList<>();
        this.obstacleMap = this.generateNewMap();
        this.player = new Player(this.getRows() - 1, this.getColumns() / 2);
        this.markTiles();
        this.printBoard();

        while (!isTraversable(this.obstacleMap)) {
            this.obstacleMap = generateNewMap();
        }

        this.placeEnemies(0);
        this.markTiles();
    }

    /**
     * Performs some generic logic to increase chances of ObstacleAndEnemyMap being traversable
     *
     * @param oldMap the obstacleMap to perform logic on
     * @return the new ObstacleAndEnemyMap
     */
    private static boolean[][] makeTraverseable(boolean[][] oldMap) {
        int x = oldMap.length - 3;

        while (x >= 5) {
            x--;
            for (int y = 5; y < oldMap[0].length - 5; y++) {
                if ((oldMap[x][y] && !oldMap[x + 1][y] && oldMap[x + 1][y - 1] && oldMap[x + 1][y + 1])
                        || (!oldMap[x][y] && !oldMap[x][y + 1] && oldMap[x + 1][y] && oldMap[x + 1][y + 1])) {
                    for (int t = 1; t <= 4; t++) {
                        for (int c = 1; c <= t; c++) {
                            oldMap[x - t][y + c] = false;
                            oldMap[x - t][c] = false;
                            oldMap[x - t][y - c] = false;
                        }
                    }
                    oldMap[x][y] = false;
                    oldMap[x][y + 1] = false;
                    y++;
                } else if (!oldMap[x][y] && oldMap[x][y + 1] && oldMap[x][y - 1] && oldMap[x + 1][y]) {
                    oldMap[x + 2][y + 1] = false;
                    oldMap[x + 2][y - 1] = false;
                    oldMap[x + 2][y + 2] = false;
                    oldMap[x + 2][y - 2] = false;
                    oldMap[x + 1][y + 1] = false;
                    oldMap[x + 1][y - 1] = false;
                    oldMap[x + 1][y] = false;
                    oldMap[x + 2][y] = false;
                    oldMap[x][y] = false;
                    oldMap[x][y + 1] = false;
                }
            }
        }

        return oldMap;
    }

    /**
     * Method places enemies on the board in random, possible locations.
     *
     * @param offset the number of tiles to exclude from enemy addition.
     */
    private void placeEnemies(int offset) {
        int chunkStepSize = this.getVisibleRows() / 4;
        int numOfChunks = this.getRows() / chunkStepSize;

        if (offset != 0) {
            numOfChunks = 1;
            chunkStepSize = offset / 4;
        }

        for (int chunkIndex = 0; chunkIndex < numOfChunks; chunkIndex++) {
            int visibleBoardStart = chunkIndex * chunkStepSize;
            int visibleBoardEnd = (chunkIndex + 1) * chunkStepSize;

            int i = 0;
            while (i < this.getDifficulty() + 1) {
                try {
                    addEnemy(visibleBoardStart, visibleBoardEnd);
                    i++;
                } catch (NullPointerException e) {
                    i = 0;
                }
            }

            chunkIndex++;
        }

    }

    /**
     * Taken from https://gamedevelopment.tutsplus.com/tutorials/generate-random-cave-levels-using-cellular-automata--gamedev-9664
     * needs a lot more work.
     *
     * @param oldMap the map to run simulations on.
     * @return a nice map with traversable obstacles.
     */
    private boolean[][] doSimulationStep(boolean[][] oldMap, int offset) {
        final double deathLimit = 2; // These numbers are entirely arbitrary.
        final double birthLimit = 5;

        for (int x = 0; x < this.getRows() - (offset + this.getVisibleRows() / 4); x++) {
            for (int y = 0; y < oldMap[0].length; y++) {
                int nbs = countAdjacentObstacles(oldMap, x, y);
                //The new value is based on our simulation rules
                //First, if a cell is alive but has too few neighbours, kill it.
                if (oldMap[x][y]) {
                    oldMap[x][y] = !(nbs < deathLimit) && nbs > 0;
                } else {
                    oldMap[x][y] = nbs > birthLimit;
                }
            }
        }

        return oldMap;
    }

    /**
     * Creates and adds a new enemy to the board.
     * @param visibleBoardStart the visible board start.
     * @param visibleBoardEnd the visible board end.
     */
    private void addEnemy(int visibleBoardStart, int visibleBoardEnd) {
        Enemy e = new Enemy();
        enemyStart(e, visibleBoardStart, visibleBoardEnd);
        enemyEnd(e, visibleBoardStart, visibleBoardEnd);
        enemyPath(e);
        this.enemies.add(e);
    }

    /**
     * Generates a whole new obstacle map.
     * @return A boolean[][] of obstacles.
     */
    private boolean[][] generateNewMap() {
        boolean[][] newMap = new boolean[this.getRows()][this.getColumns()];

        // Ensures the edges of the board are always an obstacle.
        for (int i = 0; i < this.getRows(); i++) {
            newMap[i][0] = true;
            newMap[i][this.getColumns() - 1] = true;
        }

        for (int row = 5; row < this.getRows() - safeZone; row++) {
            for (int col = 0; col < this.getColumns(); col++) {
                if (generateRandomDouble() < difficultyModifier && row < this.getRows()) {
                    newMap[row][col] = true;
                }
            }
        }

        // This recursively runs the cellular simulation per the int in numberOfSteps.
        for (int i = 0; i < numberOfSteps; i++) {
            newMap = doSimulationStep(newMap, 0);
        }

        return makeTraverseable(newMap);
    }

    /**
     * Counts the number of adjacent obstacles.
     *
     * @param map the map to count.
     * @param x   the center x coord.
     * @param y   the center y coord.
     * @return a count of adjacent obstacles.
     */
    private static int countAdjacentObstacles(boolean[][] map, int x, int y) {
        int count = 0;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int xAdjacent = x + i;
                int yAdjacent = y + j;
                if (xAdjacent < 0 || yAdjacent < 0 || xAdjacent >= map.length || yAdjacent >= map[0].length) {
                    count += 1;
                } else if (map[xAdjacent][yAdjacent]) {
                    count += 1;
                }
            }
        }

        return count;
    }

    /**
     * AStar based traversibility testing.
     * @param map the map the test.
     * @return true if map is traversable.
     */
    private boolean isTraversable(boolean[][] map) {
        int threshold = 7000; // Decrease value to tighten restraints on traversability
        boolean isTraversable = false;

        try {
            for (int i = 0; i < this.getColumns(); i++) {
                int cost = pathfinding.AStar.aStarCost(this.getRows(), this.getColumns(), this.getPlayer().getRow(),
                        this.getPlayer().getCol(), 0, i, this.obstacleLocations(map));
                if ((0 < cost) && (cost < threshold)) {
                    isTraversable = true;
                }
            }
        } catch (NullPointerException e) {
            return false;
        }

        return isTraversable;
    }

    /**
     * Determine coordinates of obstacles in the map
     *
     * @return A int array containing coordinates of the obstacles [row, column]
     */
    private int[][] obstacleLocations(boolean[][] map) {
        int numObstacles = checkNumObstacles(map);
        int obstacleNum = 0;
        int[][] locations = new int[numObstacles][2];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j]) {
                    locations[obstacleNum][0] = i;
                    locations[obstacleNum][1] = j;
                    obstacleNum += 1;
                }
            }
        }

        return locations;
    }

    /**
     * Determines coordinates of non obstacles obstacles in a specified section of the map (start row, end Row]
     *
     * @param startRows The starting row
     * @param endRows   The ending row
     * @return locations
     */
    private int[][] nonObstacleLocations(int startRows, int endRows) {
        boolean[][] checkMap = this.obstacleMap;
        int numObstacles = checkNumObstacles(startRows, endRows);
        int totalTiles = checkMap[0].length * (endRows - startRows);
        int numFreeSpace = totalTiles - numObstacles;
        int freeSpaceNum = 0;
        int[][] locations = new int[numFreeSpace][2];

        for (int i = startRows; i < endRows; i++) {
            for (int j = 0; j < checkMap[0].length; j++) {
                if (!checkMap[i][j]) {
                    locations[freeSpaceNum][0] = i;
                    locations[freeSpaceNum][1] = j;
                    freeSpaceNum += 1;
                }
            }
        }

        return locations;
    }

    /**
     * Determines the amount of obstacles in the map
     */
    private int checkNumObstacles(boolean[][] map) {
        int numObstacles = 0;

        for (boolean[] aCheckMap : map) {
            for (int j = 0; j < map[0].length; j++) {
                if (aCheckMap[j]) {
                    numObstacles += 1;
                }
            }
        }

        return numObstacles;
    }

    /**
     * Determines the number of obstacles within specified start and end rows
     *
     * @param startRows The starting row
     * @param endRows   The end rows
     * @return int
     */
    private int checkNumObstacles(int startRows, int endRows) {
        int numObstacles = 0;
        boolean[][] checkMap = this.obstacleMap;

        for (int i = startRows; i < endRows; i++) {
            for (int j = 0; j < checkMap[0].length - 1; j++) {
                if (checkMap[i][j]) {
                    numObstacles += 1;
                }
            }
        }

        return numObstacles;
    }

    /**
     * Determines a valid start position for enemies within the start and end rows
     *
     * @param aEnemy    The enemy to set the start positions
     * @param startRows The minimum row where the enemy should spawn
     * @param endRows   The maximum row where the enemy should spawn
     */
    private void enemyStart(Enemy aEnemy, int startRows, int endRows) {
        int[][] freeTiles = this.nonObstacleLocations(startRows, endRows);
        int[] randomStartCoords = freeTiles[(int) (generateRandomDouble() * freeTiles.length)];

        aEnemy.setStartLocation(randomStartCoords[0], randomStartCoords[1]);
        aEnemy.setCurrentLocation(aEnemy.getStartCoords()[0], aEnemy.getStartCoords()[1]);

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
    private void enemyEnd(Enemy aEnemy, int startRows, int endRows) {
        int minTravelCost = 150;
        int maxTravelCost = 400;

        int[] endCoords = pathfinding.AStar.chooseDestination(this.getRows(), this.getColumns(), aEnemy.getStartCoords()[0],
                aEnemy.getStartCoords()[1], this.player.getRow(), this.player.getCol(),
                this.obstacleLocations(this.obstacleMap), minTravelCost, maxTravelCost);

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
    private void enemyPath(Enemy aEnemy) {
        List<int[]> enemyPath = pathfinding.AStar.pathfinding(this.getRows(), this.getColumns(), aEnemy.getStartCoords()[0],
                aEnemy.getStartCoords()[1], aEnemy.getEndCoords()[0], aEnemy.getEndCoords()[1],
                this.obstacleLocations(this.obstacleMap));
        aEnemy.setPath(enemyPath);

    }

    /**
     * flags tiles according to their surrounding tiles for map art application
     * Chars markers are based on the following:
     * char markers are compass directions. E is substituted for T since enemy uses E
     * if char marker is CAPITAL the letter is indicating the only side that is connected to an obstacle
     * if char marker is lowercase the letter is indicating the only side that is not connected to an obstacle
     * <p>
     * a = N & E sides exposed
     * b = S & E sides exposed
     * c = W & S sides exposed
     * d = N & W sides exposed
     * e = N & S sides exposed
     * f = W & E sides exposed
     */
    void markTiles() {
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getColumns(); j++) {
                if (this.isObstacle(i, j)) {
                    this.setBoard(i, j, 'X');
                } else {
                    this.setBoard(i, j, '.');
                }
            }
        }

        for (Enemy enemy : this.enemies) {
            this.setBoard(enemy.getCurrentCoords()[0], enemy.getCurrentCoords()[1], 'E');
        }


        this.setBoard(this.player.getRow(), this.player.getCol(), 'P');
        this.notifyAllObservers();
    }

    ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * @param row the row to check.
     * @param col the col to check.
     * @return true if the (row, col) is an obstacle.
     */
    boolean isObstacle(int row, int col) {
        return this.obstacleMap[row][col];
    }

    /**
     * @param rowEnd destination x coord.
     * @param colEnd destination y coord.
     * @return true if move is valid.
     */
    Boolean isValidMove(int rowEnd, int colEnd) {
        Boolean isValid = true;
        if (this.isObstacle(rowEnd, colEnd)) {
            isValid = false;
        }

        return isValid;
    }

    public void set(int row, int col, boolean set) {
        this.obstacleMap[row][col] = set;
        this.notifyAllObservers();
    }

    public Player getPlayer() {
        return player;
    }

}
