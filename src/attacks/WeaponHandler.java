package attacks;

import GameStates.GamePanel;
import being.EnemyHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import being.Enemy;
import being.Protagonist;
import maps.MapHandler;
import pickup.Items;
import graphics.TileShape;
import sound.SoundHandler;

public class WeaponHandler {

    private final MapHandler maps;
    private Weapon currentWeapon;
    private Weapon bossWeapon;
    private ArrayList<Weapon> availableWeapons;
    private ArrayList<Items> requiredItems = new ArrayList<>(Arrays.asList(Items.PROJECTILE, Items.CUPIDBOW, Items.DAGGER));
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
    private Timer enemy_attack_timer;
    private int playerDim;
    private SoundHandler sound;


    // Constructor initialises array of bullets
    public WeaponHandler(MapHandler maps, ProjectileHandler projectiles, Protagonist player, EnemyHandler enemies, GamePanel panel, SoundHandler sound) {
        this.sound = sound;
        canAttack = true;
        playerDim = player.getHeight();
        this.maps = maps;
        this.game = panel;
        availableWeapons = new ArrayList<>();
        currentWeapon = null;
        currentItem = null;
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

        this.attack_timer = new Timer(300, (new ActionListener() { //this timer shows what can be done
            @Override
            public void actionPerformed(ActionEvent e) {
                canAttack = true;
            }
        }));
        attack_timer.start();

        this.enemy_attack_timer = new Timer(5000, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enemyAttack(); //todo WILLIAM I MOVED THE METHOD HERE i recommend using timers.
            }
        }));
        enemy_attack_timer.start();

    }

    public void obtainAllWeapons(){
        for (Items item : requiredItems){
            if (!containsWeapon(item)){
                addWeapon(item);
            }
        }
    }

    private boolean containsWeapon(Items item){
        Boolean contains = false;
        for (Weapon weapon : availableWeapons){
            if (weapon.getItems() == item){
                contains = true;
            }
        }
        return contains;
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
            case DAGGER:
                weapon = new Dagger(player, 40, 40, "daggerNorth.png", false, enemies);
                currentWeapon = weapon;
                currentItem = Items.DAGGER;
                icons.get(currentWeapon.getItems()).setIsRenderable(true);
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
        if (currentWeapon != null) {
            currentWeapon.paint(win);
            icons.get(currentItem).renderShape(win);

        }
        bossWeapon.paint(win);
    }

    public void attack() {
        if (!canAttack) {
            return;
        }
        if (currentWeapon.getSoundFile() != null){
            sound.play(currentWeapon.getSoundFile());
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
        if (currentWeapon != null) {
            currentWeapon.checkCollision();
        }
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
        icons.put(Items.DAGGER, new TileShape(x, y, width, height, "daggerNorth.png", false));
        icons.put(Items.PROJECTILE, new TileShape(x, y, width, height, "arrowNorth.png", false));
        icons.put(Items.CUPIDBOW, new TileShape(x, y, width, height, "cupid.png", false));
    }

    public void stopTimers() {
        canAttack = false;
        velocity_timer.stop();
        for (int i = 0; i < availableWeapons.size(); i++) {
            availableWeapons.get(i).stopTimers();
        }
        if (currentWeapon != null) {
            currentWeapon.stopTimers();
        }

        attack_timer.stop();
        enemy_attack_timer.start();

    }

    public void startTimers() {
        canAttack = true;
        velocity_timer.start();
        for (int i = 0; i < availableWeapons.size(); i++) {
            availableWeapons.get(i).startTimers();
        }
        attack_timer.start();
        enemy_attack_timer.stop();
    }

}
