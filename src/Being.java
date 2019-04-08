import java.awt.*;
import java.util.ArrayList;

/**
 * this class represents a tile shape with an image e.g hedge, enemy etc
 */
public class Being extends TileShape {

    private MapHandler maps;
    private ProjectileHandler  projectileHandler;
    private int level;


    public Being(int xPos, int yPos, String image, MapHandler maps, ProjectileHandler ph) {
        super(xPos, yPos, image, true);
        this.projectileHandler = ph;
        level = 0;
        this.maps = maps;
    }

    public void checkPortal() {
        Rectangle playerRec = this.getBounds();
        for (TileShape portal : maps.getCurrentPortal()) {
            Rectangle portalRec = portal.getBounds();
            if (playerRec.intersects(portalRec)) {
                maps.setNextLevel();
                this.setX(40);
                this.setY(40);
            }
        }
    }

    public void checkCollision() {

        Rectangle playerRec = this.getBounds();

        for (TileShape obstacle : maps.getCurrentObstacles()) {
            Rectangle obstacleRec = obstacle.getBounds();

            if (playerRec.intersects(obstacleRec)) {

                if (this.getX() - 6 < obstacle.getX() - this.getWidth()) {//intersects left
                    this.setX(obstacle.getX() - this.getWidth());
                }

                if (this.getX() + 6 > obstacle.getX() + obstacle.getWidth()) { //intersects right
                    this.setX(obstacle.getX() + obstacle.getWidth());
                }

                if (this.getY() - 6 < obstacle.getY() - this.getHeight()) { //intersect bottom
                    this.setY(obstacle.getY() - this.getHeight());
                }

                if (this.getY() + 6 > obstacle.getY() + obstacle.getHeight()) { //intersects top
                    this.setY(obstacle.getY() + obstacle.getHeight());
                }
            }
        }
    }

    public void damageHealth() {
        System.out.println("bullet hit player");
    }

    public Boolean checkCollisionWeapon() {
        Rectangle playerRec = this.getBounds();
        for (Projectile bullet : projectileHandler.getProjectiles()) {
            Rectangle bulletRec = bullet.getBounds();
            if (playerRec.intersects(bulletRec)) {
                return true;
            }
        }
        return false;
    }

}
