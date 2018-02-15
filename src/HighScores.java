import java.io.*;

/**
 * Class Owner: Josh
 *
 * Stores high scores into a text files, reads the same file and returns a high score array (String[][]).
 */
public class HighScores {
    private Object[][] highScores = new Object[10][2];
    private String fileName = "scores.txt";

    public void main(String[] args) {

    }

    private File getFile() throws FileNotFoundException {
        File highScores = new File(this.fileName);
        Boolean fileExists = highScores.exists();

        if (fileExists) {
            return highScores;
        } else {
            throw new FileNotFoundException();
        }

    }

    private void loadFromFile() throws IOException {
        String line = null;
        File highScores = getFile();

        FileReader fileReader = new FileReader(highScores);

        try (BufferedReader reader = new BufferedReader(fileReader)) {
            int counter = 0;
            while ((line = reader.readLine()) != null) {
                String[] playerScore = line.split(", ");
                Integer.parseInt(playerScore[1]);
                this.highScores[counter][0] = playerScore[0];
                this.highScores[counter][1] = playerScore[1];
                counter++;
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            System.out.println("Could not find file " + fileName);
        }

    }

    public int[][] get() {
        return null;
    }

    public void set(String user_name, int score) {
    }

    public Object[][] getHighScores() {
        return highScores;
    }
}
