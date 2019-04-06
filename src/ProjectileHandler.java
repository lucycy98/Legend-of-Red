import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class ProjectileHandler {

    private ArrayList<Projectile> projectiles;
    Timer velocity_timer;
    private ArrayList<TileShape> currentObs;

    // Constructor initialises array of bullets
    public ProjectileHandler(ArrayList<TileShape> obstacles) {
        this.currentObs = obstacles;
        projectiles = new ArrayList<>();
        this.velocity_timer = new Timer(1000/300, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move();
            }
        }));
    }

    public void paint(Graphics2D win){
        for (Projectile proj : projectiles){
            proj.paint(win);
        }
    }

    public ArrayList<Projectile> getProjectiles(){
        return projectiles;
    }

    public void move(){

        for (int i = 0; i < projectiles.size(); i++) {
            Projectile proj = projectiles.get(i);
            proj.move();
            if (proj.checkCollision()){
                System.out.println("coolliding!");
                proj.setIsRenderable(false);
                projectiles.remove(proj);
            }
        }

    }

    public void shoot(Direction dir, int xPos, int yPos, int tileSize){
        Projectile projectile = new Projectile(dir, xPos, yPos, tileSize, currentObs);
        projectiles.add(projectile);
    }

    public void start(){
        velocity_timer.start();

    }

    public void stop(){
        velocity_timer.stop();
    }


}
