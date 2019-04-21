package GameStates;

import attacks.ProjectileHandler;
import attacks.WeaponHandler;
import being.EnemyHandler;
import being.Protagonist;
import pickup.Items;
import score.Score;
import game.TutorialLevel;
import maps.MapHandler;
import pickup.PickUpItemHandler;
import sound.SoundHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;


public class GamePanel extends JPanel implements KeyListener {

    private ProjectileHandler projectiles;
    private Protagonist player;
    private int tileSize = 60;
    private EnemyHandler enemies;
    private MapHandler maps;
    private WeaponHandler weapons;
    private int weaponLocation = 800;
    private  Gamestate option;
    private Timer updateHealth;
    private Timer gameTimer;
    private Score score;
    private  int timeLeft = 1000 * 180;
    private String time;
    private PickUpItemHandler item;
    private TutorialLevel tutorial;
    private SoundHandler sound;

    // gameScreen Constructor
    public GamePanel(Score score, SoundHandler sound) {
        this.sound = sound;
        option = Gamestate.GAME;
        maps = new MapHandler(this);
        this.score = score;
        item = new PickUpItemHandler(maps, sound);
        enemies = new EnemyHandler(tileSize, maps, score, item, sound);
        maps.addEnemyHandler(enemies);
        player = new Protagonist(tileSize, tileSize, tileSize, tileSize, "player.png", tileSize * 2, maps, enemies, sound);
        enemies.addPlayer(player);
        weapons = new WeaponHandler(maps, projectiles, player, enemies, this, sound);
        enemies.addWeaponHandler(weapons);
        tutorial = new TutorialLevel(maps, item);
        player.addTutorialLevel(tutorial);
        weapons.createImages(weaponLocation, -40, 40, 40);

        this.updateHealth = new Timer(2, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getHealth() <= 0) {
                    stopTimers();
                    score.lost();
                    option = Gamestate.LOSE;
                }
            }
        }));
        updateHealth.start();

        this.gameTimer = new Timer(100, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft -= 100;

                SimpleDateFormat df = new SimpleDateFormat("mm:ss");
                time = df.format(timeLeft);

                if (timeLeft <= 0) {
                    score.lost();
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

    public void gameWon(){
        stopTimers();
        score.considerTimeRemaining(timeLeft);
        score.considerHealth(player.getHealth());
        option = Gamestate.WIN;
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
        tutorial.paint(window);

        window.setColor(Color.white);
        window.setFont(new Font("TimesRoman", Font.PLAIN, 15));

        window.drawString("Score:", 250, 25);
        window.drawString(String.valueOf(score.getScore()), 300, 25);

        window.drawString("Level:", 400, 25);
        window.drawString(String.valueOf(maps.getCurrentLevel() + 1), 450, 25);

        window.drawString("Health:", 550, 25);
        window.drawString(String.valueOf(player.getHealth() + 1), 600, 25);

        window.drawString("Current Weapon:", 700, 25);

        window.drawString("Time Left:", 850, 25);
        if (time != null) {
            window.drawString(time, 930, 25);
        }
        repaint();
    }

    public void stopTimers() {
        weapons.stopTimers();
        enemies.stopTimers();
        player.stopTimers();
        updateHealth.stop();
        gameTimer.stop();
    }

    public void startTimers() {
        weapons.startTimers();
        enemies.startTimers();
        player.startTimers();
        updateHealth.start();
        gameTimer.start();
    }

    public Gamestate getOption() {
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

        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                stopTimers();
                option = Gamestate.MENU;
                break;
            case KeyEvent.VK_SPACE:
                if (maps.getCurrentLevel() == 0) {
                    tutorial.nextMessage();
                } else {
                    weapons.attack();
                }
                break;
            case KeyEvent.VK_T:
                weapons.addWeapon(Items.CUPIDBOW);
            case KeyEvent.VK_S:
                weapons.changeWeapon();
                break;
            case KeyEvent.VK_P:
                if (option == Gamestate.PAUSE) { //play game

                    startTimers();
                    option = Gamestate.GAME;
                    break;
                } else { //pause game
                    sound.pause();
                    stopTimers();
                    option = Gamestate.PAUSE;
                    break;
                }
            case KeyEvent.VK_PAGE_DOWN:
                maps.setFinalLevel();
                weapons.obtainAllWeapons();
                tutorial.beginGame();
                break;
            default:
                player.keyPressed(evt);
        }
    }
}
