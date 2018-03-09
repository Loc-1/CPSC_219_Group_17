import java.util.ArrayList;

/**
 * @author Josh
 * <p>
 * Handles the storage of Score objects.
 */
public class HighScores {
    ArrayList<Score> highScores = new ArrayList<>();

    public void addHighScore(String playerHandle, int score) {
        this.highScores.add(new Score(playerHandle, score));
    }

    public ArrayList<Score> getHighScores() {
        return this.highScores;
    }

    /**
     * Inner class represents a new score object.
     */
    public class Score {
        String playerHandle;
        int score;

        Score(String playerHandle, int score) {
            this.playerHandle = playerHandle;
            this.score = score;
        }
    }
}