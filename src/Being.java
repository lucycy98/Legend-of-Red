import java.awt.*;
import java.util.ArrayList;

/**
 * this class represents a tile shape with an image e.g hedge, enemy etc
 */
public class Being extends TileShape {

    private ArrayList<TileShape> currentObstacles;
    private ArrayList<TileShape> currentPortals;
    private GamePanel game;
    int level;


    public Being(int xPos, int yPos, String image, ArrayList<TileShape> obs, ArrayList<TileShape> port, GamePanel g) {
        super(xPos, yPos, image, true);
        game = g;
        currentObstacles = obs;
        currentPortals = port;
        level = 0;
    }

    public void setNewObstacles(ArrayList<TileShape> obs) {
        currentObstacles.clear();
        currentObstacles = obs;
    }

    public void checkPortal() {
        Rectangle playerRec = this.getBounds();

        for (TileShape portal : currentPortals) {
            Rectangle portalRec = portal.getBounds();

            if (playerRec.intersects(portalRec)) {
                level++;
                this.setX(40);
                this.setY(40);
            }
        }
    }

    public void checkCollision() {

        Rectangle playerRec = this.getBounds();

        for (TileShape obstacle : currentObstacles) {
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
        ArrayList<Projectile> bullets = game.getProjectiles();
        for (Projectile bullet : bullets) {
            Rectangle bulletRec = bullet.getBounds();
            if (playerRec.intersects(bulletRec)) {
                return true;
            }
        }
        return false;
    }

}
