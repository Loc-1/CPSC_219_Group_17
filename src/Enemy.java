/**
 * Class Owner: Lincoln
 *
 * The Enemy class contains all elements related to an enemy: enemies follow 'rails' of set length; enemies move at
 * a constant velocity; enemies have predetermined starting and ending points on their 'rails.'
 */

public class Enemy {
    private int direction;
    private int speed;
    private int x_start;
    private int x_end;
    private int y_start;
    private int y_end;

    /**
     * @param direction {'0': 'up', '1': 'down', '2', 'left', '3', 'right}
     */
    public void setDirection(int direction) {
    }

    /**
     * @param x x-coordinate of starting location.
     * @param y y-coordinate of starting location.
     */
    public void setStartLocation(int x, int y) {
    }

    /**
     * @param x x-coordinate of ending location.
     * @param y y-coordinate of ending location.
     */
    public void setEndLocation(int x, int y) {
    }

    /**
     * @param velocity {'1.0': 'Top speed', '0.0': 'Stopped'}
     */
    public void setSpeed(double velocity) {
    }
}
