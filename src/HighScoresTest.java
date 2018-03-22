import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HighScoresTest {

    @Test
    public void addHighScore() throws Exception {
        HighScores.clear();
        HighScores.add("test", 15);
        HighScores.add("test2", 20);
        HighScores.add("test3", 1);
        HighScores.add("test4", 21);
        HighScores.add("test5", 17);
        HighScores.add("test6", 13);
        HighScores.add("test2", 2);
        HighScores.add("test2", 221);
        HighScores.add("test2", 109);
        HighScores.add("test2", 16);
        HighScores.add("test2", 88);
        assertEquals("Array length is wrong, must be exactly 10 not " + HighScores.get().size(),
                10, HighScores.get().size());

    }

    @Test
    public void getHighScores() throws Exception {
        for (Score s : HighScores.get()) {
            System.out.println(s.playerHandle + ", " + s.score);
        }

    }

    @Test
    public void isHighScore() throws Exception {
        assertEquals("1 is not a valid high score in this test.", false, HighScores.isHighScore(1));
        assertEquals("7 is a valid high score in this test.", true, HighScores.isHighScore(7));
        HighScores.add("Second Test", 7);
        assertEquals("", true, HighScores.isHighScore(10));
        HighScores.add("Third Test", 10);
        assertEquals("", false, HighScores.isHighScore(1));
        HighScores.add("Fourth Test", 1);
        assertEquals("", false, HighScores.isHighScore(5));
        HighScores.add("Fifth Test", 5);

        System.out.println("----------");
        for (Score s : HighScores.get()) {
            System.out.println(s.playerHandle + ", " + s.score);
        }

        assertEquals("", true, HighScores.isHighScore(13));
        HighScores.add("Begone!", 13);
        assertEquals("", true, HighScores.isHighScore(13));
        HighScores.add("I'm good!", 13);
        assertEquals("", false, HighScores.isHighScore(10));
        HighScores.add("If I'm here you're screwed", 10);

        System.out.println("----------");
        for (Score s : HighScores.get()) {
            System.out.println(s.playerHandle + ", " + s.score);
        }

        HighScores.clear();
        assertEquals("", true, HighScores.isHighScore(1));
        HighScores.add("Final Test", 1);
    }

    @Test
    public void clearHighScores() throws Exception {
        HighScores.clear();
        assertEquals("High scores should be empty, there were " + HighScores.get().size() + " items.",
                0, HighScores.get().size());

    }

}