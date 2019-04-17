import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WeaponHandler {

    private final MapHandler maps;
    private Weapon currentWeapon;
    private Weapon otherWeapon;
    private Weapon bossWeapon;
    private ArrayList<Weapon> availableWeapons;
    Timer velocity_timer;
    EnemyHandler enemies;
    Protagonist player;
    //private Weapon current;

    // Constructor initialises array of bullets
    public WeaponHandler(MapHandler maps, ProjectileHandler projectiles, Protagonist player, EnemyHandler enemies) {
        this.maps = maps;
        availableWeapons = new ArrayList<>();
        Weapon dagger = new Dagger(player, 40, 40, "dagger.jpg", false, enemies);
        availableWeapons.add(dagger);
        currentWeapon = dagger;
        //otherWeapon = new ProjectileHandler(maps, player, enemies);
        bossWeapon = new ProjectileHandler(maps, player, enemies, Items.PROJECTILE);
        this.enemies = enemies;
        this.player = player;

        this.velocity_timer = new Timer(1000/300, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkCollision();
            }
        }));
        velocity_timer.start();
    }

    public void addWeapon(Items item){
        Weapon weapon;
        switch(item){
            case PROJECTILE:
                weapon = new ProjectileHandler(maps, player, enemies, Items.PROJECTILE);
                break;
            case CUPIDBOW:
                weapon = new ProjectileHandler(maps, player, enemies, Items.CUPIDBOW);
                break;
                default:
                    weapon = null;
        }
        if (weapon != null){
            availableWeapons.add(weapon);
        }
    }

    public void removeWeapon(Items items){
        for (int i = 0; i < availableWeapons.size(); i++){
            Weapon weapon = availableWeapons.get(i);
            Items currentItem = weapon.getItems();
            if (currentItem == items){
                availableWeapons.remove(i);
                weapon = null; //get rid of it
            }
        }
    }

    public void paint(Graphics2D win){
        enemyAttack();
        currentWeapon.paint(win);
        bossWeapon.paint(win);
    }

    public void attack(){
        currentWeapon.attack();
    }

    public void enemyAttack(){
        for (int i = 0; i < enemies.getCurrentEnemies().size(); i++) {
            Enemy enemy = enemies.getCurrentEnemies().get(i);
            if (enemy.canRangeAttack) {
                bossWeapon.enemyRangeAttack(enemy);
            }
        }
    }

    public void checkCollision(){
        currentWeapon.checkCollision();
    }

    public void changeWeapon(){
        int index = availableWeapons.indexOf(currentWeapon);

        if (index + 1 < availableWeapons.size()){
            currentWeapon = availableWeapons.get(index + 1);
        } else {
            currentWeapon = availableWeapons.get(0);
        }
    }

}
