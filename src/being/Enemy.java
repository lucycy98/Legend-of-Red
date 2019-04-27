package being;

import being.Being;
import game.Direction;
import graphics.TileShape;
import maps.MapHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

/**
 * keep incrementing x, y until key release OR another arrow is pressed.
 */
public class Enemy extends Being {

    // co-ordinates of player
    private int dx, dy;
    private Direction currentDirection = Direction.NORTH_EAST;
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

    public Enemy(int xPos, int yPos, int width, int height, String image, int tile, MapHandler maps, int level, boolean canRangeAttack, int health, int difficulty) {
        super(xPos, yPos, width, height, 1, image);
        this.maps = maps;
        this.difficulty = difficulty;
        assignSpeed();
        currentKilled = 0;
        beingAttacked = false;
        movingBack = false;
        timeLeft = 200;
        this.level = level;
        this.canRangeAttack = canRangeAttack;
        friendly = false;
        attackStatus = false;
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
                dy = ThreadLocalRandom.current().nextInt(-1, 1);
                dx = ThreadLocalRandom.current().nextInt(-1, 1);
                break;
            default:
                dy = 2;
                dx = 2;
                break;
        }
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
        System.out.println(dir);
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

    public void incrementKilledWolf() {
        if (currentKilled + 1 > maxCanKill) {
            friendly = false;
            changeImage("wolf.png");
        } else {
            currentKilled = currentKilled + 1;
        }
    }

    public int getLevel() {
        return level;
    }

    public Direction getDir() {
        return currentDirection;
    }

    public void paint(Graphics2D win) {
        renderShape(win);
    }

    @Override
    public void damageHealth() {
        health--;
        if (health == 0) {
            killWolf();
        }
        flash();
    }

    public void flash() {
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

    public void killWolf() {
        this.setIsRenderable(false);
        isAlive = false;
    }

    public Boolean getIsAlive() {
        return isAlive;
    }

    public void becomeFriendly() {
        friendly = true;
        changeImage("friendlyWolf.png");
    }

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

    public void moveEnemyBackOld() {
        int currentX = getX();
        int currentY = getY();
        ArrayList<Direction> collidingobs = checkCollisionDirection(maps.getCurrentObstacles());
        System.out.println(dx);

        switch (movingBackDirection) {
            case NORTH:
                if (!collidingobs.contains(Direction.NORTH)) {
                    setY(currentY - dy);
                }
                break;
            case EAST:
                if (!collidingobs.contains(Direction.EAST)) {
                    setX(currentX + dx);
                }
                break;
            case WEST:
                if (!collidingobs.contains(Direction.WEST)) {
                    setX(currentX - dx);
                }
                break;
            case SOUTH:
                if (!collidingobs.contains(Direction.SOUTH)) {
                    setY(currentY + dy);
                }
                break;
        }
        checkCollision(maps.getCurrentObstacles());
    }

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

    public void randomMovement() {
        if (timeLeft > 0) {
            timeLeft--;
        } else {
            int randX;
            int randY;
            switch (difficulty) {
                case 1:
                    randX = ThreadLocalRandom.current().nextInt(-2, 2);
                    randY = ThreadLocalRandom.current().nextInt(-1, 1);
                    break;
                case 2:
                    randX = ThreadLocalRandom.current().nextInt(-3, 3);
                    randY = ThreadLocalRandom.current().nextInt(-2, 2);
                    break;
                default:
                    randX = ThreadLocalRandom.current().nextInt(-3, 3);
                    randY = ThreadLocalRandom.current().nextInt(-2, 2);
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

    public Boolean getcanRangeAttack() {
        return canRangeAttack;
    }

    public boolean isFriendly() {
        return friendly;
    }

    public boolean isAttacking() {
        return attackStatus;
    }

    public void setAttackStatus(Boolean bool) {
        attackStatus = bool;
    }
}