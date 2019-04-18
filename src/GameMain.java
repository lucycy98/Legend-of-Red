import Engine.GameEngine;

import javax.swing.*;
import java.awt.*;

/**
 * where the game starts
 */
public class GameMain {

    public static void main(String[] args) {

        JFrame gameWindow = new JFrame("Game 1.1");
        gameWindow.setSize(1030, 790);
        gameWindow.setVisible(true);
        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel playScreen;
        playScreen = new GamePanel();
        playScreen.setBackground(Color.BLACK);
        gameWindow.add(playScreen);
        gameWindow.validate();
        playScreen.requestFocus();

    }
}
