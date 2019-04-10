import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WeaponHandler {

    private final MapHandler maps;
    private ProjectileHandler projectiles;
    private Weapon currentWeapon;
    private Weapon otherWeapon;
    Timer velocity_timer;

    // Constructor initialises array of bullets
    public WeaponHandler(MapHandler maps, ProjectileHandler projectiles, Protagonist player, EnemyHandler enemies) {
        this.maps = maps;
        this.projectiles = projectiles;
        currentWeapon = new Dagger(player, 40, 40, "dagger.jpg", false, enemies);
        otherWeapon = new ProjectileHandler(maps, player, enemies);

        this.velocity_timer = new Timer(1000/300, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkCollision();
            }
        }));
        velocity_timer.start();
    }

    public void paint(Graphics2D win){
        currentWeapon.paint(win);
    }

    public void attack(){
        currentWeapon.attack();
    }

    public void checkCollision(){
        currentWeapon.checkCollision();
    }

    public void changeWeapon(){
        Weapon temp = currentWeapon;
        currentWeapon = otherWeapon;
        otherWeapon = temp;
    }

}
