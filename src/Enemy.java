import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * keep incrementing x, y until key release OR another arrow is pressed.
 */
public class Enemy extends Being {

    // co-ordinates of player
    private int dx, dy;
    int tileSize;
    private Direction currentDirection = Direction.NORTH_EAST;

    public Enemy(int xPos, int yPos, String image, int tile, MapHandler maps, ProjectileHandler ph) {
        super(xPos, yPos, image, maps.getCurrentObstacles(), ph);
        this.tileSize = tile;
        dy = tileSize / 4;
        dx = tileSize / 2;
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
    }


    public void move() {
        int currentX = getX();
        int currentY = getY();

        if (currentX <= 40 || currentX > 1200) {
            dx = dx * -1;
        }

        if (currentY <= 40 || currentY >= 700) {
            dy = dy * -1;
        }
        setX(currentX + dx);
        setY(currentY + dy);
    }
}