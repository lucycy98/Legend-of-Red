package model.attacks;

import model.pickup.Items;
import model.TileShape;
import model.being.EnemyHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import model.being.Enemy;
import model.being.Protagonist;
import controller.GameEngine;
import model.maps.MapHandler;
import sound.SoundHandler;

/**
 * this class deals with weapon attacks and logic.
 * it treats weapons as the weapons (the interface) calling methods specific to the interface.
 * also shows the current weapon in the model.game panel screen.
 * weapons include projectile, cupidbow and dagger.
 */

public class WeaponHandler {

    private final MapHandler maps;
    private Weapon currentWeapon;
    private Weapon bossWeapon;
    private ArrayList<Weapon> availableWeapons;
    private ArrayList<Items> requiredItems = new ArrayList<>(Arrays.asList(Items.PROJECTILE, Items.CUPIDBOW, Items.DAGGER));
    private Items currentItem;
    private Timer velocity_timer;
    private EnemyHandler enemies;
    private Protagonist player;
    private GameEngine game;
    private HashMap<Items, TileShape> icons;
    private int scaleFactor;
    private int counter;
    private Boolean canAttack;
    private Timer attack_timer;
    private Timer enemy_attack_timer;
    private int playerDim;
    private SoundHandler sound;

    // Constructor initialises array of bullets
    public WeaponHandler(MapHandler maps, Protagonist player, EnemyHandler enemies, GameEngine panel, SoundHandler sound) {
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

        this.velocity_timer = new Timer(1000 / 100, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkCollision();
            }
        }));
        velocity_timer.start();
        scaleFactor = 15;
        counter = 0;

        this.attack_timer = new Timer(300, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canAttack = true;
            }
        }));
        attack_timer.start();

        this.enemy_attack_timer = new Timer(5000, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enemyAttack();
            }
        }));
        enemy_attack_timer.start();
    }

    //////////////// adding removing and switching weapons \\\\\\\\\\\\\\\\\\\\\\\\

    /**
     * creates weapon and adds to inventory.
     * @param item
     */
    public void addWeapon(Items item) {
        Weapon weapon;
        switch (item) {
            case PROJECTILE:
                weapon = new ProjectileHandler(maps, player, enemies, Items.PROJECTILE);
                break;
            case CUPIDBOW:
                weapon = new ProjectileHandler(maps, player, enemies, Items.CUPIDBOW, this);
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
                availableWeapons.remove(weapon);
                weapon = null; //get rid of it
            }
        }
    }

    public void changeWeapon() {
        if (currentWeapon == null){
            return;
        }

        if (availableWeapons.size() > 1){
            sound.play("changeWeapon.wav");
        }
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

    public void obtainAllWeapons(){
        for (Items item : requiredItems){
            if (!containsWeapon(item)){
                addWeapon(item);
            }
        }
    }

    //////////////////////////////////// attack \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    /**
     * checks if weapon collided.
     */
    public void checkCollision() {
        if (currentWeapon != null) {
            currentWeapon.checkCollision();
        }
        bossWeapon.checkEnemyCollision();
    }

    /**
     * player attack - add sound and attack for current weapon.
     */
    public void attack() {
        if (!canAttack) {
            return;
        }
        if (currentWeapon.getSoundFile() != null){
            sound.play(currentWeapon.getSoundFile());
        }
        currentWeapon.attack();
        canAttack = false;
    }

    /**
     * attack for boss wolf (projectile).
     */
    public void enemyAttack() {
        for (int i = 0; i < enemies.getCurrentEnemies().size(); i++) {
            Enemy enemy = enemies.getCurrentEnemies().get(i);
            if (enemy.getcanRangeAttack()) {
                bossWeapon.enemyRangeAttack(enemy);
            }
        }
    }

    /**
     * cupid bow can only be used ONCE.
     */
    public void cupidUsed(){
        removeWeapon(Items.CUPIDBOW);
        changeWeapon();
    }

    ////////////////// other methods \\\\\\\\\\\\\\\\\\\\\\\\
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

    private boolean containsWeapon(Items item){
        Boolean contains = false;
        for (Weapon weapon : availableWeapons){
            if (weapon.getItems() == item){
                contains = true;
            }
        }
        return contains;
    }

    /**
     * creates icon for current weapon in UI
     */
    public void createImages(int x, int y, int width, int height) {
        icons = new HashMap<>();
        icons.put(Items.DAGGER, new TileShape(x, y, width, height, "daggerIcon.png", false));
        icons.put(Items.PROJECTILE, new TileShape(x, y, width, height, "bow.png", false));
        icons.put(Items.CUPIDBOW, new TileShape(x, y, width, height, "cupid.png", false));
    }

    public void paint(Graphics2D win) {
        if (currentWeapon != null) {
            currentWeapon.paint(win);
            icons.get(currentItem).renderShape(win);

        }
        bossWeapon.paint(win);
    }

}
