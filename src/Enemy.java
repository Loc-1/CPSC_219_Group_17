import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class Owner: Lincoln
 *
 * The Enemy class contains all elements related to an enemy: enemies follow 'rails' of set length; enemies move at
 * a constant velocity; enemies have predetermined starting and ending points on their 'rails.'
 * Enemies do not overlap the obstacle map. The player is killed if they run into an obstacle.
 */

class Enemy {
    private int x_start, x_end;
    private int y_start, y_end;
    private int x_current, y_current;
    private List<int[]> path;
    private int pathProgress;

    //Testing if enemy moves back and forth
    public static void main(String[] args) {
        Enemy enemy = new Enemy();
        List<int[]> path = new ArrayList<>(Arrays.asList(new int[]{0, 1}, new int[]{0, 2}, new int[]{0, 3}, new int[]{0, 4}));

        enemy.setStartLocation(0, 0);
        enemy.setCurrentLocation(0, 0);
        enemy.setEndLocation(0, 2);
        enemy.setPath(path);

    }

    /**
     * Moves the enemy from the start coordinates to the end coordinates, and back.
     */
    void move() {
        // When enemy reaches end of its path, it moves back
        // if ((this.x_current == this.x_end) && (this.y_current == this.y_end)) {
        if (pathProgress == path.size()) {
            Collections.reverse(this.path);
            this.pathProgress = 0;
        }

        this.x_current = this.path.get(pathProgress)[0];
        this.y_current = this.path.get(pathProgress)[1];

        this.pathProgress += 1;
    }

    /**
     * @param x row position of start location
     * @param y column position of end location
     */
    void setStartLocation(int x, int y) {
        this.x_start = x;
        this.y_start = y;
    }

    /**
     * @param x row position of end location
     * @param y column position of end location
     */
    void setEndLocation(int x, int y) {
        this.x_end = x;
        this.y_end = y;
    }

    /**
     * @param x row position of current location
     * @param y column position of current location
     */
    void setCurrentLocation(int x, int y) {
        this.x_current = x;
        this.y_current = y;

    }

    int[] getStartCoords() {
        return new int[]{this.x_start, this.y_start};
    }

    int[] getCurrentCoords() {
        return new int[]{this.x_current, this.y_current};
    }

    int[] getEndCoords() {
        return new int[]{this.x_end, this.y_end};
    }

    /**
     * @param aPath int[] containing the path of the enemy.
     */
    void setPath(List<int[]> aPath) {
        this.path = aPath;
    }

}
