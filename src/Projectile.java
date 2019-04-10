import java.awt.*;

public class Projectile {

    private int dx;
    private int dy;
    private int currentXPos;
    private int currentYPos;
    private Direction direction;
    private Boolean isRenderable = true;
    private MapHandler maps;

    public Projectile(Direction dir, int xPos, int yPos, int tileSize, MapHandler maps) {
        setVelocity(dir, tileSize);
        direction = dir;
        currentXPos = xPos;
        currentYPos = yPos;
        this.maps = maps;
    }

    public Rectangle getBounds() {
        return new Rectangle(currentXPos, currentYPos, 5, 5);
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
        if (isRenderable) {
            currentXPos += dx;
            currentYPos += dy;
        }
    }

    public boolean checkCollision() {
        Rectangle bulletRec = this.getBounds();
        for (TileShape obstacle : maps.getCurrentObstacles()){
            Rectangle obstacleRec = obstacle.getBounds();
            if (bulletRec.intersects(obstacleRec)) {
                return true;
            }
        }
        return false;
    }

    public void setIsRenderable(Boolean bool){
        isRenderable = bool;
    }

    public void paint(Graphics2D win) {
        if (isRenderable) {
            win.fillOval(currentXPos, currentYPos, 5, 5);
        } else {
            //win.clearRect(currentXPos, currentYPos, 5, 5);
            System.out.println("not renderable");
        }
    }
}
