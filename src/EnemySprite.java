import javafx.scene.image.Image;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Josh
 * An extension of the general Sprite class that includes a movement timer. This allows a sprite to move automatically.
 */
class EnemySprite extends Sprite {
    private final Enemy enemy;
    private long moveTimer;
    private Timer timer;
    private long difficultyMod;

    EnemySprite(int difficulty, Enemy setEnemy) {
        this.enemy = setEnemy;

        Image enemyLeftImage = new Image("en_left.png");
        super.setImage(enemyLeftImage);

        this.updatePosition();

        Random r = new Random();

        switch (difficulty) {
            case 1:
                difficultyMod = 3;
                break;
            case 2:
                difficultyMod = 2;
                break;
            case 3:
                difficultyMod = 1;
                break;
        }

        while (this.moveTimer < 100) {
            this.moveTimer = (r.nextInt(1000) * difficultyMod);
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
     * :TODO: Add the image update code here.
     */
    private void updatePosition() {
        int[] coords = this.enemy.getCurrentCoords();
        super.setY(coords[0]);
        super.setX(coords[1]);
    }

    /**
     * Stops the timer thread.
     */
    void stop() {
        this.timer.cancel();
        this.timer.purge();
    }

}
