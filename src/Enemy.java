import java.util.Random;
import java.util.Arrays;


/**
 * Class Owner: Lincoln
 *
 * The Enemy class contains all elements related to an enemy: enemies follow 'rails' of set length; enemies move at
 * a constant velocity; enemies have predetermined starting and ending points on their 'rails.'
 * Enemies do not overlap the obstacle map. The player is killed if they run into an obstacle.
 */

public class Enemy {
    private int direction;
    private int speed;
    private int x_start, x_end;
    private int y_start, y_end;
    private int x_current, y_current;
    private int pathprogress;
    
    /**
     * Constructor that creates an enemy and sets the start and end locations
     */
    public Enemy(int boardRows, int boardColumns) {
    	
    	int xTravelDis = distanceToTravel();
    	int yTravelDis = distanceToTravel();
    	
    	this.x_start = (int) (generateRandomDouble() * (boardRows-5) + 5); // Ensuring enemy path does not go off array bounds
    	this.y_start = (int) (generateRandomDouble() * (boardColumns-5) + 5); // :TODO: Make this more intuitive
    	
    	
    	// Determine end coordinates using movement constraints
    	if (generateRandomDouble() > 0.50) {
    	    this.x_end = x_start + xTravelDis;	
    	} else {
    		this.x_end = x_start - xTravelDis;
    	}
    	
    	if (generateRandomDouble() > 0.50) {
    		this.y_end = y_start + yTravelDis;
    	} else {
    		this.y_end = y_start - yTravelDis;
    	}
    	
    }
    
    /**
     * Moves the enemy from the start coordinates to the end coordinates.
     * @param startCoords row-column coordinates of starting location
     * @param endCoords row-column coordinates of ending location
     * @param stepNumber The current step or progress of the enemy
     * @param blockedCoords 2D array with all locations that cannot be traversed
     */
    public void move(int[] startCoords, int[] endCoords, int stepNumber, int[][] blockedCoords) {
    	
    }

    /**
     * @param direction {'0': 'up', '1': 'down', '2', 'left', '3', 'right}
     */
    public void setDirection(int direction) {
    }

    /**
     * @param xy row-column coordinates of starting location.
     */
    public void setStartLocation(int x, int y) {
    	this.x_start = x;
    	this.y_start = y;
    }
    
    /**
     * @param xy row-column coordinates of ending location.
     */
    public void setEndLocation(int x, int y) {
    	this.x_end = x;
    	this.y_end = y;
    }
    
    /**
     * @param xy row-column coordinates of current location.
     */

    public void setCurrentLocation(int x, int y) {
    	this.x_current = x;
    	this.y_current = y;
    }


    /**
     * @param velocity {'1.0': 'Top speed', '0.0': 'Stopped'}
     */
    public void setSpeed(double velocity) {
    }
    
    /**
     * Gets the start coordinates
     */
    public int[] getStartCoords() {
    	int [] startCoords = new int[] {this.x_start, this.y_start};
    	return startCoords;
    }

    /**
     * Gets the current coordinates
     */
    public int[] getCurrentCoords() {
    	int [] currentCoords = new int[] {this.x_current, this.y_current};
    	return currentCoords;
    }
    
    /**
     * Gets the end coordinates
     */
    public int[] getEndCoords() {
    	int [] endCoords = new int[] {this.x_end, this.y_end};
    	return endCoords;
    }
    /**
     * 
     * @return a random double.
     */
    public double generateRandomDouble() {
        Random randomNum = new Random();
        return randomNum.nextDouble();
    }
    
    /**
     * 
     * Randomly determines how far the enemies path will be.
     * Changing constraint values will allow or restrict enemy movement
     */
    public int distanceToTravel() {
    	return (int) (generateRandomDouble() * 4 + 1);
    }
    
    //Testing
    public static void main(String[] args) {
    	Enemy enemy = new Enemy(20, 20);

    	System.out.println(Arrays.toString(enemy.getStartCoords()));
    	System.out.println(Arrays.toString(enemy.getEndCoords()));
    	
    }

}
