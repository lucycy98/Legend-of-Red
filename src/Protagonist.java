import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * keep incrementing x, y until key release OR another arrow is pressed.
 */
public class Protagonist extends Being implements Timers {

    private EnemyHandler enemies;
    // co-ordinates of player
    private int dx, dy;
    int tileSize;
    private Rectangle2D rec;
    private Direction currentDirection = Direction.NORTH_EAST;
    private MapHandler maps;
    Boolean pressUp = false;
    Boolean pressDown = false;
    Boolean pressLeft = false;
    Boolean pressRight = false;
    int health;
    int speed = 2;
    int buffer = 50;
    private int score = 0;
    boolean beingAttacked;
    Timer velocity_timer;


    public Protagonist(int xPos, int yPos, int width, int height, String image, int tile, MapHandler maps, EnemyHandler enemies) {
        super(xPos, yPos, width, height, 2, image);
        this.tileSize = tile;
        this.maps = maps;
        this.enemies = enemies;
        health = 99;
        beingAttacked = false;
        this.velocity_timer = new Timer(1000 / 300, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer();
            }
        }));
        velocity_timer.start();

    }



    public void checkPortal() {
        Rectangle playerRec = this.getBounds();
        for (TileShape portal : maps.getCurrentForwardPortal()) {
            Rectangle portalRec = portal.getBounds();
            if (playerRec.intersects(portalRec)) {
                maps.setNextLevel();
                enemies.setEnemy();
                this.setX(tileSize + buffer);
                this.setY(tileSize + buffer);
            }
        }
        for (TileShape portal : maps.getCurrentBackwardPortal()) {
            Rectangle portalRec = portal.getBounds();
            if (playerRec.intersects(portalRec)) {
                maps.setPreviousLevel();
                enemies.setEnemy();
                this.setX(tileSize + buffer);
                this.setY(tileSize + buffer);
            }
        }
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

    public void movePlayer() {
        int currentX = getX();
        setX(currentX + dx);
        int currentY = getY();
        setY(currentY + dy);
        checkCollision(maps.getCurrentObstacles());
        checkPortal();
    }

    public boolean checkEnemy(Enemy enemy) {
        Rectangle playerRec = this.getBounds();
        Rectangle obstacleRec = enemy.getBounds();

        if (playerRec.intersects(obstacleRec)) {
            if (!enemy.attackStatus) {
                health -= 5;
                enemy.attackStatus = true;
            }
            return true;
        } else {
            enemy.attackStatus = false;
        }
        return false;
    }

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

    public void damageHealth() {
        System.out.println("bullet hit player");
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

    @Override
    public void stopTimers() {
        velocity_timer.stop();
    }

    @Override
    public void startTimers() {
        velocity_timer.start();
    }
}
