
import java.util.*;


/**
 * Class Owner: Lincoln
 *
 * The Enemy class contains all elements related to an enemy: enemies follow 'rails' of set length; enemies move at
 * a constant velocity; enemies have predetermined starting and ending points on their 'rails.'
 * Enemies do not overlap the obstacle map. The player is killed if they run into an obstacle.
 */

public class Enemy {
    private int speed;
    private int enemyID;
    private int x_start, x_end;
    private int y_start, y_end;
    private int x_current, y_current;
    private List<int[]> path;
    private int pathprogress;
 
   
    /**
     * Moves the enemy from the start coordinates to the end coordinates, and back.
     */
    public void move() {
    	
    	// When enemy reaches end of its path, it moves back
    	//if ((this.x_current == this.x_end) && (this.y_current == this.y_end)) {
    	if (pathprogress == path.size()) {
    		Collections.reverse(this.path);
    		this.pathprogress = 0;
    	}

    	this.x_current = this.path.get(pathprogress)[0];
    	this.y_current = this.path.get(pathprogress)[1];
    	this.pathprogress += 1;
    }


    /**
     * @param x row position of start location
     * @param y column position of end location
     */
    public void setStartLocation(int x, int y) {
    	this.x_start = x;
    	this.y_start = y;
    }
    
    /**
     * @param x row position of end location
     * @param y column position of end location
     */
    public void setEndLocation(int x, int y) {
    	this.x_end = x;
    	this.y_end = y;
    }
    
    /**
     * @param x row position of current location
     * @param y column position of current location
     */

    public void setCurrentLocation(int x, int y) {
    	this.x_current = x;
    	this.y_current = y;
    }
    
    /**
     * @param path int[] containing the path of the enemy.
     */
    public void setPath(List<int[]> apath) {
    	this.path = apath;
    }
    
    /**
     * Sets the enemy's ID
     */
    public void setEnemyID(int ID) {
    	this.enemyID = ID;
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
     * @return The enemy's path
     */
    public List<int[]> getPath() {
    	return this.path;
    }
    
    /**
     * @return Integer value for path progress
     */
    public int getPathProgress() {
    	return this.pathprogress;
    }
    
    /**
     * @return An enemy's ID
     */
    public int getEnemyID() {
    	return this.enemyID;
    }

    
    //Testing if enemy moves back and forth
    public static void main(String[] args) {
    	Enemy enemy = new Enemy();
    	List<Integer> ints = new ArrayList<Integer>(Arrays.asList(1,2,3));
    	List<int[]> path = new ArrayList<int[]>(Arrays.asList(new int[] {0,1}, new int[] {0,2}, new int[] {0,3}, new int[] {0,4}));
    	
    	//System.out.println(Arrays.deepToString(path.toArray()));
    	
    	enemy.setStartLocation(0, 0);
    	enemy.setCurrentLocation(0, 0);
    	enemy.setEndLocation(0, 2);
    	enemy.setPath(path);
    	
    	for (int i = 0; i < 10; i++) {
    		System.out.print(Arrays.toString(enemy.getCurrentCoords()));
    		//System.out.println(enemy.getPathProgress());
    		//System.out.println(Arrays.deepToString(enemy.getPath().toArray()));
        	enemy.move();
        	
    	}


    }

}
