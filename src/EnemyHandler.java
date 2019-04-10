import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class EnemyHandler {

    private ProjectileHandler projectiles;
    private HashMap<Integer, ArrayList<Enemy>> enemies;
    private ArrayList<Enemy> currentEnemies;
    private int tileSize;
    private MapHandler maps;
    private Dagger dagger;

    public EnemyHandler(int tileSize, MapHandler maps, ProjectileHandler ph) {
        enemies = new HashMap<>();
        this.maps = maps;
        this.projectiles = ph;
        this.tileSize = tileSize;
        int initialLevel = 1;
        this.currentEnemies = createEnemies(initialLevel);
        enemies.put(initialLevel, currentEnemies);
    }

    public ArrayList<Enemy> createEnemies(int level){
        ArrayList<Enemy> enemies = new ArrayList<>();
        if (level == 1){
            for (int i = 0; i < 4; i++){
                int x = new Random().nextInt(32);
                int y = new Random().nextInt(24);
                enemies.add(new Enemy(x*40, y*40, "wolf.png", tileSize, maps, projectiles));
            }
        } else if (level == 2){
            for (int i = 0; i < 5; i++) {
                int x = new Random().nextInt(32);
                int y = new Random().nextInt(24);
                enemies.add(new Enemy(x * 40, y * 40, "wolf.png", tileSize*2, maps, projectiles));
            }
        }
        return enemies;
    }

    public void setNextLevel(){
        currentEnemies = createEnemies(maps.getCurrentLevel());
        enemies.put(maps.getCurrentLevel(), currentEnemies);
    }

    public void move(){
        for (int i = 0; i < currentEnemies.size(); i++) {
            Enemy enemy = currentEnemies.get(i);
            enemy.move();
        }
    }

    public void paint(Graphics2D win){
        move();
        for (Enemy enemy : currentEnemies){
            enemy.paint(win);
        }
    }

    public void damageEnemy(Enemy enemy){
        enemy.damageHealth();
        if(!enemy.getIsAlive()){
            //dead
            currentEnemies.remove(enemy); //todo update the hashmap!!!
        }

    }

    public ArrayList<Enemy> getCurrentEnemies(){
        return currentEnemies;
    }

    public ArrayList<Enemy> getEnemies(int level){
        return enemies.get(level);
    }

}
