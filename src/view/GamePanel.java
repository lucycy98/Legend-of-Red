package view;

import controller.GameEngine;
import model.score.Score;
import sound.SoundHandler;
import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel{

    private int width;
    private final int infoHeight = 25;
    private final GameEngine gameEngine;

    // gameScreen Constructor
    public GamePanel(int width, int height, Score score, SoundHandler sound, int difficulty) {
        this.gameEngine = new GameEngine(width,score, sound,difficulty);
        this.width = width;
        this.addKeyListener(gameEngine);
        this.setFocusable(true);
        this.requestFocus();
    }

    public GameEngine getEngine(){
        return this.gameEngine;
    }

    @Override
    public void paint(Graphics gr) {
        super.paint(gr);
        gameEngine.paint(gr);
        Graphics2D window = (Graphics2D) gr;
        window.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        window.setColor(Color.white);
        window.setFont(new Font("TimesRoman", Font.PLAIN, 15));

        int ratio = width / 7;

        window.drawString("Score:", 1 * ratio, infoHeight);
        window.drawString(String.valueOf(gameEngine.getCurrentScore()), 1 * ratio + 60, infoHeight);

        window.drawString("Level:", 2 * ratio, 25);
        window.drawString(String.valueOf(gameEngine.getCurrentLevel()), 2 * ratio + 60, infoHeight);

        window.drawString("Health:", 3 * ratio, 25);
        window.drawString(String.valueOf(gameEngine.getHealth() + 1), 3 * ratio + 80, infoHeight);

        window.drawString("Current Weapon:", 4 * ratio, infoHeight);

        window.drawString("Time Left:", 5 * ratio + 70, infoHeight);
        if (gameEngine.getTime() != null) {
            window.drawString(gameEngine.getTime(), 5 * ratio + 170, infoHeight);
        }
        repaint();

    }


}
