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
public class Protagonist extends Being{

    // co-ordinates of player
    private int dx, dy;
    int tileSize;
    private Rectangle2D rec;
    private Direction currentDirection = Direction.NORTH_EAST;
    Boolean pressUp = false;
    Boolean pressDown = false;
    Boolean pressLeft = false;
    Boolean pressRight = false;

    public Protagonist(int xPos, int yPos, String image, int tile,MapHandler maps, ProjectileHandler ph) {
        super(xPos, yPos, image, maps, ph);
        this.tileSize = tile;
    }

    public Direction getDir() {
        return currentDirection;
    }

    public void paint(Graphics2D win) {
        movePlayer();
        renderShape(win);
    }

    public void movePlayer() {
        int currentX = getX();
        setX(currentX + dx);
        int currentY = getY();
        setY(currentY + dy);
        checkCollision();
        checkPortal();
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
            dy = -tileSize;
        } else if (pressDown) {
            dy = tileSize;
        } else {
            dy = 0;
        }

        if (pressRight && pressLeft) {
            dx = 0;
        } else if (pressLeft) {
            dx = -tileSize;
        } else if (pressRight) {
            dx = tileSize;
        } else {
            dx = 0;
        }
    }

    public void damageHealth(){
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

}
