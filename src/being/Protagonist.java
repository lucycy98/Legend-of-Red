package being;

import game.Direction;
import game.Timers;
import game.TutorialLevel;
import maps.MapHandler;
import graphics.TileShape;
import sound.SoundHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.TimerTask;

/**
 *  this class represents the player / red riding hood
 *  its movements are controlled by arrow keys
 */
public class Protagonist extends Being implements Timers {

    private EnemyHandler enemies;
    private int dx, dy;
    private int tileSize;
    private Direction currentDirection = Direction.NORTH_EAST;
    private MapHandler maps;
    private Boolean pressUp = false;
    private Boolean pressDown = false;
    private Boolean pressLeft = false;
    private Boolean pressRight = false;
    private int health;
    private int speed = 1;
    private int buffer = 50;
    private Timer velocity_timer;
    private boolean isInvincible;
    private TutorialLevel tutorial;
    private Boolean canMove;
    private SoundHandler sound;
    private Rectangle smallplayerRec;
    private TutorialLevel tutorialEnd;

    public Protagonist(int xPos, int yPos, int width, int height, String image, int tile, MapHandler maps, EnemyHandler enemies, SoundHandler sound) {
        super(xPos, yPos, width, height, image);
        this.sound = sound;
        canMove = true;
        this.tileSize = tile;
        this.maps = maps;
        this.enemies = enemies;
        health = 99;
        this.velocity_timer = new Timer(1000/200, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkCollision(maps.getCurrentObstacles());
                if (canMove){
                    movePlayer();
                }
                checkPortal();

            }
        }));
        velocity_timer.start();
        isInvincible = false;
    }

    //////////// movement and direction \\\\\\\\\\\\\\\\

    /**
     * direction that the player is facing - useful for weapon directions
     */
    public void face() {
        if (pressUp) {
            if (pressRight) {
                currentDirection = Direction.NORTH_EAST;
            } else if (pressLeft) {
                currentDirection = Direction.NORTH_WEST;
            } else {
                currentDirection = Direction.NORTH;
            }
        } else if (pressDown) {
            if (pressRight) {
                currentDirection = Direction.SOUTH_EAST;
            } else if (pressLeft) {
                currentDirection = Direction.SOUTH_WEST;
            } else {
                currentDirection = Direction.SOUTH;
            }
        } else if (pressRight) {
            currentDirection = Direction.EAST;
        } else if (pressLeft) {
            currentDirection = Direction.WEST;
        }
    }

    public void setVelocity() {
        if (pressUp && pressDown) {
            dy = 0;
        } else if (pressUp) {
            dy = -speed;
        } else if (pressDown) {
            dy = speed;
        } else {
            dy = 0;
        }

        if (pressRight && pressLeft) {
            dx = 0;
        } else if (pressLeft) {
            dx = -speed;
        } else if (pressRight) {
            dx = speed;
        } else {
            dx = 0;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            pressLeft = false;
        }

        if (key == KeyEvent.VK_RIGHT) {
            pressRight = false;
        }

        if (key == KeyEvent.VK_UP) {
            pressUp = false;
        }

        if (key == KeyEvent.VK_DOWN) {
            pressDown = false;
        }

        setVelocity();
        face();
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            pressLeft = true;
        }

        if (key == KeyEvent.VK_RIGHT) {
            pressRight = true;
        }

        if (key == KeyEvent.VK_UP) {
            pressUp = true;
        }

        if (key == KeyEvent.VK_DOWN) {
            pressDown = true;
        }
        setVelocity();
        face();
    }

    public void movePlayer() {
        int currentX = getX();
        setX(currentX + dx);
        int currentY = getY();
        setY(currentY + dy);
    }


    public void canMove(Boolean move){
        canMove = move;
    }

    ////////////////// HEALTH \\\\\\\\\\\\\\\\\\\\

    public void damageHealth() {
        health -= 5;
        flash();
    }

    public void flash() {
        changeImage("damagedPlayer.png");
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Your database code here
                changeImage("player.png");

            }
        }, 200);
    }

    public void healthUp() {
        int up = 0;
        sound.play("healthUp.wav");
        if (health < 30) {
            up = 40;
        } else if (health < 70) {
            up = 20;
        } else if (health < 90) {
            up = 9;
        }
        health = health + up;
    }

    /**
     * checks if enemy is attacking
     * @param enemy
     * @return
     */
    public boolean enemyIsAttacking(Enemy enemy) {
        smallplayerRec = this.getSmallerBounds();
        Rectangle obstacleRec = enemy.getBounds();

        if (isInvincible) {
            return false;
        }

        if (smallplayerRec.intersects(obstacleRec)) {
            if (!enemy.isAttacking()) {
                sound.play("loseHealth.wav");
                health -= 5;
                flash();
                enemy.setAttackStatus(true);
            }
            return true;
        } else {
            enemy.setAttackStatus(false);
        }
        return false;
    }

    /**
     * check if player is colliding with a portal
     */
    private void checkPortal() {
        Rectangle playerRec = this.getBounds();
        TileShape fportal = maps.getCurrentForwardPortal();
        if (fportal != null){
            Rectangle fportalRec = fportal.getBounds();
            if (playerRec.intersects(fportalRec)) {
                if (maps.getCurrentLevel() == 0) {
                    tutorial.beginGame();
                }
                maps.setNextLevel();
                if (maps.getCurrentLevel() == 4 && tutorialEnd.canTutorial()){
                    tutorialEnd.startTutorialEnd();
                }

                if (!maps.gameIsWon()){
                    sound.play("portal.wav");
                    enemies.setEnemy();
                    this.setX(tileSize + buffer);
                    this.setY(tileSize * 3 + buffer);
                }
            }
        }
        TileShape bportal = maps.getCurrentBackwardPortal();
        if (bportal != null){
            Rectangle bportalRec = bportal.getBounds();
            if (playerRec.intersects(bportalRec)) {
                maps.setPreviousLevel();
                if (maps.getCurrentLevel() == 0) {
                    tutorial.backLevel();
                } else if (maps.getCurrentLevel() == 3){
                    enemies.startTimers();
                }
                enemies.setEnemy();
                this.setX(tileSize * 6 + buffer);
                this.setY(tileSize * 3 + buffer);
            }
        }
    }

    /**
     * when player obtains wolfskin, chnage transparency.
     * @param time
     */
    public void setInvincible(int time) {
        isInvincible = true;
        changeImage("transparentplayer.png");java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Your database code here
                isInvincible = false;
                changeImage("player.png");
            }
        }, 5000);
    }

    /////////////// OTHER METHODS \\\\\\\\\\\\\\\\\\\

    public void addTutorialLevel(TutorialLevel tut) {
        tutorial = tut;
    }

    public void addTutorialLevelEnd(TutorialLevel tut) {
        tutorialEnd = tut;
    }

    public int getHealth() {
        return health;
    }

    public Direction getDir() {
        return currentDirection;
    }

    public void paint(Graphics2D win) {
        renderShape(win);
    }

    public boolean isInvincible(){
        return isInvincible;
    }

    @Override
    public void stopTimers() {
        velocity_timer.stop();
    }

    @Override
    public void startTimers() {
        velocity_timer.start();
    }
}
