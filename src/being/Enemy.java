package being;

import game.Direction;
import graphics.TileShape;
import maps.MapHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

/**
 * this class represents an enemy, or a wolf object.
 * it's behaviours are dependent on its level assigned, as well as difficulty.
 */
public class Enemy extends Being {

    // co-ordinates of player
    private int dx, dy;
    private Boolean isAlive = true;
    private MapHandler maps;
    private int timeLeft;
    private int level;
    private boolean canRangeAttack;
    private boolean friendly;
    private boolean attackStatus;
    private int health;
    private int maxCanKill = 3;
    private int currentKilled;
    private boolean movingBack;
    private Direction movingBackDirection;
    private Boolean beingAttacked;
    private int difficulty;

    public Enemy(int xPos, int yPos, int width, int height, String image, MapHandler maps, int level, boolean canRangeAttack, int health, int difficulty) {
        super(xPos, yPos, width, height,  image);
        this.maps = maps;
        this.difficulty = difficulty;
        currentKilled = 0;
        beingAttacked = false;
        movingBack = false;
        timeLeft = 200;
        this.level = level;
        this.canRangeAttack = canRangeAttack;
        friendly = false;
        attackStatus = false;

        assignSpeed();

        this.health = health * difficulty;
    }

    private void assignSpeed() {
        switch (level) {
            case 1:
                dy = 0;
                dx = 0;
                while (dy == 0) {
                    if (difficulty == 3) {
                        dy = ThreadLocalRandom.current().nextInt(-2, 2);
                    } else {
                        dy = ThreadLocalRandom.current().nextInt(-1, 1);
                    }
                }
                while (dx == 0) {
                    if (difficulty == 3) {
                        dx = ThreadLocalRandom.current().nextInt(-2, 2);
                    } else {
                        dx = ThreadLocalRandom.current().nextInt(-1, 1);
                    }
                }
                break;
            case 2:
                dy = ThreadLocalRandom.current().nextInt(-2, 2);
                dx = ThreadLocalRandom.current().nextInt(-2, 2);
                break;
            default:
                dy = 2;
                dx = 2;
                break;
        }
    }

