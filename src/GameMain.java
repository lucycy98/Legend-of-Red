import Engine.GameEngine;

import javax.swing.*;
import java.awt.*;

/**
 * where the game starts
 */
public class GameMain {

    public static void main(String[] args) throws InterruptedException {

        JFrame gameWindow = new JFrame("Game 1.1");
        gameWindow.setSize(1040, 850);
        gameWindow.setVisible(true);
        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Menu menu;
        GamePanel playScreen;

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
                    while (playScreen.getOption() == Gamestate.GAME) {
                        Thread.sleep(1);
                    }
                    gameWindow.remove(playScreen);
                    state = playScreen.getOption();
            }
        }
        gameWindow.dispose();
        System.exit(0);
    }
}
