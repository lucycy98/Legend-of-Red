import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
public class ProjectileHandler implements Weapon {

    private final MapHandler maps;
    private ArrayList<Projectile> projectiles;
    private Protagonist player;
    Timer velocity_timer;
    private int projectileSpeed = 4;
    private EnemyHandler enemies;

    // Constructor initialises array of bullets
    public ProjectileHandler(MapHandler maps, Protagonist player, EnemyHandler enemies) {
        this.maps = maps;
        this.player = player;
        projectiles = new ArrayList<>();
        this.enemies = enemies;
        this.velocity_timer = new Timer(1000/300, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move();
            }
        }));
        velocity_timer.start();
    }

    public void move(){
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile proj = projectiles.get(i);
            proj.move();
            if (proj.checkCollision()){
                proj.setIsRenderable(false);
                projectiles.remove(proj);
            }
        }
    }

    @Override
    public void attack() {
        Direction dir = player.getDir();
        int xPos = player.getX();
        int yPos = player.getY();
        Projectile projectile = new Projectile(dir, xPos, yPos, projectileSpeed, maps);
        projectiles.add(projectile);
    }

//    public void bossAttack() {
//        Enemy boss = enemies.getCurrentEnemies().get(0);
//        int playerX = player.getX();
//        int playerY = player.getY();
//
//        int bossX = boss.getX();
//        int bossY = boss.getY();
////        Projectile projectile = new Projectile(dir, xPos, yPos, projectileSpeed, maps);
////        projectiles.add(projectile);
//    }

    @Override
    public void paint(Graphics2D g) {
        for (Projectile proj : projectiles){
            proj.paint(g);
        }
    }

    @Override
    public void checkCollision() {
        for (Projectile projectile: projectiles){
            Rectangle projRec = projectile.getBounds();
            ArrayList<Enemy> es = enemies.getCurrentEnemies();
            for (int i = 0; i < es.size(); i++){

                Rectangle enemyRec = es.get(i).getBounds();
                if (projRec.intersects(enemyRec)) {
                    enemies.damageEnemy(es.get(i));
                }
            }
        }
    }
}
