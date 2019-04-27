package being;

import attacks.WeaponHandler;
import game.Direction;
import score.Score;
import game.Timers;
import maps.MapHandler;
import pickup.PickUpItemHandler;
import graphics.TileShape;
import sound.SoundHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/***
 * this class creates the enemies and pprovides the logic for enemies.
 * It deals with wolf movement and health of wolves as a collective.
 */

public class EnemyHandler implements Timers {

    private HashMap<Integer, ArrayList<Enemy>> enemies;
    private ArrayList<Enemy> currentEnemies;
    private int tileSize;
    private MapHandler maps;
    private Protagonist player;
    private PickUpItemHandler item;
    private int totallevels;
    private Score score;
    private Timer velocity_timer;
    private Timer los_timer;
    private SoundHandler sound;
    private int difficulty;


    public EnemyHandler(int tileSize, MapHandler maps, Score score, PickUpItemHandler item, SoundHandler sound, int difficulty) {
        this.sound = sound;
        this.item = item;
        enemies = new HashMap<>();
        this.maps = maps;
        totallevels = maps.getTotalLevels();
        this.tileSize = tileSize;
        this.score = score;
        score.totalLevels(totallevels);
        this.difficulty = difficulty;

        //creating enemies
        for (int i = 0; i < totallevels; i++) {
            int level = i;
            ArrayList<Enemy> enemy = createEnemies(level);
            enemies.put(level, enemy);
        }
        this.currentEnemies = enemies.get(maps.getCurrentLevel());

        this.velocity_timer = new Timer(1000 / 40, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                basicMove();
            }
        }));
        velocity_timer.start();

        this.los_timer = new Timer(1000 / 100, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveLos();
            }
        }));
        los_timer.start();
    }

    public ArrayList<Enemy> createEnemies(int level) {
        ArrayList<Enemy> enemies = new ArrayList<>();
        if (level == 0) {
            return enemies;
        }
        int[] enemiesPerLevel = {0, 6, 6, 5, 1};

        if (level < totallevels - 1) {
            for (int i = 0; i < enemiesPerLevel[level]; i++) {
                int x = ThreadLocalRandom.current().nextInt(1, maps.getxTiles() - 1);
                int y = ThreadLocalRandom.current().nextInt(1, maps.getyTiles() - 1);
                boolean overlap = false;
                for (TileShape obs : maps.getMap(level).getObstacles()) {
                    if (obs.getX() / tileSize == x && obs.getY() / tileSize == y) {
                        overlap = true;
                    }
                }
                if (!overlap) {
                    enemies.add(new Enemy(x * tileSize, y * tileSize, tileSize, tileSize, "wolf.png", maps, level, false, 1, difficulty));
                } else {
                    i--;
                }
            }
        } else { //boss level
            enemies.add(new Enemy(10 * tileSize, 5 * tileSize, tileSize * 2, tileSize * 2, "wolf.png", maps, level, true, 5, difficulty));
        }
        item.addNumberOfEnemies(level, enemiesPerLevel[level]);
        return enemies;
    }


    ///////////////////////////////////////// ENEMY EVENTS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    public void moveLos() { //dealing with tracking
        if (player == null) {
            return;
        }
        for (int i = 0; i < currentEnemies.size(); i++) {
            Enemy enemy = currentEnemies.get(i);
            if (enemy.isFriendly()) {
                moveFriendly(enemy);
            } else if (maps.getCurrentLevel() <= 2 || player.enemyIsAttacking(enemy)) {
                continue;
            } else if (enemy.isMovingBack()) {
                enemy.moveEnemyBack();
            } else if (player.isInvincible()) {
                enemy.randomMovement();
            } else {
                enemy.losTracking(player.getX(), player.getY(), false);
            }
        }
    }

    public void basicMove() { //dealing with levels 1,2
        if (player == null || maps.getCurrentLevel() > 2) {
            return;
        }
        for (int i = 0; i < currentEnemies.size(); i++) {
            Enemy enemy = currentEnemies.get(i);
            if (enemy.isFriendly() || player.enemyIsAttacking(enemy)) {
                continue;
            } else if (enemy.isMovingBack()) {
                enemy.moveEnemyBack();
            } else if (player.isInvincible()) {
                enemy.randomMovement();
            } else {
                switch (enemy.getLevel()) {
                    case 1:
                        enemy.move();
                        break;
                    case 2:
                        enemy.randomMovement();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public Enemy findClosestEnemy(Being subject, ArrayList<Enemy> enemies) {
        double shortestDistance = 999;
        Enemy closestEnemy = enemies.get(0);
        for (Enemy enemy : enemies) {
            if (enemy.equals(subject))
                continue;
            double dist = Math.abs(subject.getDistance(enemy));
            shortestDistance = (dist < shortestDistance & dist != 0) ? dist : shortestDistance;
            closestEnemy = enemy;
        }
        return closestEnemy;
    }

    private void moveFriendly(Enemy enemy) {
        Enemy closest = findClosestEnemy(enemy, currentEnemies);
        enemy.losTracking(closest.getX(), closest.getY(), true);
        if (enemy.getBounds().intersects(closest.getBounds())) {
            damageEnemy(closest, null);
            if (!closest.getIsAlive()) {
                enemy.incrementKilledWolf();
            }
        }
    }

    public void damageEnemy(Enemy enemy, Direction dir) {
        enemy.damageHealth();
        sound.play("damageEnemy.wav");
        if (!enemy.getIsAlive()) {
            score.killWolf(maps.getCurrentLevel());
            item.addEnemiesKilled(enemy.getLevel(), enemy.getX(), enemy.getY());
            currentEnemies.remove(enemy);
        } else { //not kill just damage
            score.damageWolf(maps.getCurrentLevel());
            if (dir != null) {
                enemy.setMovingBack(dir); //move enemy back.

            }
        }
    }

    //////////////////////////////////// HELPER \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    public void setEnemy() {
        currentEnemies = enemies.get(maps.getCurrentLevel());
    }

    public ArrayList<Enemy> getCurrentEnemies() {
        return currentEnemies;
    }

    public void addPlayer(Protagonist player) {
        item.addPlayer(player);
        this.player = player;
    }

    public void addWeaponHandler(WeaponHandler weapon) {
        item.addWeaponHandler(weapon);
    }


    public void stopTimers() {
        velocity_timer.stop();
        item.stopTimers();
        los_timer.stop();
    }

    public void startTimers() {
        velocity_timer.start();
        los_timer.start();

        item.startTimers();
    }

    /////////////////////// GRAPHICS \\\\\\\\\\\\\\\\\\\\\\\\\\\
    public void paint(Graphics2D win) {
        for (Enemy enemy : currentEnemies) {
            enemy.paint(win);
        }
        item.paint(win);
    }

}
