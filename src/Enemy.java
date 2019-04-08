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
    private ArrayList<TileShape> currentObstacles;
    private ArrayList<TileShape> currentPortals;

    public Enemy(int xPos, int yPos, String image, int tile, ArrayList<TileShape> obs, ArrayList<TileShape> port, GamePanel game) {
        super(xPos, yPos, image, obs, port, game);
        this.tileSize = tile;
        dy = tileSize / 4;
        dx = tileSize / 2;
        currentObstacles = obs;
        currentPortals = port;
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