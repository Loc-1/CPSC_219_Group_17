import javafx.collections.transformation.SortedList;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class Owner: Ashton
 * <p>
 * Class is a procedurally generated map of interesting and traversable 'obstacles' represented as a 2d array of
 * primitive booleans.
 */
public class ObstacleMap {
    private boolean[][] obstacleMap;
    private int rows;
    private int cols;
    private int difficulty;

    /**
     * Creates a new obstacle map.
     *
     * @param setRows    the desired number of rows.
     * @param setColumns the desired number of columns
     * @param difficulty the desired difficulty.
     */
    public ObstacleMap(int setRows, int setColumns, int difficulty) {
        this.obstacleMap = new boolean[setRows][setColumns];
        this.rows = setRows;
        this.cols = setColumns;
        this.difficulty = difficulty;
        final int safeZone = 2; // The number of rows without obstacles in the starting zone.
        final int numberOfSteps = 750; // The number of times to run the simulation step.
        final double difficultyModifier = calcDifficultyModifier(difficulty);

        for (int row = 0; row < (setRows) - safeZone; row++) {
            for (int col = 0; col < setColumns; col++) {
                if (generateRandomDouble() < difficultyModifier) {
                    this.obstacleMap[row][col] = true;
                }
            }
        }

        // This recursively runs the cellular simulation per the int in numberOfSteps.
        for (int i = 0; i < numberOfSteps; i++) {
            this.obstacleMap = doSimulationStep(this.obstacleMap);
        }



      this.obstacleMap = makeTraverseable(this.obstacleMap);

      for (int i = 0; i < 100; i++) {
            this.obstacleMap = doSimulationStep(this.obstacleMap);
        }

        // Ensures the edges of the board are always an obstacle.
        for (int i = 0; i < (setRows); i++) {
            this.obstacleMap[i][0] = true;
            this.obstacleMap[i][setColumns - 1] = true;
        }




    }

    /**
     * Taken from https://gamedevelopment.tutsplus.com/tutorials/generate-random-cave-levels-using-cellular-automata--gamedev-9664
     * needs a lot more work.
     *
     * @param oldMap the map to run simulations on.
     * @return a nice map with traversable obstacles.
     */

    public static boolean[][] doSimulationStep(boolean[][] oldMap) {
        final double deathLimit = 3; // These numbers are entirely arbitrary.
        final double birthLimit = 5;

        boolean[][] newMap = new boolean[oldMap.length][oldMap[0].length];

        for (int x = 0; x < oldMap.length - 1; x++) {
            for (int y = 0; y < oldMap[0].length; y++) {
                int nbs = countAdjacentObstacles(oldMap, x, y);
                //The new value is based on our simulation rules
                //First, if a cell is alive but has too few neighbours, kill it.
                if (oldMap[x][y]) {
                    newMap[x][y] = !(nbs < deathLimit);
                } //Otherwise, if the cell is dead now, check if it has the right number of neighbours to be 'born'
                else {
                    newMap[x][y] = nbs > birthLimit;
                }
            }
        }

        return newMap;
    }









