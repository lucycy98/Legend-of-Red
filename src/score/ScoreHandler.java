package score;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * this class deals with high scores.
 */
public class ScoreHandler {

    private static final String HIGHSCORE_FILE = "scores.dat";
    private static ObjectOutputStream outputStream = null;
    private static ObjectInputStream inputStream = null;

    private static ArrayList<Integer> highscores;
    private int maxScores = 3;

    public ScoreHandler() {
        loadScoreFile();
        if (highscores == null) {
            highscores = new ArrayList<>();
            highscores.add(0);
            highscores.add(0);
            highscores.add(0);
        }
    }

    public void addGameScore(int score) {
        highscores.add(score);
    }

    public int getTopScore(int index) {
        if (index < 0 || index > maxScores - 1) {
            return -1;
        }
        Collections.sort(highscores);
        return highscores.get(highscores.size() - index - 1);
    }


    /**
     * Opens the high score file and loads the high score
     * data into memory
     */
    public static void loadScoreFile() {
        try {
            inputStream = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE));
            highscores = (ArrayList<Integer>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            //none
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes out the high score data to disk from memory
     */
    public static void updateScoreFile() {
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE));
            outputStream.writeObject(highscores);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
