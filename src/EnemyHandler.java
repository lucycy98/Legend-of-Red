import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class EnemyHandler {

    private ArrayList<Enemy> enemies;
    int level;
    GamePanel game;
    Timer velocity_timer;


    // Constructor initialises array of bullets
    public EnemyHandler(int level, Map map, int tileSize, GamePanel parent) {
        enemies = new ArrayList<>();
        if (level == 1){

            for (int i = 0; i < 4; i++){
                int x = new Random().nextInt(32);
                int y = new Random().nextInt(24);

                enemies.add(new Enemy(x*40, y*40, "wolf.png", tileSize, map.getObstacles(), map.getPortals(), parent));
            }
        }
        this.level = level;
        this.game = parent;
    }

    public void move(){
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            enemy.move();
            if (enemy.checkCollisionWeapon()){
                System.out.println("being hit by projectile");
                enemy.setIsRenderable(false);
                enemies.remove(enemy);
            }
        }
    }


    public void paint(Graphics2D win){
        move();
        for (Enemy enemy : enemies){
            enemy.paint(win);
        }
    }

    public ArrayList<Enemy> getEnemies(){
        return enemies;
    }


}
