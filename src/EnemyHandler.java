import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class EnemyHandler implements Timers{

    private HashMap<Integer, ArrayList<Enemy>> enemies;
    private ArrayList<Enemy> currentEnemies;
    private int tileSize;
    private MapHandler maps;
    protected Protagonist player;
    private PickUpItemHandler item;
    int totallevels = 4;
    private Score score;
    private Timer velocity_timer;


    public EnemyHandler(int tileSize, MapHandler maps, Score score) {
        item = new PickUpItemHandler(maps);
        enemies = new HashMap<>();
        this.maps = maps;
        this.tileSize = tileSize;
        this.score = score;
        score.totalLevels(totallevels);

        for (int i = 0; i < totallevels; i++) {
            ArrayList<Enemy> enemy = createEnemies(i);
            enemies.put(i, enemy);
        }
        this.currentEnemies = enemies.get(maps.getCurrentLevel());

        this.velocity_timer = new Timer(1000 / 300, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move();
            }
        }));
        velocity_timer.start();
    }

    public void stopTimers(){
        velocity_timer.stop();
    }

    public void startTimers(){
        velocity_timer.start();
    }

    public void addPlayer(Protagonist player) {
        item.player = player;
        this.player = player;
    }

    public void addWeaponHandler(WeaponHandler weapon) {
        item.addWeaponHandler(weapon);
    }

    public ArrayList<Enemy> createEnemies(int level) {
        ArrayList<Enemy> enemies = new ArrayList<>();
        int[] enemiesPerLevel = {6, 6, 5, 1};

        if (level < 3) {
            for (int i = 0; i < enemiesPerLevel[level]; i++) {
                int x = ThreadLocalRandom.current().nextInt(1, maps.getxTiles() - 1);
                int y = ThreadLocalRandom.current().nextInt(1, maps.getyTiles() - 1);
                boolean overlap = false;
                for (TileShape obs : maps.maps.get(level).getObstacles()) {
                    if (obs.getX() / tileSize == x && obs.getY() / tileSize == y) {
                        overlap = true;
                    }
                }
                if (!overlap) {
                    enemies.add(new Enemy(x * tileSize, y * tileSize, tileSize, tileSize, "wolf.png", tileSize, maps, level, true));
                } else {
                    i--;
                }
            }
        } else { //boss level
            int x = ThreadLocalRandom.current().nextInt(1, maps.getxTiles() - 1);
            int y = ThreadLocalRandom.current().nextInt(1, maps.getyTiles() - 1);
            enemies.add(new Enemy(x * tileSize, y * tileSize, tileSize * 2, tileSize * 2, "wolf.png", tileSize * 2, maps, level, true));
        }
        System.out.println("level created " + level);
        System.out.println("enemise in level" + level + enemiesPerLevel[level]);
        item.addNumberOfEnemies(level, enemiesPerLevel[level]);
        return enemies;
    }

    public void setEnemy() {
        currentEnemies = enemies.get(maps.getCurrentLevel());
    }

    public Enemy findClosestEnemy(Being subject, ArrayList<Enemy> enemies) {
        double shortestDistance = 999;
        Enemy closestEnemy = enemies.get(0);
        for (Enemy enemy : enemies) {
            double dist = Math.abs(subject.getDistance(enemy));
            shortestDistance = (dist < shortestDistance & dist != 0) ? dist : shortestDistance;
            closestEnemy = enemy;
        }
        return closestEnemy;
    }

    public void move() {
        if (player == null){
            return;
        }
        for (int i = 0; i < currentEnemies.size(); i++) {
            Enemy enemy = currentEnemies.get(i);
            if (!player.checkEnemy(enemy)) {

                if (enemy.friendly) {
                    Enemy closest = findClosestEnemy(enemy, currentEnemies);
                    enemy.losTracking(closest.getX(), closest.getY());
                    if (enemy.getBounds().intersects(closest.getBounds())) {
                        enemy.damageHealth();
                        closest.damageHealth();
                    }
                } else {
                    if (enemy.getLevel() == 0) {
                        enemy.move();
                    } else if (enemy.getLevel() == 1) {
                        enemy.randomMovement();
                    } else {
                        enemy.losTracking(player.getX(), player.getY());
                    }
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
        for (Enemy enemy : currentEnemies) {
            enemy.paint(win);
        }
        item.paint(win);
    }

    public void damageEnemy(Enemy enemy) {
        enemy.damageHealth();
        if (!enemy.getIsAlive()) {
            score.killWolf(maps.getCurrentLevel());
            item.addEnemiesKilled(enemy.getLevel(), enemy.getX(), enemy.getY());
            currentEnemies.remove(enemy); //todo update the hashmap
        } else { //not kill just damage
            score.damageWolf(maps.getCurrentLevel());

        }
    }

    public ArrayList<Enemy> getCurrentEnemies() {
        return currentEnemies;
    }

    public ArrayList<Enemy> getEnemies(int level) {
        return enemies.get(level);
    }

}
