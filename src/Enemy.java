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

    public Enemy(int xPos, int yPos, String image, int tile, MapHandler maps, ProjectileHandler ph) {
        super(xPos, yPos, image, ph);
        this.tileSize = tile;
        this.maps = maps;
        dy = tileSize / 4;
        ry = tileSize / 4;
        dx = tileSize / 2;
        rx = tileSize / 2;
        timeLeft = 0;
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

    public Boolean getIsAlive(){
        return isAlive;
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

    public void randomMovement(){
        int currentX = getX();
        int currentY = getY();

        int countDown = ThreadLocalRandom.current().nextInt(40, 50);
        int randX = ThreadLocalRandom.current().nextInt(-2, 2);
        int randY = ThreadLocalRandom.current().nextInt(-1, 1);

        if (timeLeft > 0){
            //move
            setX(currentX + rx);
            setY(currentY + ry);
            timeLeft --;
        }

        if (checkCollision(maps.getCurrentObstacles()) | timeLeft == 0){
            timeLeft = countDown;
            rx = randX;
            ry = randY;
        }

    }

}