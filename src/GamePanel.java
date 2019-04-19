import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;


public class GamePanel extends JPanel implements KeyListener {

    ProjectileHandler projectiles;
    Protagonist player;
    int speed = 8;
    int tileSize = 60;
    EnemyHandler enemies;
    MapHandler maps;
    WeaponHandler weapons;
    int weaponLocation = 800;
    Gamestate option;
    Timer updateHealth;
    Timer gameTimer;
    Score score;
    int timeLeft = 1000 * 180;
    String time;

    // gameScreen Constructor
    public GamePanel() {
        option = Gamestate.GAME;
        maps = new MapHandler();
        score = new Score();
        enemies = new EnemyHandler(tileSize, maps, score);
        player = new Protagonist(tileSize, tileSize, tileSize, tileSize, "player.png", tileSize, maps, enemies);
        enemies.addPlayer(player);
        weapons = new WeaponHandler(maps, projectiles, player, enemies, this);
        enemies.addWeaponHandler(weapons);
        weapons.createImages(weaponLocation, -40, 40, 40);

        this.updateHealth = new Timer(2, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getHealth() <= 0){
                    option = Gamestate.LOSE;
                    updateHealth.stop();
                }
            }
        }));
        updateHealth.start();

        this.gameTimer = new Timer(100, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft -= 100;

                SimpleDateFormat df=new SimpleDateFormat("mm:ss");
                time = df.format(timeLeft);

                if(timeLeft<=0)
                {
                    option = Gamestate.LOSE;
                    gameTimer.stop();
                }
            }
        }));
        updateHealth.start();
        gameTimer.start();

        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();
    }

    @Override
    public void paint(Graphics gr) {
        // Clears window
        super.paint(gr);
        Graphics2D window = (Graphics2D) gr;
        window.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        maps.paint(gr);
        player.paint(window);
        enemies.paint(window);
        weapons.paint(window);

        window.setColor(Color.white);

        window.drawString("Score:", 250, 25);
        window.drawString(String.valueOf(score.getScore()), 300, 25);

        window.drawString("Level:", 400, 25);
        window.drawString(String.valueOf(maps.getCurrentLevel() + 1), 450, 25);

        window.drawString("Health:", 550, 25);
        window.drawString(String.valueOf(player.getHealth() + 1), 600, 25);

        window.drawString("Current Weapon:", 700, 25);

        window.drawString("Time Left:", 850, 25);
        if (time != null){
            window.drawString(time, 930, 25);
        }


        repaint();

    }

    public void stopTimers(){
        weapons.stopTimers();
        enemies.stopTimers();
        player.stopTimers();
        updateHealth.stop();
        gameTimer.stop();
    }

    public void startTimers(){
        weapons.startTimers();
        enemies.startTimers();
        player.startTimers();
        updateHealth.start();
        gameTimer.start();
    }

    public Gamestate getOption(){
        return option;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    @Override
    public void keyPressed(KeyEvent evt) {

        if (option == Gamestate.PAUSE){
            if (evt.getKeyCode() == KeyEvent.VK_P){
                startTimers();
                option = Gamestate.GAME;
            }
        } else {
            switch (evt.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                    weapons.attack();
                    break;
                case KeyEvent.VK_S:
                    weapons.changeWeapon();
                    break;
                case KeyEvent.VK_P:
                    stopTimers();
                    option = Gamestate.PAUSE;
                default:
                    player.keyPressed(evt);
            }
        }
    }
}
