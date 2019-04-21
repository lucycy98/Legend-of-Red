package score;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreHandler {

    private ArrayList<Integer> highscores;
    private int maxScores = 3;

    public ScoreHandler(){
        highscores = new ArrayList<>();
        highscores.add(0);
        highscores.add(0);
        highscores.add(0);
    }

    public void addGameScore(int score){
        highscores.add(score);
    }


    public int getTopScore(int index){
        if (index < 0 || index > maxScores - 1){
            return -1;
        }
        Collections.sort(highscores);
        return highscores.get(highscores.size() - index - 1);
    }
}
