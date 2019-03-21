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
        }
    }

    public void shoot(int xVel, int yVel, int xPos, int yPos){
        Projectile projectile = new Projectile(xVel, yVel, xPos, yPos);
        projectiles.add(projectile);
    }

    public void start(){
        velocity_timer.start();

    }

    public void stop(){
        velocity_timer.stop();
    }


}
