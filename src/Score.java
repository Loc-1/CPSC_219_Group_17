import java.io.Serializable;

/**
 * This class is needed to serialize the score objects in such a way that they can be recovered.
 */
final class Score implements Serializable {
    final String playerHandle;
    final int score;

    Score(String playerHandle, int score) {
        this.playerHandle = playerHandle;
        this.score = score;
    }

    int getScore() {  // This getter is needed for the comparator.
        return score;
    }

}
