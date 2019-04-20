import GameStates.GamePanel;
import GameStates.Gamestate;
import GameStates.Lose;
import GameStates.Menu;

import javax.swing.*;
import java.awt.*;

/**
 * where the game starts
 */
public class GameMain {

    public static void main(String[] args) throws InterruptedException {


        JFrame gameWindow = new JFrame("Game of Red");
        gameWindow.setSize(1040, 850);
        gameWindow.setVisible(true);
        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GameStates.Menu menu;
        GamePanel playScreen;
        Lose lose;

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
                case GAME:
                    playScreen = new GamePanel();
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
                    lose = new Lose(2,2);
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

            }
        }
        gameWindow.dispose();
        System.exit(0);
    }
}
