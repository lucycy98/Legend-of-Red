package model.attacks;

import model.pickup.Items;
import model.being.Enemy;

import java.awt.*;

/**
 * this class is an interface for a weapon
 */
public interface Weapon {

    public void attack();
    public void enemyRangeAttack(Enemy enemy);
    public void paint(Graphics2D g);
    public void checkCollision();
    public void checkEnemyCollision();
    public Items getItems();
    public void stopTimers();
    public void startTimers();
    public String getSoundFile();
}
