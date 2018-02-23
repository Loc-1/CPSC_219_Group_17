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
    private int[] startCoords;
    private int[] endCoords;
    private int[] currentCoords;
    
    /**
     * Constructor that creates an enemy and sets the start and end locations
     */
    public Enemy(int boardRows, int boardColumns) {
    	int x_start;
    	int x_end;
    	int y_start;
    	int y_end;
    	
    	x_start = (int) (generateRandomDouble() * (boardRows-4) + 4); // Ensuring enemy path does not go off array bounds
    	y_start = (int) (generateRandomDouble() * (boardColumns-4) + 4); // :TODO: Make this more intuitive
    	
    	this.startCoords = new int[] {x_start, y_start};
    	
    	// Determine end coordinates using movement constraints
    	if (generateRandomDouble() > 0.50) {
    	    x_end = x_start + distanceToTravel();	
    	} else {
    		x_end = x_start - distanceToTravel();
    	}
    	
    	if (generateRandomDouble() > 0.50) {
    		y_end = y_start + distanceToTravel();
    	} else {
    		y_end = y_start - distanceToTravel();
    	}
    	
    	this.endCoords = new int[] {x_end, y_end}; 
    }

    /**
     * @param direction {'0': 'up', '1': 'down', '2', 'left', '3', 'right}
     */
    public void setDirection(int direction) {
    }

    /**
     * @param xy row-column coordinates of starting location.
     */
    public void setStartLocation(int[] xy) {
    	this.startCoords = xy;
    }

    /**
     * @param xy row-column coordinates of ending location.
     */
    public void setEndLocation(int[] xy) {
    	this.endCoords = xy;
    }

    /**
     * @param velocity {'1.0': 'Top speed', '0.0': 'Stopped'}
     */
    public void setSpeed(double velocity) {
    }
    
    /**
     * Gets the start coordinates
     */
    public int[] getstartCoords() {
    	return this.startCoords;
    }
    
    /**
     * Gets the end coordinates
     */
    public int[] getendCoords() {
    	return this.endCoords;
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

    	System.out.println(Arrays.toString(enemy.getstartCoords()));
    	System.out.println(Arrays.toString(enemy.getendCoords()));
    	
    }

}
