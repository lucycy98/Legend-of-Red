package attacks;

import being.Enemy;
import being.EnemyHandler;
import being.Protagonist;
import game.Direction;
import game.Timers;
import pickup.Items;
import graphics.TileShape;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * this class represents a dagger - a weapon for the player.
 */
public class Dagger extends TileShape implements Weapon, Timers {

    private int currentXPos;
    private int currentYPos;
    private Direction direction;
    private Protagonist player;
    private EnemyHandler enemies;
    private Items item = Items.DAGGER;
    private javax.swing.Timer velocity_timer;
    private int playerDim;
    private String weaponSound = "dagger.wav";
    private Direction lastWeaponDir;
    Rectangle daggerRec;


    public Dagger(Protagonist player, int x, int y, String img, Boolean r, EnemyHandler enemies) {
        super(x, y, 40, 40, img, false);
        this.player = player;
        playerDim = player.getHeight();
        this.enemies = enemies;
        this.velocity_timer = new javax.swing.Timer(1000 / 100, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDagger();
            }
        }));
        velocity_timer.start();
    }

    @Override
    public void attack() {
        player.canMove(false);
        this.setIsRenderable(true);
        showDagger();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Your database code here
                player.canMove(true);
                setIsRenderable(false);

            }
        }, 300);
    }

    @Override
    public void enemyRangeAttack(Enemy enemy) {
        //not required
    }

    @Override
    public void checkEnemyCollision(){
        //not required
    }

    /**
     * checks if dagger is colliding with enemy - damage enemy if so.
     */
    @Override
    public void checkCollision() {
        if (!this.isRenderable()) {
            return;
        }
        daggerRec = this.getBounds();
        ArrayList<Enemy> es = enemies.getCurrentEnemies();
        for (int i = 0; i < es.size(); i++) {
            Enemy enemy = es.get(i);
            Rectangle enemyRec = es.get(i).getBounds();

            if (daggerRec.intersects(enemyRec)) {
                if (!enemy.isBeingAttacked()) {
                    enemies.damageEnemy(enemy, lastWeaponDir);
                    enemy.setbeingAttacked(true);
                }
            } else {
                enemy.setbeingAttacked(false);
            }
        }
    }

    /**
     * this method changes the image and x y positions based on the direction/location of player
     */
    private void showDagger() {
        direction = player.getDir();
        int xPos = player.getX();
        int yPos = player.getY();
        switch (direction) {
            case NORTH:
                lastWeaponDir = Direction.NORTH;
                this.changeImage("daggerNorth.png");
                currentXPos = xPos + 10;
                currentYPos = yPos - playerDim + 20;
                break;
            case NORTH_EAST:
                lastWeaponDir = Direction.NORTH;
                this.changeImage("daggerNorth.png");
                currentXPos = xPos + 10;
                currentYPos = yPos - playerDim + 20;
                break;
            case NORTH_WEST:
                lastWeaponDir = Direction.NORTH;
                this.changeImage("daggerNorth.png");
                currentXPos = xPos + 10;
                currentYPos = yPos - playerDim + 20;
                break;
            case SOUTH:
                lastWeaponDir = Direction.SOUTH;
                this.changeImage("DaggerSouth.png");
                currentXPos = xPos + 10;
                currentYPos = yPos + playerDim - 10;
                break;
            case SOUTH_EAST:
                lastWeaponDir = Direction.SOUTH;
                this.changeImage("DaggerSouth.png");
                currentXPos = xPos + 10;
                currentYPos = yPos + playerDim - 10;
                break;
            case SOUTH_WEST:
                lastWeaponDir = Direction.SOUTH;
                this.changeImage("DaggerSouth.png");
                currentXPos = xPos + 10;
                currentYPos = yPos + playerDim - 10;
                break;
            case EAST:
                lastWeaponDir = Direction.EAST;
                this.changeImage("daggerEast.png");
                currentXPos = xPos + playerDim - 10;
                currentYPos = yPos + 20;
                break;
            case WEST:
                lastWeaponDir = Direction.WEST;
                this.changeImage("DaggerWest.png");
                currentXPos = xPos - playerDim + 30;
                currentYPos = yPos + 20;
                break;
            default:
                currentXPos = xPos + 10;
                currentYPos = yPos + playerDim - 10;
        }
        this.setX(currentXPos);
        this.setY(currentYPos);
    }

    /**
     * this method returns a rectangle of the dagger that better describes its position.
     */
    @Override
    public Rectangle getBounds() {
        int x = currentXPos;
        int y = currentYPos;
        int buffer = 10;
        switch(lastWeaponDir){
            case NORTH:
                y = y + buffer;
                x = x - buffer/2;
                break;
            case EAST:
                x = x - buffer*2;
                y = y - buffer - buffer/2;
                break;
            case WEST:
                x = x + buffer/2;
                y = y - buffer - buffer/2;
                break;
            case SOUTH:
                y = y - buffer - buffer/2;
                x = x - buffer/2;
                break;
        }
        return new Rectangle(x, y, 60, 60);
    }

    @Override
    public Items getItems() {
        return item;
    }

    @Override
    public void stopTimers() {
        velocity_timer.stop();
    }

    @Override
    public void startTimers() {
        velocity_timer.start();
    }

    @Override
    public String getSoundFile() {
        return weaponSound;
    }

    @Override
    public void paint(Graphics2D win) {
        this.renderShape(win);
        showDagger();
    }


}