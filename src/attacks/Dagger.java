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
    private Boolean isAttacking;


    public Dagger(Protagonist player, int x, int y, String img, Boolean r, EnemyHandler enemies) {
        super(x, y, 40, 40, img, false);
        this.player = player;
        isAttacking = false;
        playerDim = player.getHeight();
        this.enemies = enemies;
        this.velocity_timer = new javax.swing.Timer(1000 / 300, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDagger();
            }
        }));
        velocity_timer.start();
    }

    public Rectangle getBounds() {
        return new Rectangle(currentXPos, currentYPos, 40, 40);
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
    }

    @Override
    public void checkEnemyCollision(){

    }

    @Override
    public void paint(Graphics2D win) {
        this.renderShape(win);
        showDagger();
    }

    @Override
    public void checkCollision() {
        if (this.isRenderable()) {
            Rectangle daggerRec = this.getBounds();
            ArrayList<Enemy> es = enemies.getCurrentEnemies();
            for (int i = 0; i < es.size(); i++) {
                Enemy enemy = es.get(i);
                Rectangle enemyRec = es.get(i).getBounds();
                if (daggerRec.intersects(enemyRec)) {
                    if (!enemy.isBeingAttacked()){
                        System.out.println("damaging");
                        enemies.damageEnemy(enemy, lastWeaponDir);
                        enemy.setbeingAttacked(true);
                    } else {
                        System.out.println("           not damaing!!!!!!!!!  ");
                    }
                } else {
                    enemy.setbeingAttacked(false);
                }
            }
        }
    }

    public void checkCollisionOLD() {
        if (this.isRenderable()) {
            Rectangle daggerRec = this.getBounds();
            ArrayList<Enemy> es = enemies.getCurrentEnemies();
            for (int i = 0; i < es.size(); i++) {
                Rectangle enemyRec = es.get(i).getBounds();
                if (daggerRec.intersects(enemyRec)) {
                    enemies.damageEnemy(es.get(i), lastWeaponDir);
                }
            }
        }
    }




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
                this.changeImage("daggerSouth.png");
                currentXPos = xPos + 10;
                currentYPos = yPos + playerDim - 10;
                break;
            case SOUTH_EAST:
                lastWeaponDir = Direction.SOUTH;
                this.changeImage("daggerSouth.png");
                currentXPos = xPos + 10;
                currentYPos = yPos + playerDim - 10;
                break;
            case SOUTH_WEST:
                lastWeaponDir = Direction.SOUTH;
                this.changeImage("daggerSouth.png");
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
                lastWeaponDir = Direction.EAST;
                this.changeImage("daggerWest.png");
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

}