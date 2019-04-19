import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class WeaponHandler {

    private final MapHandler maps;
    private Weapon currentWeapon;
    private Weapon bossWeapon;
    private ArrayList<Weapon> availableWeapons;
    private Items currentItem;
    Timer velocity_timer;
    EnemyHandler enemies;
    Protagonist player;
    GamePanel game;
    HashMap<Items, TileShape> icons;
    int scaleFactor;
    int counter;
    Boolean canAttack;
    private Timer attack_timer;


    // Constructor initialises array of bullets
    public WeaponHandler(MapHandler maps, ProjectileHandler projectiles, Protagonist player, EnemyHandler enemies, GamePanel panel) {
        canAttack = true;
        this.maps = maps;
        this.game = panel;
        availableWeapons = new ArrayList<>();
        Weapon dagger = new Dagger(player, 60, 60, "daggerNorth.png", false, enemies);
        availableWeapons.add(dagger);
        currentWeapon = dagger;
        currentItem = Items.DAGGER;
        bossWeapon = new ProjectileHandler(maps, player, enemies, Items.PROJECTILE);
        this.enemies = enemies;
        this.player = player;

        this.velocity_timer = new Timer(1000 / 300, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkCollision();
            }
        }));
        velocity_timer.start();
        scaleFactor = 15;
        counter = 0;

        this.attack_timer = new Timer(150, (new ActionListener() { //this timer shows what can be done
            @Override
            public void actionPerformed(ActionEvent e) {
                canAttack = true;
                counter++;
                if (counter % scaleFactor == 0) {
                    enemyAttack(); //todo WILLIAM I MOVED THE METHOD HERE
                }
            }
        }));
        attack_timer.start();
    }

    public void addWeapon(Items item) {
        Weapon weapon;
        switch (item) {
            case PROJECTILE:
                weapon = new ProjectileHandler(maps, player, enemies, Items.PROJECTILE);
                break;
            case CUPIDBOW:
                weapon = new ProjectileHandler(maps, player, enemies, Items.CUPIDBOW);
                break;
            default:
                weapon = null;
        }
        if (weapon != null) {
            availableWeapons.add(weapon);
        }
    }

    public void removeWeapon(Items items) {
        for (int i = 0; i < availableWeapons.size(); i++) {
            Weapon weapon = availableWeapons.get(i);
            Items currentItem = weapon.getItems();
            if (currentItem == items) {
                availableWeapons.remove(i);
                weapon = null; //get rid of it
            }
        }
    }

    public void paint(Graphics2D win) {

        currentWeapon.paint(win);
        bossWeapon.paint(win);
        icons.get(currentItem).renderShape(win);
    }

    public void attack() {
        if (!canAttack) {
            return;
        }
        currentWeapon.attack();
        if (currentWeapon.getItems() == Items.CUPIDBOW) {
            removeWeapon(Items.CUPIDBOW);
        }
        canAttack = false;
    }

    public void enemyAttack() {
        for (int i = 0; i < enemies.getCurrentEnemies().size(); i++) {
            Enemy enemy = enemies.getCurrentEnemies().get(i);
            if (enemy.canRangeAttack) {
                bossWeapon.enemyRangeAttack(enemy);
            }
        }
    }

    public void checkCollision() {
        currentWeapon.checkCollision();
        bossWeapon.checkEnemyCollision();
    }

    public void changeWeapon() {
        int index = availableWeapons.indexOf(currentWeapon);
        icons.get(currentWeapon.getItems()).setIsRenderable(false);

        if (index + 1 < availableWeapons.size()) {
            currentWeapon = availableWeapons.get(index + 1);
        } else {
            currentWeapon = availableWeapons.get(0);
        }
        icons.get(currentWeapon.getItems()).setIsRenderable(true);
        currentItem = currentWeapon.getItems();
    }

    public Items getCurrentWeapon() {
        return currentItem;
    }

    public void createImages(int x, int y, int width, int height) {
        icons = new HashMap<>();
        icons.put(Items.DAGGER, new TileShape(x, y, width, height, "daggerNorth.png", true));
        icons.put(Items.PROJECTILE, new TileShape(x, y, width, height, "arrowNorth.png", false));
        icons.put(Items.CUPIDBOW, new TileShape(x, y, width, height, "cupid.png", false));
    }

    public void stopTimers(){
        canAttack  = false;
        velocity_timer.stop();
        for (int i = 0; i < availableWeapons.size(); i++){
            availableWeapons.get(i).stopTimers();
        }
        currentWeapon.stopTimers();
        attack_timer.stop();

    }

    public void startTimers(){
        canAttack  = true;
        velocity_timer.start();
        for (int i = 0; i < availableWeapons.size(); i++){
            availableWeapons.get(i).startTimers();
        }
        attack_timer.start();
    }

}
