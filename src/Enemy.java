import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * keep incrementing x, y until key release OR another arrow is pressed.
 */
public class Enemy extends Being {

    // co-ordinates of player
    private int dx, dy;
    private int rx, ry;
    int tileSize;
    private Direction currentDirection = Direction.NORTH_EAST;
    private Boolean isAlive = true;
    private MapHandler maps;
    private int timeLeft;
    private int level;
    public boolean canRangeAttack;
    public boolean friendly;

    public Enemy(int xPos, int yPos, int width, int height, String image, int tile, MapHandler maps, ProjectileHandler ph, int level, boolean canRangeAttack) {
        super(xPos, yPos, width, height, 1, image, ph);
        this.tileSize = tile;
        this.maps = maps;
        dy = tileSize / 32;
        ry = tileSize / 32;
        dx = tileSize / 32;
        rx = tileSize / 32;
        timeLeft = 0;
        this.level = level;
        this.canRangeAttack = canRangeAttack;
        friendly = false;
    }

    public int getLevel() {
        return level;
    }

    public Direction getDir() {
        return currentDirection;
    }

    public void paint(Graphics2D win) {
        renderShape(win);
    }

    @Override
    public void damageHealth() {
        System.out.println("bullet hit wolf");
        killWolf();
    }

    private void killWolf() {
        this.setIsRenderable(false);
        isAlive = false;
    }

    public Boolean getIsAlive() {
        return isAlive;
    }

    public void becomeFriendly() {
        friendly = true;
    }

    public void move() {
        int currentX = getX();
        int currentY = getY();

        if (checkCollision(maps.getCurrentObstacles())) {
            dx = dx * -1;
        }

        if (checkCollision(maps.getCurrentObstacles())) {
            dy = dy * -1;
        }
        setX(currentX + dx);
        setY(currentY + dy);
    }

    public void randomMovement() {
        int currentX = getX();
        int currentY = getY();

        int countDown = 50;
        int randX = ThreadLocalRandom.current().nextInt(-2, 2);
        int randY = ThreadLocalRandom.current().nextInt(-1, 1);

        if (timeLeft > 0) {
            //move
            setX(currentX + rx);
            setY(currentY + ry);
            timeLeft--;
        }

        if (checkCollision(maps.getCurrentObstacles()) | timeLeft == 0) {
            timeLeft = countDown;
            rx = randX;
            ry = randY;
        }

    }

    //line of sight tracking movement
    public void losTracking(int playerX, int playerY) {
        int currentX = getX();
        int currentY = getY();

        int distX = playerX - currentX;
        int distY = playerY - currentY;

        int scale = Math.max(Math.abs(distX), Math.abs(distY));

        int dx;
        int dy;

        if (scale != 0) {
            dx = (distX / scale);
            dy = (distY / scale);
        } else {
            dx = 0;
            dy = 0;
        }

        if (!checkCollision(maps.getCurrentObstacles())) {
            setX(currentX + dx);
            setY(currentY + dy);
        }
    }
}