package attacks;

import being.Being;
import game.Direction;
import maps.MapHandler;
import graphics.TileShape;

import java.awt.*;

/**
 * this class represents a singular projectile (arrow).
 */
public class Projectile extends TileShape {

    private int dx;
    private int dy;
    private int currentXPos;
    private int currentYPos;
    private Direction direction;
    private Boolean isRenderable = true;
    private MapHandler maps;
    private  Boolean isImage;
    private int timer;

    public Projectile(Direction dir, int xPos, int yPos, int tileSize, MapHandler maps, String image, int w, int h) {
        super(xPos, yPos, w, h, image, true);
        setVelocity(dir, tileSize);
        direction = dir;
        currentXPos = xPos;
        currentYPos = yPos;
        this.maps = maps;
        isImage = true;
        timer = 0;
    }

    public int incrementTimer() {
        return timer++;
    }

    private void setVelocity(Direction dir, int tileSize) {
        switch (dir) {
            case NORTH:
                dx = 0;
                dy = -tileSize;
                break;
            case NORTH_EAST:
                dx = tileSize;
                dy = -tileSize;
                break;
            case EAST:
                dx = tileSize;
                dy = 0;
                break;

            case SOUTH_EAST:
                dx = tileSize;
                dy = tileSize;
                break;

            case SOUTH:
                dx = 0;
                dy = tileSize;
                break;

            case SOUTH_WEST:
                dx = -tileSize;
                dy = tileSize;
                break;

            case WEST:
                dx = -tileSize;
                dy = 0;
                break;

            case NORTH_WEST:
                dx = -tileSize;
                dy = -tileSize;
                break;
            default:
                dx = 0;
                dy = 0;
        }
        dx = dx * 2;
        dy = dy * 2;
    }

    public void move() { //movement for players projectile
        if (!isRenderable) {
            return;
        }

        if (isImage) {
            this.setX(this.getX() + dx);
            this.setY(this.getY() + dy);
        } else {
            currentXPos += dx;
            currentYPos += dy;
        }

    }

    /**
     * tracking of bullet for boss wolf
     * @param player
     */
    public void emove(Being player) {
        if (!isRenderable) {
            return;
        }

        int distX = player.getX() - this.getX();
        int distY = player.getY() - this.getY();

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
        this.setX(this.getX() + dx * 3);
        this.setY(this.getY() + dy * 3);

    }

    public boolean checkCollision() {
        Rectangle bulletRec = this.getBounds();
        for (TileShape obstacle : maps.getCurrentObstacles()) {
            Rectangle obstacleRec = obstacle.getBounds();
            if (bulletRec.intersects(obstacleRec)) {
                return true;
            }
        }
        return false;
    }

    public void setIsRenderable(Boolean bool) {
        isRenderable = bool;
    }

    public void paint(Graphics2D win) {
        if (!isRenderable) {
            System.out.println("not renderable");
        }

        if (!isImage) {
            win.fillOval(currentXPos, currentYPos, 5, 5);
        } else {
            this.renderShape(win);
        }

    }
}
