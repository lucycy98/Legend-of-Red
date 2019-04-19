import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Score {

    private int score;
    private int totalLevels;

    public Score(){
        score = 0;
    }

    public void totalLevels(int levels){
        totalLevels = levels;
    }

    public void killWolf(int level){
        score = score + 50;

    }

    public void damageWolf(int level){
        score = score + 10;
    }

    public int getScore(){
        return score;
    }

}
