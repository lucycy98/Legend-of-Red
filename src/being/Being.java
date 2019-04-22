package being;

import game.Direction;
import graphics.TileShape;

import java.awt.*;
import java.util.ArrayList;

/**
 * this class represents a tile shape with an image e.g hedge, enemy etc
 */
public class Being extends TileShape {

    private int speed;

    public Being(int xPos, int yPos, int width, int height, int speed, String image) {
        super(xPos, yPos, width, height, image, true);
        this.speed = speed;
    }

    public double getDistance(Being otherBeing) {
        int dx = this.getX() - otherBeing.getX();
        int dy = this.getY() - otherBeing.getY();

        double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        return distance;
    }

    /**
     * checks collision and doesnt allow moving into the obstacle.
     *
     * @param obstacles
     */
    public boolean checkCollision(ArrayList<TileShape> obstacles) {

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
                return true;
            }
        }
        return false;
    }

    /**
     * returns what location w/ respect to the being, the obstacle is.
     * will be more computer friendly if we changed this to look at map tiles instead.
     */
    public ArrayList<Direction> checkCollisionDirection(ArrayList<TileShape> obstacles) {
        ArrayList<Direction> obs = new ArrayList<>();
        Rectangle playerRec = this.getBounds();
        for (TileShape obstacle : obstacles) {
            Rectangle obstacleRec = obstacle.getBounds();
            if (playerRec.intersects(obstacleRec)) {
                obs = new ArrayList<>();
                if (this.getY() + 10 > obstacle.getY() + obstacle.getHeight()) { //intersects top
                    obs.add(Direction.NORTH);
                }
                if (this.getX() - 10 < obstacle.getX() - this.getWidth()) {//intersects left
                    obs.add(Direction.WEST);
                }
                if (this.getX() + 10 > obstacle.getX() + obstacle.getWidth()) { //intersects right
                    obs.add(Direction.EAST);
                }
                if (this.getY() - 10 < obstacle.getY() - this.getHeight()) { //intersect bottom
                    obs.add(Direction.SOUTH);
                }
            }
        }
        return obs;
    }

//    /**
//     * returns a boolean whether it has collided with an obstacle.
//     * @param obstacles
//     * @return
//     */
//    public boolean checkCollisionWithoutMoving(ArrayList<graphics.TileShape> obstacles) {
//
//        Rectangle playerRec = this.getBounds();
//
//        for (graphics.TileShape obstacle : obstacles) {
//            Rectangle obstacleRec = obstacle.getBounds();
//            if (playerRec.intersects(obstacleRec)) {
//                return true;
//            }
//        }
//        return false;
//    }

    public void damageHealth() {
        System.out.println("bullet hit player");
    }

}