    public boolean[][] makeTraverseable(boolean[][] oldMap) {
        boolean[][] newMap = this.obstacleMap;



        int x = newMap.length - 3;


        while (x >= 5) {
            x--;
            for (int y = 5; y < newMap[0].length - 5; y++){
                if ((newMap[x][y] && !newMap[x+1][y] && newMap[x+1][y-1] && newMap[x+1][y+1])
                        || (!newMap[x][y] && !newMap[x][y+1] && newMap[x+1][y] && newMap[x+1][y+1])) {




                    for (int t = 1; t <= 4;t++) {
                        for(int c = 1; c <= t; c++){
                            newMap[x-t][y+c] = false;
                            newMap[x-t][c] = false;
                            newMap[x-t][y-c] = false;
                        }
                    }

                    newMap[x][y] = false;
                    newMap[x][y+1] = false;



                    y++;

                }
                else if (!newMap[x][y] && newMap[x][y+1] && newMap[x][y-1] && newMap[x+1][y]) {
                    newMap[x+2][y+1] = false;
                    newMap[x+2][y-1] = false;
                    newMap[x+2][y+2] = false;
                    newMap[x+2][y-2] = false;
                    newMap[x+1][y+1] = false;
                    newMap[x+1][y-1] = false;
                    newMap[x+1][y] = false;
                    newMap[x+2][y] = false;
                    newMap[x][y] = false;
                    newMap[x][y+1] = false;
                }
            }
        }
        return newMap;
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

                if (i == 0 && j == 0) {
                } else if (xAdjacent < 0 || yAdjacent < 0 || xAdjacent >= map.length || yAdjacent >= map[0].length) {
                    count += 1;
                } else if (map[xAdjacent][yAdjacent]) {
                    count += 1;
                }
            }
        }

        return count;
    }
    
    /**
     * Determine coordinates of obstacles in the map
     * @return A int array containing coordinates of the obstacles [row, column]
     */
    public int[][] obstacleLocations(){
    	
    	boolean[][] checkMap = this.obstacleMap;
    	int numObstacles = checkNumObstacles();
    	int obstacleNum = 0;
    	
    	int[][] locations = new int[numObstacles][2];
    	
    	for (int i = 0; i < checkMap.length; i++) {
    		for (int j = 0; j < checkMap[0].length; j++) {
    			if (checkMap[i][j]) { 				
    				locations[obstacleNum][0] = i;
    				locations[obstacleNum][1] = j;
    				obstacleNum += 1;

    			}
    			
    		}
    	}
        return locations;
    }
    
    /**
     * Determine coordinates of non obstacles in the map
     * @return A int array containing coordinates of the nonobstacles [row, column]
     */
    public int[][] nonObstacleLocations(){
    	
    	boolean[][] checkMap = this.obstacleMap;
    	int numObstacles = checkNumObstacles();
    	int totalTiles = checkMap[0].length * checkMap.length;
    	int numFreeSpace = totalTiles - numObstacles;
    	
    	int freeSpaceNum = 0;
    	
    	int[][] locations = new int[numFreeSpace][2];
    	
    	for (int i = 0; i < checkMap.length; i++) {
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
     * Determines coordinates of non obstacles obstacles in a specified section of the map (start row, end Row]
     * @param startRows		The starting row
     * @param endRows		The ending row
     * @return
     */
    public int[][] nonObstacleLocations(int startRows, int endRows) {
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
    public int checkNumObstacles() {
    	
    	boolean[][] checkMap = this.obstacleMap;
    	int numObstacles = 0;
    	
    	for (int i = 0; i < checkMap.length; i++) {
    		for (int j = 0; j < checkMap[0].length; j++) {
    			if (checkMap[i][j]) {
    				numObstacles += 1;
    			}
    		}
    	}
    	
    	return numObstacles;
    }
    
    /**
     * Determines the number of obstacles within specified start and end rows
     * @param startRows 	The starting row
     * @param startCols		The starting column
     * @param endRows		The end rows
     * @param endCols		The end column
     * @return
     */
    public int checkNumObstacles(int startRows, int endRows) {
    	int numObstacles = 0;
    	boolean[][] checkMap = this.obstacleMap;
    	
    	for (int i = startRows; i < endRows; i++) {
    		for (int j = 0; j < checkMap[0].length; j++) {
    			if (checkMap[i][j]) {
    				numObstacles += 1;
    			}
    		}
    	}
    	
    	return numObstacles;
    }

    /**
     * First pass at the flood fill map checker.
     *
     * @param checkMap the map to analyze for open tiles.
     * @return a double representing the number of open tiles divided by the total tiles.
     */
    private static double checkObstacleMap(boolean[][] checkMap) {
        double openTiles = 0.00;
        double totalTiles = checkMap[0].length * checkMap.length;

        for (int i = 0; i < checkMap.length; i++) {
            for (int j = 0; j < checkMap[0].length; j++) {
                if (!checkMap[i][j]) {
                    openTiles += 1;
                }
            }
        }

        return openTiles / totalTiles;
    }

    /**
     * Changes to these modifiers will set the seed chance in the obstacleMap generation.
     *
     * @param setDifficulty the difficulty int.
     * @return a difficulty modifier for the simulation step / seed generation.
     */
    public static double calcDifficultyModifier(int setDifficulty) {
        double difficultyModifier = 0.00;

        if (setDifficulty == 1) {
            difficultyModifier = 0.42;
        } else if (setDifficulty == 2) {
            difficultyModifier = 0.47;
        } else if (setDifficulty == 3) {
            difficultyModifier = 0.52;
        }

        return difficultyModifier;
    }

    /**
     * @return a random double.
     */
    public double generateRandomDouble() {
        Random randomNum = new Random();
        return randomNum.nextDouble();
    }

    /**
     * @param row         the row to set.
     * @param col         the col to set.
     * @param setObstacle true/false.
     */
    public void setObstacle(int row, int col, boolean setObstacle) {
        this.obstacleMap[row][col] = setObstacle;
    }

    /**
     * @param row the row to check.
     * @param col the col to check.
     * @return true if the (row, col) is an obstacle.
     */
    public boolean isObstacle(int row, int col) {
        return this.obstacleMap[row][col];
    }

    /**
     * @return the obstacle map as an boolean[][].
     */
    public boolean[][] getObstacleMap() {
        return obstacleMap;
    }

}
