package being;

import being.Being;
import game.Direction;
import maps.MapHandler;

import java.awt.*;
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
    private int rx, ry;
    private int tileSize;
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

    public Enemy(int xPos, int yPos, int width, int height, String image, int tile, MapHandler maps, int level, boolean canRangeAttack, int health) {
        super(xPos, yPos, width, height, 1, image);
        this.tileSize = tile;
        this.maps = maps;
        currentKilled = 0;
        beingAttacked = false;
        movingBack = false;
        dy = tileSize / 32;
        ry = tileSize / 32;
        dx = tileSize / 32;
        rx = tileSize / 32;
        timeLeft = 0;
        this.level = level;
        this.canRangeAttack = canRangeAttack;
        friendly = false;
        attackStatus = false;
        this.health = health;
    }

    public Boolean isMovingBack(){
        return movingBack;
    }

    public Boolean isBeingAttacked(){
        return beingAttacked;
    }

    public void setbeingAttacked(Boolean bool){
        beingAttacked = bool;
    }

    public void setMovingBack(Direction dir){
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
        }, 1000);
    }

    public void incrementKilledWolf(){
        if (currentKilled + 1 > maxCanKill){
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
        health --;
        if (health == 0){
            killWolf();
        }
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

    public void moveEnemyBack(){
        int currentX = getX();
        int currentY = getY();
        ArrayList<Direction> collidingobs = checkCollisionDirection(maps.getCurrentObstacles());
        System.out.println(dx);

        switch(movingBackDirection){
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

        if (checkCollision(maps.getCurrentObstacles())) {
            dx = dx * -1;
        }

        if (checkCollision(maps.getCurrentObstacles())) {
            dy = dy * -1;
        }
        setX(currentX + dx);
        setY(currentY + dy);
    }

    public void randomMovement() {
        int currentX = getX();
        int currentY = getY();

        int countDown = 50;
        int randX = ThreadLocalRandom.current().nextInt(-2, 2);
        int randY = ThreadLocalRandom.current().nextInt(-1, 1);

        if (timeLeft > 0) {
            //move
            setX(currentX + rx);
            setY(currentY + ry);
            timeLeft--;
        }

        if (checkCollision(maps.getCurrentObstacles()) | timeLeft == 0) {
            timeLeft = countDown;
            rx = randX;
            ry = randY;
        }

    }

    //line of sight tracking movement
    public void losTracking(int playerX, int playerY) {
        int currentX = getX();
        int currentY = getY();

        int distX = playerX - currentX;
        int distY = playerY - currentY;

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

        if (!checkCollision(maps.getCurrentObstacles())) {
            setX(currentX + dx);
            setY(currentY + dy);
        }
    }

    public Boolean getcanRangeAttack(){
        return canRangeAttack;
    }

    public boolean isFriendly(){
        return friendly;
    }

    public boolean isAttacking(){
        return attackStatus;
    }

    public void setAttackStatus(Boolean bool){
        attackStatus = bool;
    }
}