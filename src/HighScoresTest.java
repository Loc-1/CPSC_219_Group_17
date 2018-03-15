import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class HighScoresTest {

    @Test
    public void addHighScore() throws Exception {
        HighScores highScores = new HighScores();
        highScores.addHighScore("test", 15);
        highScores.addHighScore("test2", 20);
        highScores.addHighScore("test3", 2);
        highScores.addHighScore("test4", 21);
        highScores.addHighScore("test5", 17);
        highScores.addHighScore("test6", 13);
        highScores.addHighScore("test2", 5);
        highScores.addHighScore("test2", 221);
        highScores.addHighScore("test2", 109);
        highScores.addHighScore("test2", 16);
        highScores.addHighScore("test2", 88);
        assertEquals("Array length is wrong, must be exactly 10 not " + highScores.getHighScores().size(),
                10, highScores.getHighScores().size());

    }

    @Test
    public void clearHighScores() throws Exception {
        HighScores highScores = new HighScores();
        highScores.clearHighScores();
        assertEquals("Array should be empty, it contains " + highScores.getHighScores().size() + " items",
                0, highScores.getHighScores().size());
    }

    @Test
    public void getHighScores() throws Exception {
        HighScores highScores = new HighScores();
        ArrayList<Score> scores = highScores.getHighScores();

        for (Score s : scores) {
            System.out.println(s.playerHandle + ", " + s.score);
        }
    }

}