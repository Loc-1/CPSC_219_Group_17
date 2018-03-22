import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An extension of the general Sprite class that includes a movement timer. This allows a sprite to move automatically.
 */
class EnemySprite extends Sprite {
    private final Enemy enemy;
    private long moveTimer;
    private Timer timer;

    EnemySprite(int difficulty, Enemy setEnemy) {
        this.enemy = setEnemy;
        this.updatePosition();
        Random r = new Random();

        while (this.moveTimer < 50) {
            this.moveTimer = (r.nextInt(1000) * difficulty);
        }
        this.run();

    }

    /**
     * This method runs the background movement task as a TimerTask.
     */
    private void run() {
        this.timer = new Timer();
        this.timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        enemy.move();
                        updatePosition();
                    }
                }, 0, this.moveTimer);
    }

    /**
     * Update the sprites position in the Sprite parent.
     */
    private void updatePosition() {
        int[] coords = this.enemy.getCurrentCoords();
        super.setY(coords[0]);
        super.setX(coords[1]);
        System.out.println(super.getBoundary());
    }

    /**
     * Not currently working. Should kill all the threads but doesnt.
     */
    void stop() {
        this.timer.cancel();
        this.timer.purge();
    }

}
