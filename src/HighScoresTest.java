import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HighScoresTest {

    @Test
    public void addHighScore() throws Exception {
        HighScores.clearHighScores();
        HighScores.addHighScore("test", 15);
        HighScores.addHighScore("test2", 20);
        HighScores.addHighScore("test3", 1);
        HighScores.addHighScore("test4", 21);
        HighScores.addHighScore("test5", 17);
        HighScores.addHighScore("test6", 13);
        HighScores.addHighScore("test2", 2);
        HighScores.addHighScore("test2", 221);
        HighScores.addHighScore("test2", 109);
        HighScores.addHighScore("test2", 16);
        HighScores.addHighScore("test2", 88);
        assertEquals("Array length is wrong, must be exactly 10 not " + HighScores.getHighScores().size(),
                10, HighScores.getHighScores().size());

    }

    @Test
    public void getHighScores() throws Exception {
        for (Score s : HighScores.getHighScores()) {
            System.out.println(s.playerHandle + ", " + s.score);
        }

    }

    @Test
    public void isHighScore() throws Exception {
        assertEquals("1 is not a valid high score in this test.", false, HighScores.isHighScore(1));
        assertEquals("7 is a valid high score in this test.", true, HighScores.isHighScore(7));
        HighScores.addHighScore("Second Test", 7);
        assertEquals("", true, HighScores.isHighScore(10));
        HighScores.addHighScore("Third Test", 10);
        assertEquals("", false, HighScores.isHighScore(1));
        HighScores.addHighScore("Fourth Test", 1);
        assertEquals("", false, HighScores.isHighScore(5));
        HighScores.addHighScore("Fifth Test", 5);

        System.out.println("----------");
        for (Score s : HighScores.getHighScores()) {
            System.out.println(s.playerHandle + ", " + s.score);
        }

        assertEquals("", true, HighScores.isHighScore(13));
        HighScores.addHighScore("Begone!", 13);
        assertEquals("", true, HighScores.isHighScore(13));
        HighScores.addHighScore("I'm good!", 13);
        assertEquals("", false, HighScores.isHighScore(10));
        HighScores.addHighScore("If I'm here you're screwed", 10);

        System.out.println("----------");
        for (Score s : HighScores.getHighScores()) {
            System.out.println(s.playerHandle + ", " + s.score);
        }

        HighScores.clearHighScores();
        assertEquals("", true, HighScores.isHighScore(1));
        HighScores.addHighScore("Final Test", 1);
    }

    @Test
    public void clearHighScores() throws Exception {
        HighScores.clearHighScores();
        assertEquals("High scores should be empty, there were " + HighScores.getHighScores().size() + " items.",
                0, HighScores.getHighScores().size());

    }

}