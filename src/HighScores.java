import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author Josh
 * <p>
 * Handles the storage, retrieval, and setting of Score objects. Limits the total number of high scores to a max of 10.
 */
abstract class HighScores {
    private static final ArrayList<Score> highScores = new ArrayList<>();

    /**
     * Adds a highscore to the Array of Scores if the score is actually a high score.
     *
     * @param playerHandle the player's handle as a string.
     * @param score        the player's score as a string.
     */
    static void add(String playerHandle, int score) {
        highScores.add(new Score(playerHandle, score));
        save();
    }

    /**
     * @return an ArrayList<Score> containing all the HighScores after loading them from disk.
     */
    static ArrayList<Score> get() {
        load();

        return highScores;
    }

    /**
     * Clears the high score array and saves it.
     */
    static void clear() {
        highScores.clear();
        save();

    }

    /**
     * Checks if the score is a high score. If highScores.size < 10 all scores are high scores.
     *
     * @param score the Player's score.
     * @return True if score is a high score.
     */
    static boolean isHighScore(int score) {
        boolean isHighScore;

        if (highScores.isEmpty()) {
            isHighScore = true;
        } else if (highScores.size() > 10) {
            isHighScore = true;
        } else if (score == highScores.get(0).score) {
            highScores.remove(highScores.get(0));
            save();
            isHighScore = true;
        } else isHighScore = score > highScores.get(0).score;

        return isHighScore;
    }

    /**
     * Saves the highscores to a .ser (serialized object) file in the root.
     */
    private static void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("highscores.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            sort();
            objectOut.writeObject(highScores);
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This sorts the list in ascending order and ensures the list only contains a max of ten entries.
     */
    private static void sort() {
        Comparator<Score> scoreComparator = Comparator.comparingInt(Score::getScore);
        highScores.sort(scoreComparator);
        while (highScores.size() > 10) {
            highScores.remove(0); // Removes the lowest score from the list.
        }

    }

    /**
     * Loads the highscores from a .ser (serialized object) file.
     */
    private static void load() {
        try {
            if (highScores.isEmpty()) { // Prevent load if highScores isn't empty.
                FileInputStream fileIn = new FileInputStream("highscores.ser");
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);

                @SuppressWarnings("unchecked")
                ArrayList<Score> scoresIn = (ArrayList<Score>) objectIn.readObject();

                highScores.addAll(scoresIn);
                objectIn.close();
                fileIn.close();
                sort();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}