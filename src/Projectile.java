import java.awt.*;

public class Projectile {

    private int dx;
    private int dy;

    private int currentXPos;
    private int currentYPos;
    private Direction direction;

    public Projectile(Direction dir, int xPos, int yPos, int tileSize) {
        setVelocity(dir, tileSize);
        direction = dir;
        currentXPos = xPos;
        currentYPos = yPos;
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

    public void move() {
        currentXPos += dx;
        currentYPos += dy;
    }

    public Boolean checkCollision() {
        if (currentXPos < 10 || currentXPos > 1200 || currentYPos < 10 || currentYPos > 700) {
            return true;
        }
        return false;
    }

    public void paint(Graphics2D win) {
        win.fillOval(currentXPos, currentYPos, 5, 5);
    }
}
