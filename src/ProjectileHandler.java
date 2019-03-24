import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProjectileHandler {

    private ArrayList<Projectile> projectiles;
    Timer velocity_timer;

    // Constructor initialises array of bullets
    public ProjectileHandler() {
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

    public void move(){
        for (Projectile proj : projectiles){
            proj.move();
            if (proj.checkCollision()){
                //projectiles.remove(proj);
                //remove projectile
            }
        }
    }

    public void shoot(Direction dir, int xPos, int yPos, int tileSize){
        Projectile projectile = new Projectile(dir, xPos, yPos, tileSize);
        projectiles.add(projectile);
    }

    public void start(){
        velocity_timer.start();

    }

    public void stop(){
        velocity_timer.stop();
    }


}
