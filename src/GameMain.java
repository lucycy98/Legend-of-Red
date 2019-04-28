import GameStates.*;
import GameStates.Menu;
import score.Score;
import score.ScoreHandler;
import sound.SoundHandler;

import javax.swing.*;
import java.awt.*;

/**
 * where the game starts
 */
public class GameMain {

    public static void main(String[] args) throws InterruptedException {


        int width = 1020;
        int height = 830;
        JFrame gameWindow = new JFrame("Game of Red");
        gameWindow.setSize(width, height);
        gameWindow.setVisible(true);
        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GameStates.Menu menu;
        DifficultyPanel difficulty;
        GamePanel playScreen;
        LosePanel lose;
        WinPanel win;
        HighScorePanel scorePanel;
        Score score = null;
        ScoreHandler scoreHandler = new ScoreHandler();
        SoundHandler soundHandler = new SoundHandler();
        int diff = 1;

        Gamestate state = Gamestate.MENU;
        while(state != Gamestate.QUIT){
            switch (state){
                case MENU:
                    menu = new Menu(2,2);
                    menu.setBackground(Color.BLACK);
                    gameWindow.add(menu);
                    gameWindow.validate();
                    menu.requestFocus();
                    while (menu.getOption() == null) {
                        Thread.sleep(1);
                    }
                    gameWindow.remove(menu);
                    state = menu.getOption();
                    break;
                case DIFFICULTY:
                    System.out.println("difficulty chooser");
                    difficulty = new DifficultyPanel(2,2, diff);
                    difficulty.setBackground(Color.BLACK);
                    gameWindow.add(difficulty);
                    gameWindow.validate();
                    difficulty.requestFocus();
                    while (difficulty.getOption() == null) {
                        Thread.sleep(1);
                    }
                    gameWindow.remove(difficulty);
                    state = difficulty.getOption();
                    diff = difficulty.getDiff();
                    break;
                case HIGHSCORE:
                    System.out.println("highscore");
                    scorePanel = new HighScorePanel(scoreHandler);
                    scorePanel.setBackground(Color.BLACK);
                    gameWindow.add(scorePanel);
                    gameWindow.validate();
                    scorePanel.requestFocus();
                    while (scorePanel.getOption() == null) {
                        Thread.sleep(1);
                    }
                    gameWindow.remove(scorePanel);
                    state = scorePanel.getOption();
                    break;
                case GAME:
                    score = new Score(diff);
                    playScreen = new GamePanel(width, height, score, soundHandler, diff);
                    playScreen.setBackground(Color.BLACK);
                    gameWindow.add(playScreen);
                    gameWindow.validate();
                    playScreen.requestFocus();
                    while (playScreen.getOption() == Gamestate.GAME || playScreen.getOption() == Gamestate.PAUSE ) {
                        Thread.sleep(1);
                    }
                    gameWindow.remove(playScreen);
                    state = playScreen.getOption();
                    break;
                case LOSE:
                    if (score == null){
                        break;
                    }
                    lose = new LosePanel(score, soundHandler);
                    scoreHandler.addGameScore(score.getScore());
                    lose.setBackground(Color.BLACK);
                    gameWindow.add(lose);
                    gameWindow.validate();
                    lose.requestFocus();
                    while (lose.getOption() == null) {
                        Thread.sleep(1);
                    }
                    gameWindow.remove(lose);
                    state = lose.getOption();
                    break;
                case WIN:
                    if (score == null){
                        break;
                    }
                    win = new WinPanel(score, soundHandler);
                    scoreHandler.addGameScore(score.getScore());
                    win.setBackground(Color.BLACK);
                    gameWindow.add(win);
                    gameWindow.validate();
                    win.requestFocus();
                    while (win.getOption() == null) {
                        Thread.sleep(1);
                    }
                    gameWindow.remove(win);
                    state = win.getOption();
                    break;
            }
        }
        gameWindow.dispose();
        System.exit(0);
    }
}
