import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProjectileHandler implements Weapon, Timers {

    private final MapHandler maps;
    private ArrayList<Projectile> projectiles;
    private ArrayList<Projectile> enemyProjectiles;
    private Protagonist player;
    Timer velocity_timer;
    private int projectileSpeed = 4;
    private EnemyHandler enemies;
    private Items item;

    // Constructor initialises array of bullets
    public ProjectileHandler(MapHandler maps, Protagonist player, EnemyHandler enemies, Items item) {
        this.item = item;
        this.maps = maps;
        this.player = player;
        projectiles = new ArrayList<>();
        enemyProjectiles = new ArrayList<>();
        this.enemies = enemies;
        this.velocity_timer = new Timer(1000 / 300, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move();
            }
        }));
        velocity_timer.start();
    }

    @Override
    public Items getItems() {
        return item;
    }

    public void move() {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile proj = projectiles.get(i);
            proj.move();
            if (proj.checkCollision()) {
                proj.setIsRenderable(false);
                projectiles.remove(proj);
            }
        }
        for (int i = 0; i < enemyProjectiles.size(); i++) {
            Projectile eproj = enemyProjectiles.get(i);
            eproj.emove(player);
            if (eproj.checkCollision()) {
                eproj.setIsRenderable(false);
                enemyProjectiles.remove(eproj);
            }
        }
    }

    @Override
    public void attack() {
        Direction dir = player.getDir();
        int xPos = player.getX();
        int yPos = player.getY();
        String image = "";
        int w = 0;
        int h = 0;
        switch (dir) {
            case NORTH:
                image = "arrowNorth.png";
                w = 10;
                h = 25;
                break;
            case SOUTH:
                image = "arrowSouth.png";
                w = 10;
                h = 25;
                break;
            case EAST:
                image = "arrowEast.png";
                w = 25;
                h = 10;
                break;
            case WEST:
                image = "arrowWest.png";
                w = 25;
                h = 10;
                break;
            default:
        }
        //Projectile projectile = new Projectile(dir, xPos, yPos, projectileSpeed, maps);
        if (!image.equals("")) {
            Projectile projectile = new Projectile(dir, xPos, yPos, projectileSpeed, maps, image, w, h);
            projectiles.add(projectile);
        }
    }

    @Override
    public void enemyRangeAttack(Enemy enemy) {
        int distX = player.getX() - enemy.getX();
        int distY = player.getY() - enemy.getY();
        Direction dir = null;
        int scale = Math.max(Math.abs(distX), Math.abs(distY));

        int dx;
        int dy;

        if (scale != 0) {
            dx = (distX / scale);
            dy = (distY / scale);
        } else {
            dx = 0;
            dy = 0;
        }

        if (dx == 1 & dy == 0) {
            dir = Direction.EAST;
        } else if (dx == 0 & dy == -1) {
            dir = Direction.NORTH;
        } else if (dx == -1 & dy == 0) {
            dir = Direction.WEST;
        } else if (dx == 0 & dy == 1) {
            dir = Direction.SOUTH;
        }

        String image = "circle.png";

        if (dir != null) {
            Projectile projectile = new Projectile(dir, enemy.getX(), enemy.getY(), projectileSpeed, maps, image, 5, 5);
            enemyProjectiles.add(projectile);
        }
    }

    @Override
    public void paint(Graphics2D g) {
        for (Projectile proj : projectiles) {
            proj.paint(g);
        }
        for (Projectile eproj : enemyProjectiles) {

            //todo change magical number
            if (eproj.incrementTimer() == 150){
                eproj.setIsRenderable(false);
                enemyProjectiles.remove(eproj);
                return;
            }
            else{
                eproj.paint(g);
            }
        }
    }

    @Override
    public void checkCollision() {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile projectile = projectiles.get(i);
            Rectangle projRec = projectile.getBounds();
            ArrayList<Enemy> es = enemies.getCurrentEnemies();
            for (int j = 0; j < es.size(); j++) {
                Rectangle enemyRec = es.get(j).getBounds();
                if (projRec.intersects(enemyRec)) {
                    if (item == Items.PROJECTILE) {
                        enemies.damageEnemy(es.get(j));
                    } else {
                        es.get(j).becomeFriendly();
                    }
                    projectile.setIsRenderable(false);
                    projectiles.remove(projectile);
                    return;
                }
            }
        }
    }

    @Override
    public void stopTimers(){
        velocity_timer.stop();
    }

    @Override
    public void startTimers() {
        velocity_timer.start();
    }

    @Override
    public void checkEnemyCollision() {
        Rectangle playerRec = player.getBounds();
        for (int i = 0; i < enemyProjectiles.size(); i++){
            Projectile eproj = enemyProjectiles.get(i);
            Rectangle projRec = eproj.getBounds();
            if (projRec.intersects(playerRec)) {
                player.health -= 5;
                eproj.setIsRenderable(false);
                enemyProjectiles.remove(eproj);
                return;
            }
        }
    }
//    @Override
//    public void checkCollision() {
//        for (Projectile projectile : projectiles){
//            Rectangle projRec = projectile.getBounds();
//            ArrayList<Enemy> currentEnemies = enemies.getCurrentEnemies();
//            for (Enemy enemy : currentEnemies){
//                Rectangle enemyRec = enemy.getBounds();
//                if (projRec.intersects(enemyRec)) {
//                    if (item == Items.PROJECTILE){
//                        enemies.damageEnemy(enemy);
//                    } else {
//                        enemy.becomeFriendly();
//                    }
//                    projectile.setIsRenderable(false);
//                    projectiles.remove(projectile);
//                }
//            }
//        }
//    }

}