    ///////////////////////// DAMAGE HEALTH \\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    private void killWolf() {
        this.setIsRenderable(false);
        isAlive = false;
    }

    @Override
    public void damageHealth() {
        health--;
        if (health == 0) {
            killWolf();
        }
        flash();
    }

    private void flash() {
        changeImage("redWolf.png");
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Your database code here
                changeImage("wolf.png");

            }
        }, 200);
    }

    public Boolean isMovingBack() {
        return movingBack;
    }

    public Boolean isBeingAttacked() {
        return beingAttacked;
    }

    public void setbeingAttacked(Boolean bool) {
        beingAttacked = bool;
    }

    public void setMovingBack(Direction dir) {
        movingBack = true;
        movingBackDirection = dir;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Your database code here
                movingBack = false;
            }
        }, 800);
    }

    ////////// FRIENDLY WOLF \\\\\\\\\\\\\\\\
    /**
     * friendly wolves can only kill a maximum number of other wolves before they die.
     */
    public void incrementKilledWolf() {
        if (currentKilled + 1 > maxCanKill) {
            friendly = false;
            changeImage("wolf.png");
        } else {
            currentKilled = currentKilled + 1;
        }
    }

    public void becomeFriendly() {
        friendly = true;
        changeImage("friendlyWolf.png");
    }

    public boolean isFriendly() {
        return friendly;
    }

    /////////////////// MOVEMENT \\\\\\\\\\\\\\\\\\\\\\\\\\\

    /**
     * when enemy is attacked by player, they move backwards depending on the last direction of the dagger.
     */
    public void moveEnemyBack() {
        int currentX = getX();
        int currentY = getY();

        switch (movingBackDirection) {
            case NORTH:
                if (isValidMove(maps.getCurrentObstacles(), currentX, currentY - 2)) {
                    setY(currentY - 2);
                }
                break;
            case EAST:
                if (isValidMove(maps.getCurrentObstacles(), currentX + 2, currentY)) {
                    setX(currentX + 2);
                }
                break;
            case WEST:
                if (isValidMove(maps.getCurrentObstacles(), currentX - 2, currentY)) {
                    setX(currentX - 2);
                }
                break;
            case SOUTH:
                if (isValidMove(maps.getCurrentObstacles(), currentX, currentY + 2)) {
                    setY(currentY + 2);
                }
                break;
        }
    }

    /**
     * this method takes the most updated dx, and dy (velocity) and checks if
     * is a valid move (i.e no obstacles). if not, then it proceeds to find another
     * direction to move in.
     */
    public void move() {
        int currentX = getX();
        int currentY = getY();

        int finalX;
        int finalY;
        ArrayList<TileShape> obs = maps.getCurrentObstacles();

        if (isValidMove(obs, currentX + dx, currentY + dy)) { //NE
            finalX = currentX + dx;
            finalY = currentY + dy;
        } else if (isValidMove(obs, currentX - dx, currentY + dy)) { //NW
            finalX = currentX - dx;
            finalY = currentY + dy;
            dx = dx * -1;
        } else if (isValidMove(obs, currentX + dx, currentY - dy)) { //SE
            finalX = currentX + dx;
            finalY = currentY - dy;
            dy = dy * -1;
        } else if (isValidMove(obs, currentX - dx, currentY - dy)) { //SW
            finalX = currentX - dx;
            finalY = currentY - dy;
            dy = dy * -1;
            dx = dx * -1;
        } else if (isValidMove(obs, currentX, currentY - dy)) { //S
            finalX = currentX;
            finalY = currentY - dy;
            dy = dy * -1;
        } else if (isValidMove(obs, currentX, currentY + dy)) { //N
            finalX = currentX;
            finalY = currentY + dy;
        } else if (isValidMove(obs, currentX + dx, currentY)) { //E
            finalX = currentX + dx;
            finalY = currentY;
        } else if (isValidMove(obs, currentX - dx, currentY)) { //W
            finalX = currentX - dx;
            finalY = currentY;
            dx = dx * -1;
        } else {
            return;
        }
        setX(finalX);
        setY(finalY);
    }

    /**
     * this movement changes direction / speed after a certain amount of time.
     */
    public void randomMovement() {
        if (timeLeft > 0) {
            timeLeft--;
        } else {
            int randX;
            int randY;
            switch (difficulty) {
                case 1:
                    randX = ThreadLocalRandom.current().nextInt(-2, 2);
                    randY = ThreadLocalRandom.current().nextInt(-2, 2);
                    break;
                default:
                    randX = ThreadLocalRandom.current().nextInt(-3, 3);
                    randY = ThreadLocalRandom.current().nextInt(-3, 3);
                    break;
            }
            dx = randX;
            dy = randY;
            timeLeft = 200;
        }
        move();
    }

    //line of sight tracking movement
    public void losTracking(int playerX, int playerY, Boolean friendly) {

        int currentX = getX();
        int currentY = getY();

        int distX = playerX - currentX;
        int distY = playerY - currentY;

        int scale = Math.max(Math.abs(distX), Math.abs(distY));

        if (scale != 0) {
            dx = (distX / scale);
            dy = (distY / scale);
        } else {
            dx = 0;
            dy = 0;
        }

        if (friendly){
            setX(currentX + dx);
            setY(currentY + dy);
        } else {
            move();
        }
    }

    /////////// other getters and setters \\\\\\\\\\\\

    public int getLevel() {
        return level;
    }

    public Boolean getIsAlive() {
        return isAlive;
    }

    public Boolean getcanRangeAttack() {
        return canRangeAttack;
    }

    public boolean isAttacking() {
        return attackStatus;
    }

    public void setAttackStatus(Boolean bool) {
        attackStatus = bool;
    }

    /////// GRAPHICS \\\\\\\\\\\\\
    public void paint(Graphics2D win) {
        renderShape(win);
    }
}