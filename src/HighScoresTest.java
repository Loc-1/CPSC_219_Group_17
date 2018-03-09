import org.junit.Test;

import java.util.ArrayList;

public class HighScoresTest {

    @Test
    public void addHighScore() throws Exception {
        HighScores highScores = new HighScores();
        highScores.addHighScore("test", 15);
        highScores.addHighScore("test2", 20);
    }

    @Test
    public void clearHighScores() throws Exception {
        HighScores highScores = new HighScores();
        highScores.clearHighScores();
        ArrayList<HighScores.Score> scores = highScores.getHighScores();

        System.out.println(scores);
    }

    @Test
    public void getHighScores() throws Exception {
        HighScores highScores = new HighScores();
        ArrayList<HighScores.Score> scores = highScores.getHighScores();

        System.out.println(scores);
    }

    @Test
    public void isHighScore() throws Exception {
    }
}