package model.being;

import model.TileShape;

import java.awt.*;
import java.util.ArrayList;

/**
 * this class represents a living entity e.g wolf or player
 */
public class Being extends TileShape {

    public Being(int xPos, int yPos, int width, int height, String image) {
        super(xPos, yPos, width, height, image, true);
    }

    public double getDistance(Being otherBeing) {
        int dx = this.getX() - otherBeing.getX();
        int dy = this.getY() - otherBeing.getY();

        double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        return distance;
    }

    /**
     * checks for collision and doesnt allow moving into the obstacle.
     * @param obstacles
     */
    public void checkCollision(ArrayList<TileShape> obstacles) {

        Rectangle playerRec = this.getBounds();

        for (TileShape obstacle : obstacles) {
            Rectangle obstacleRec = obstacle.getBounds();

            if (playerRec.intersects(obstacleRec)) {

                if (this.getY() + 10 > obstacle.getY() + obstacle.getHeight()) { //intersects top
                    this.setY(obstacle.getY() + obstacle.getHeight());
                }

                if (this.getX() - 10 < obstacle.getX() - this.getWidth()) {//intersects left
                    this.setX(obstacle.getX() - this.getWidth());
                }

                if (this.getX() + 10 > obstacle.getX() + obstacle.getWidth()) { //intersects right
                    this.setX(obstacle.getX() + obstacle.getWidth());
                }

                if (this.getY() - 10 < obstacle.getY() - this.getHeight()) { //intersect bottom
                    this.setY(obstacle.getY() - this.getHeight());
                }
            }
        }
    }

    /**
     * checks if x and y are valid moves i.e not intersecting with an obstacle.
     * @param obstacles
     * @param x
     * @param y
     * @return
     */
    public boolean isValidMove(ArrayList<TileShape> obstacles, int x, int y) {

        Rectangle playerRec = this.getBounds(x, y);

        for (TileShape obstacle : obstacles) {
            Rectangle obstacleRec = obstacle.getBounds();

            if (playerRec.intersects(obstacleRec)) {
                return false;
            }
        }
        return true;
    }

    public void damageHealth() {
        System.out.println("damaged health");
    }

}
