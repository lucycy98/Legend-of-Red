import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class EnemyHandler {

    private ProjectileHandler projectiles;
    private HashMap<Integer, ArrayList<Enemy>> enemies;
    private ArrayList<Enemy> currentEnemies;
    private int tileSize;
    private MapHandler maps;
    protected Protagonist player;
    private PickUpItemHandler item;


    public EnemyHandler(int tileSize, MapHandler maps, ProjectileHandler ph) {
        item = new PickUpItemHandler(maps);
        enemies = new HashMap<>();
        this.maps = maps;
        this.projectiles = ph;
        this.tileSize = tileSize;
        int initialLevel = 0;
        this.currentEnemies = createEnemies(initialLevel);
        enemies.put(initialLevel, currentEnemies);
    }

    public void addPlayer(Protagonist player) {
        item.player = player;
        this.player = player;
    }
//    public void addWeapon(){
//    }

    public ArrayList<Enemy> createEnemies(int level) {
        ArrayList<Enemy> enemies = new ArrayList<>();
        int[] enemiesPerLevel = {10,10,5,1};

        if (level < 3){
            for (int i = 0; i < enemiesPerLevel[level]; i++) {
                int x = ThreadLocalRandom.current().nextInt(1, 30);
                int y = ThreadLocalRandom.current().nextInt(1, 22);
                enemies.add(new Enemy(x * 40, y * 40, 40, 40, "wolf.png", tileSize, maps, projectiles, level, false));
            }
        } else { //boss level
            int x = ThreadLocalRandom.current().nextInt(1, 30);
            int y = ThreadLocalRandom.current().nextInt(1, 22);
            enemies.add(new Enemy(x * 40, y * 40, 120, 120, "wolf.png", tileSize * 2, maps, projectiles, level, true));
        }
        System.out.println("level created " + level);
        item.addNumberOfEnemies(level, enemiesPerLevel[level]);
        return enemies;
    }

    public void setNextLevel() {
        currentEnemies = createEnemies(maps.getCurrentLevel());
        enemies.put(maps.getCurrentLevel(), currentEnemies);
    }

    public void move() {
        for (int i = 0; i < currentEnemies.size(); i++) {
            Enemy enemy = currentEnemies.get(i);
            if (!player.checkEnemy(enemy)) {
                if (enemy.getLevel() == 0) {
                    enemy.move();
                } else if (enemy.getLevel() == 1) {
                    enemy.randomMovement();
                } else {
                    enemy.losTracking(player);
                }
            }
        }
    }

//    public void attack(){
//        for (int i = 0; i < currentEnemies.size(); i++) {
//            Enemy enemy = currentEnemies.get(i);
//            if (enemy.canRangeAttack) {
//
//            }
//        }
//    }

    public void paint(Graphics2D win) {
        move();
        for (Enemy enemy : currentEnemies) {
            enemy.paint(win);
        }
        item.paint(win);
    }

    public void damageEnemy(Enemy enemy) {
        enemy.damageHealth();
        if (!enemy.getIsAlive()) {
            System.out.println("damage enemy");
            item.addEnemiesKilled(enemy.getLevel(), enemy.getX(), enemy.getY());
            currentEnemies.remove(enemy); //todo update the hashmap
        }
    }

    public ArrayList<Enemy> getCurrentEnemies() {
        return currentEnemies;
    }

    public ArrayList<Enemy> getEnemies(int level) {
        return enemies.get(level);
    }

}
