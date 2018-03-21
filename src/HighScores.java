import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author Josh
 * <p>
 * Handles the storage, retrieval, and setting of Score objects. Limits the total number of high scores to a max of 10.
 */
@SuppressWarnings("TryWithIdenticalCatches")
        // Needed for automated code review.
class HighScores {
    private final ArrayList<Score> highScores = new ArrayList<>();

    /**
     * Default constructor automatically loads scores from the highscores.ser file.
     */
    HighScores() {
        try {
            this.loadHighScores();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Adds a highscore to the Array of Scores if the score is actually a high score.
     *
     * @param playerHandle the player's handle as a string.
     * @param score        the player's score as a string.
     */
    void addHighScore(String playerHandle, int score) {
        if (this.isHighScore(score)) {
            this.highScores.add(new Score(playerHandle, score));
            this.save();
        }

    }

    /**
     * @return an ArrayList<Score> containing all the HighScores.
     */
    ArrayList<Score> getHighScores() {
        return this.highScores;
    }

    /**
     * Clears the high score array and saves it.
     */
    void clearHighScores() {
        this.loadHighScores();
        this.highScores.clear();
        this.save();

    }

    /**
     * Checks if the score is a high score. If the highScores array.size <= 10 all scores are high scores.
     *
     * @param score the Player's score.
     * @return True if score is a high score.
     */
    public boolean isHighScore(int score) {
        boolean isHighScore = false;
        if (this.highScores.size() >= 10) {
            for (Score s : highScores) {
                if (s.score > score) {
                    isHighScore = true;
                }
            }
        } else {
            System.out.println("Boo!");
            isHighScore = true;
        }

        return isHighScore;
    }

    /**
     * Saves the highscores to a .ser (serialized object) file in the root.
     */
    private void save() {
        this.sortScores();

        try {
            FileOutputStream fileOut = new FileOutputStream("highscores.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(this.highScores);
            objectOut.close();
            fileOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This sorts the list in ascending order and ensures the list only contains a max of ten entries.
     */
    private void sortScores() {
        Comparator<Score> scoreComparator = Comparator.comparingInt(Score::getScore);
        this.highScores.sort(scoreComparator);

        while (this.highScores.size() > 10) {
            this.highScores.remove(0); // Removes the lowest score from the list.
        }

    }

    /**
     * Loads the highscores from a .ser (serialized object) file.
     */
    private void loadHighScores() {
        try {
            FileInputStream fileIn = new FileInputStream("highscores.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            // noinspection unchecked (manual casting is valid in this case.)
            ArrayList<Score> scoresIn = (ArrayList<Score>) objectIn.readObject();

            for (Score s : scoresIn) {
                this.addHighScore(s.playerHandle, s.score);
            }

            objectIn.close();
            fileIn.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) { // These two are needed to keep errors from throwing up (for no reason.)
            e.printStackTrace();             // Both are technically covered under the FileNotFound exception.
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}