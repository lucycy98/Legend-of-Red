package attacks;

import being.Enemy;
import pickup.Items;

import java.awt.*;

public interface Weapon {

    public void attack();
    public void enemyRangeAttack(Enemy enemy);
    public void paint(Graphics2D g);
    public void checkCollision();
    public void checkEnemyCollision();
    public Items getItems();
    public void stopTimers();
    public void startTimers();
}
