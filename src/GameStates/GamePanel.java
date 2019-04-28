package GameStates;
import attacks.WeaponHandler;
import being.EnemyHandler;
import being.Protagonist;
import graphics.TileShape;
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

    private Protagonist player;
    private final int tileSize = 60;
    private EnemyHandler enemies;
    private MapHandler maps;
    private WeaponHandler weapons;
    private final int weaponLocation = 700;
    private  Gamestate option;
    private Timer updateHealth;
    private Timer gameTimer;
    private Score score;
    private  int timeLeft = 1000 * 300;
    private String time;
    private PickUpItemHandler item;
    private TutorialLevel tutorial;
    private TutorialLevel tutorialEnd;

    private SoundHandler sound;
    private int difficulty;
    private TileShape redSprite;
    private final SimpleDateFormat df = new SimpleDateFormat("mm:ss");
    private int height;
    private int width;
    private final int infoHeight = 25;

    // gameScreen Constructor
    public GamePanel(int width, int height, Score score, SoundHandler sound, int difficulty) {
        this.width = width;
        this.height = height;
        redSprite = new TileShape(0, -40, 40,40, "player.png", true);
        this.sound = sound;
        option = Gamestate.GAME;
        maps = new MapHandler(this);
        this.score = score;
        this.difficulty = difficulty;
        item = new PickUpItemHandler(maps, sound);
        enemies = new EnemyHandler(tileSize, maps, score, item, sound, difficulty);
        maps.addEnemyHandler(enemies);
        player = new Protagonist(tileSize, tileSize, tileSize, tileSize, "player.png", tileSize * 2, maps, enemies, sound);
        enemies.addPlayer(player);
        weapons = new WeaponHandler(maps, player, enemies, this, sound);
        enemies.addWeaponHandler(weapons);
        tutorial = new TutorialLevel(maps, item, width, true);
        tutorialEnd = new TutorialLevel(maps, item, width, false, enemies, this); //todo
        player.addTutorialLevel(tutorial);
        player.addTutorialLevelEnd(tutorialEnd);
        weapons.createImages(weaponLocation, -40, 40, 40);
        time = df.format(timeLeft);

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
                time = df.format(timeLeft);

                if (timeLeft <= 0) {
                    score.lost();
                    option = Gamestate.LOSE;
                    gameTimer.stop();
                }
            }
        }));

        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();
    }

    public void startGameTimer(){
        gameTimer.start();
    }

    public void stopGameTimer(){
        gameTimer.stop();
    }


    public void gameWon(){
        stopTimers();
        score.considerTimeRemaining(timeLeft);
        score.considerHealth(player.getHealth());
        option = Gamestate.WIN;
    }

    @Override
    public void paint(Graphics gr) {
        super.paint(gr);
        Graphics2D window = (Graphics2D) gr;
        window.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        maps.paint(window);
        player.paint(window);
        enemies.paint(window);
        weapons.paint(window);
        tutorial.paint(window);
        tutorialEnd.paint(window);
        redSprite.renderShape(window);

        window.setColor(Color.white);
        window.setFont(new Font("TimesRoman", Font.PLAIN, 15));

        int ratio = width/7;

        window.drawString("Score:", 1*ratio, infoHeight);
        window.drawString(String.valueOf(score.getScore()), 1*ratio + 60, infoHeight);

        window.drawString("Level:", 2*ratio, 25);
        window.drawString(String.valueOf(maps.getCurrentLevel()), 2*ratio + 60, infoHeight);

        window.drawString("Health:", 3*ratio, 25);
        window.drawString(String.valueOf(player.getHealth() + 1), 3*ratio + 80, infoHeight);

        window.drawString("Current Weapon:", 4*ratio, infoHeight);

        window.drawString("Time Left:", 5*ratio + 70, infoHeight);
        if (time != null) {
            window.drawString(time, 5*ratio + 170, infoHeight);
        }
        repaint();
    }

    public void paintOld(Graphics gr) {
        // Clears window
        super.paint(gr);
        Graphics2D window = (Graphics2D) gr;
        window.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        maps.paint(gr);
        player.paint(window);
        enemies.paint(window);
        weapons.paint(window);
        tutorial.paint(window);

        redSprite.renderShape(window);

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
                    System.out.println("next message");
                    tutorial.nextMessage();
                } else if(( maps.getCurrentLevel() == 4 && !tutorialEnd.isFinished())){
                    tutorialEnd.nextMessage();
                } else {
                    weapons.attack();
                }

                break;
            case KeyEvent.VK_C: //FOR TESTING!!!!!
                if (maps.getCurrentLevel() == 0){
                    break;
                }
                weapons.obtainAllWeapons();
                weapons.addWeapon(Items.CUPIDBOW);
                break;
            case KeyEvent.VK_W: //FOR TESTING!!!!!
                if (maps.getCurrentLevel() == 0){
                    break;
                }
                weapons.obtainAllWeapons();

                item.createItem(player.getX(), player.getY()+tileSize, Items.WOLFSKIN);
                break;
            case KeyEvent.VK_H:
                if (maps.getCurrentLevel() == 0){
                    break;
                }
                weapons.obtainAllWeapons();
                item.createItem(player.getX()+tileSize, player.getY(), Items.HEALTH);
            case KeyEvent.VK_ENTER:
                if (maps.getCurrentLevel() == 0){
                    tutorial.skip();
                }
                break;
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
                maps.setLevel(4);
                weapons.obtainAllWeapons();
                tutorial.beginGame();
                item.collectDagger();
                startGameTimer();
                tutorialEnd.startTutorialEnd();
                break;
            default:

                if (maps.getCurrentLevel() != 0 || tutorial.canMove()){
                    player.keyPressed(evt);
                }
                break;
        }
    }
}
