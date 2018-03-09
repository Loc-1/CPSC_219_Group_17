import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Josh
 * <p>
 * Handles the storage, retrieval, and setting of Score objects.
 */
public class HighScores {
    private ArrayList<Score> highScores = new ArrayList<>();

    /**
     * Default constructor automatically loads scores from the highscores.json file.
     */
    HighScores() {
        try {
            this.loadHighScores();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Adds a highscore to the Array of Scores.
     *
     * @param playerHandle the player's handle as a string.
     * @param score        the player's score as a string.
     */
    void addHighScore(String playerHandle, int score) {
        this.highScores.add(new Score(playerHandle, score));
        this.save();
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
     * Checks if the score is a high score.
     *
     * @param score the Player's score.
     * @return True if score is a high score.
     */
    @SuppressWarnings("unused")
    public boolean isHighScore(int score) {
        boolean isHighScore = false;

        for (Score s : highScores) {
            if (s.score > score) {
                isHighScore = true;
            }
        }

        return isHighScore;
    }

    /**
     * Saves the highscores to a .json file in the root.
     */
    private void save() {
        String json = new Gson().toJson(this.highScores);

        try {
            FileWriter file = new FileWriter("highscores.json");
            file.write(json);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Loads the highscores from a .json file.
     */
    private void loadHighScores() {
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader("highscores.json"));
            this.highScores = gson.fromJson(reader, highScores.getClass());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Inner class represents a new score object.
     */
    class Score {
        @SuppressWarnings("unused")
        final String playerHandle;
        final int score;

        Score(String playerHandle, int score) {
            this.playerHandle = playerHandle;
            this.score = score;
        }
    }

}