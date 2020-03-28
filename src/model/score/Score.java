package model.score;

import java.io.Serializable;

/**
 * this class represents the model.score of the player
 * final model.score depends on difficulty, wolves killed, health and time remaining
 */
public class Score implements Serializable {

    private int score;
    private int totalLevels;
    int maxLevels = 5;
    int difficulty;

    public Score(int difficulty){
        score = 0;
        this.difficulty = difficulty;
    }

    public void lost(){
        score = 0;
    }

    public void totalLevels(int levels){
        totalLevels = levels;
    }

    public void killWolf(int level){
        if (level < maxLevels / 2){
            score = score + 30 * difficulty;
        } else if (level == maxLevels - 1) { //boss level
            score = score + 100 * difficulty;
        } else {
            score = score + 50 * difficulty;
        }
    }

    public void damageWolf(int level){
        if (level < maxLevels / 2){
            score = score + 5 * difficulty;
        } else {
            score = score + 10 * difficulty;
        }
    }

    public void considerHealth(int health){
        score = score - (99 - (int)(health * 1.2) * difficulty);
    }

    public void considerTimeRemaining(int time){
        score = score + time/1000 * difficulty;
    }

    public int getScore(){
        return score;
    }
}
