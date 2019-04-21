package score;

public class Score {

    private int score;
    private int totalLevels;
    int maxLevels = 5;

    public Score(){
        score = 0;
    }

    public void lost(){
        score = 0;
    }

    public void totalLevels(int levels){
        totalLevels = levels;
    }

    public void killWolf(int level){
        if (level < maxLevels / 2){
            score = score + 30;
        } else if (level == maxLevels - 1) { //boss level
            score = score + 100;
        } else {
            score = score + 50;
        }
    }

    public void damageWolf(int level){
        if (level < maxLevels / 2){
            score = score + 5;
        } else {
            score = score + 10;
        }
    }

    public void considerHealth(int health){
        score = score - (99 - (int)(health * 1.2));
    }

    public void considerTimeRemaining(int time){
        score = score + time/1000;
    }

    public int getScore(){
        return score;
    }
}
