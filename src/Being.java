import java.awt.*;
import java.util.ArrayList;

/**
 * this class represents a tile shape with an image e.g hedge, enemy etc
 */
public class Being extends TileShape {

    private ProjectileHandler  projectileHandler;
    private Dagger dagger;

    public Being(int xPos, int yPos, int width, int height, String image, ProjectileHandler ph) {
        super(xPos, yPos, width, height, image, true);
        this.projectileHandler = ph;
    }

    public boolean checkCollision(ArrayList<TileShape> obstacles) {

        Rectangle playerRec = this.getBounds();

        for (TileShape obstacle : obstacles) {
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

                return true;
            }
        }
        return false;
    }

    public void damageHealth() {
        System.out.println("bullet hit player");
    }

}
