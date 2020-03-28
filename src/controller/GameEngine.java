package controller;

import view.Gamestate;
import model.attacks.WeaponHandler;
import model.being.EnemyHandler;
import model.being.Protagonist;
import model.TileShape;
import model.pickup.Items;
import model.score.Score;
import model.TutorialLevel;
import model.maps.MapHandler;
import model.pickup.PickUpItemHandler;
import sound.SoundHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;


public class GameEngine implements KeyListener {

    private Protagonist player;
    private final int tileSize = 60;
    private EnemyHandler enemies;
    private MapHandler maps;
    private WeaponHandler weapons;
    private final int weaponLocation = 700;
    private Gamestate option;
    private Timer updateHealth;
    private Timer gameTimer;
    private Score score;
    private int timeLeft = 1000 * 300;
    private String time;
    private PickUpItemHandler item;
    private TutorialLevel tutorial;
    private TutorialLevel tutorialEnd;
    private SoundHandler sound;
    private int difficulty;
    private TileShape redSprite;
    private final SimpleDateFormat df = new SimpleDateFormat("mm:ss");


    // gameScreen Constructor
    public GameEngine(int width, Score score, SoundHandler sound, int difficulty) {
        redSprite = new TileShape(0, -40, 40, 40, "player.png", true);
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
    }

    public String getTime(){
        return time;
    }

    public void startGameTimer() {
        gameTimer.start();
    }
    public void stopGameTimer() {
        gameTimer.stop();
    }

    public int getCurrentScore(){
        return score.getScore();
    }

    public int getCurrentLevel(){
        return maps.getCurrentLevel();
    }

    public int getHealth(){
        return player.getHealth() + 1;
    }

    public void gameWon() {
        stopTimers();
        score.considerTimeRemaining(timeLeft);
        score.considerHealth(player.getHealth());
        option = Gamestate.WIN;
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

    public void paint(Graphics gr) {
        Graphics2D window = (Graphics2D) gr;
        maps.paint(window);
        player.paint(window);
        enemies.paint(window);
        weapons.paint(window);
        tutorial.paint(window);
        tutorialEnd.paint(window);
        redSprite.renderShape(window);
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
                } else if ((maps.getCurrentLevel() == 4 && !tutorialEnd.isFinished())) {
                    tutorialEnd.nextMessage();
                } else {
                    weapons.attack();
                }

                break;
            case KeyEvent.VK_C: //FOR TESTING!!!!!
                if (maps.getCurrentLevel() == 0) {
                    break;
                }
                weapons.obtainAllWeapons();
                weapons.addWeapon(Items.CUPIDBOW);
                break;
            case KeyEvent.VK_W: //FOR TESTING!!!!!
                if (maps.getCurrentLevel() == 0) {
                    break;
                }
                weapons.obtainAllWeapons();

                item.createItem(player.getX(), player.getY() + tileSize, Items.WOLFSKIN);
                break;
            case KeyEvent.VK_H:
                if (maps.getCurrentLevel() == 0) {
                    break;
                }
                weapons.obtainAllWeapons();
                item.createItem(player.getX() + tileSize, player.getY(), Items.HEALTH);
            case KeyEvent.VK_ENTER:
                if (maps.getCurrentLevel() == 0) {
                    tutorial.skip();
                }
                break;
            case KeyEvent.VK_S:
                weapons.changeWeapon();
                break;
            case KeyEvent.VK_P:
                if (option == Gamestate.PAUSE) { //play model.game
                    startTimers();
                    option = Gamestate.GAME;
                    break;
                } else { //pause model.game
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

                if (maps.getCurrentLevel() == 0 && !tutorial.canMove()) {
                    break;
                }

                if (maps.getCurrentLevel() == 4 && !tutorialEnd.canMove()) {
                    break;
                }

                player.keyPressed(evt);
        }
    }

    public Gamestate getOption() {
        return option;
    }
}
