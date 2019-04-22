package pickup;

import attacks.WeaponHandler;
import being.Protagonist;
import graphics.TileShape;
import maps.MapHandler;
import sound.SoundHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class PickUpItemHandler {

    private final MapHandler maps;
    private Timer velocity_timer;
    private int levels;
    private int[] killedEnemies = {0,0,0,0,0};
    private int[] numberOfEnemies = new int[5];
    private int lastLevel = 1;
    private ArrayList<PickUpItem> itemsList;
    private ArrayList<Items> levelPickUps = new ArrayList<>(Arrays.asList(Items.PROJECTILE, Items.CUPIDBOW, Items.WOLFSKIN, Items.HEALTH));
    private int currentPickUps;
    private HashMap<Integer, ArrayList> pickupIndices = new HashMap<>();
    private WeaponHandler weapon;
    private Protagonist player;
    private SoundHandler sound;

    // Constructor initialises array of bullets
    public PickUpItemHandler(MapHandler maps, SoundHandler sound) {
        this.sound = sound;
        this.maps = maps;
        itemsList = new ArrayList<>();
        currentPickUps = 0;
        levels = maps.getTotalLevels();
        numberOfEnemies[0] = 0;

        this.velocity_timer = new Timer(1000/300, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkCollision();
            }
        }));
        velocity_timer.start();
    }

    public void addWeaponHandler(WeaponHandler weapon){
        this.weapon = weapon;
    }

    /**
     * called by enemy handler when new enemies are created
     * @param level
     * @param number
     */
    public void addNumberOfEnemies(int level, int number){
        if (level > levels || number < 1){
            return;
        }
        numberOfEnemies[level] = number;
        assignPickUp(level);
    }

    public void assignPickUp(int level){
        if (level >= levels - 1 || levels < 0){
            return;
        }
        ArrayList indices = new ArrayList();

        int index;
        switch(level){
            case 0: //tutorial level
                System.out.println("level 0");
                break;
            case 4:
                System.out.println("boss level");
            case 2: //2 pick ups here
                index = ThreadLocalRandom.current().nextInt(2, numberOfEnemies[level]);
                int index_2 = index;
                while (index_2 == index){
                    index_2 = ThreadLocalRandom.current().nextInt(2, numberOfEnemies[level]);
                }
                indices.add(index);
                indices.add(index_2);
                System.out.println("assigning pickup for level" + level + " with index " + index + " " + index_2);
                break;
            default: //levels 1, 3
                index = ThreadLocalRandom.current().nextInt(2, numberOfEnemies[level]);
                indices.add(index);
                break;
        }
        pickupIndices.put(level, indices);
    }

    public void addPlayer(Protagonist player) {
        this.player = player;
    }

    public void paint(Graphics2D win){
        for (PickUpItem item: itemsList){
            item.paint(win);
            if (item.getItem() == Items.DAGGER){
                TileShape daggerIns = new TileShape(item.getX() + 60, item.getY() + 60, 900, 160, "dialogue/daggerIns.png", true);
                daggerIns.renderShape(win);
            } else if (item.getItem() == Items.KEY & maps.getCurrentLevel() == 1){
                TileShape daggerIns = new TileShape(item.getX() + 60, item.getY() + 60, 900, 160, "dialogue/keyIns.png", true);
                daggerIns.renderShape(win);
            }else if (item.getItem() == Items.PROJECTILE){
                TileShape daggerIns = new TileShape(item.getX() + 60, item.getY() + 60, 900, 160, "dialogue/bowIns.png", true);
                daggerIns.renderShape(win);
            }else if (item.getItem() == Items.HEALTH){
                TileShape daggerIns = new TileShape(item.getX() + 60, item.getY() + 60, 900, 160, "dialogue/heartIns.png", true);
                daggerIns.renderShape(win);
            }else if (item.getItem() == Items.CUPIDBOW){
                TileShape daggerIns = new TileShape(item.getX() + 60, item.getY() + 60, 900, 160, "dialogue/cupidIns.png", true);
                daggerIns.renderShape(win);
            }else if (item.getItem() == Items.WOLFSKIN){
                TileShape daggerIns = new TileShape(item.getX() + 60, item.getY() + 60, 900, 160, "dialogue/skinIns.png", true);
                daggerIns.renderShape(win);
            }
        }
    }

    //checks whether player collides with item.
    public void checkCollision(){
        if (player == null){
            return;
        }
        Rectangle playerRec = player.getBounds();
        for (int i = 0; i < itemsList.size(); i++){
            PickUpItem item = itemsList.get(i);
            Rectangle itemRec = item.getBounds();
            if (playerRec.intersects(itemRec)) {
                if (sound != null) {
                    sound.play("collect.wav");
                }
                item.collectItem();
                itemsList.remove(i);
            }
        }
    }

    public void addEnemiesKilled(int level, int x, int y){ //to be called by enemy handler
        if (level > levels || level < 1){
            return;
        }
        System.out.println("current level is " + level);
        lastLevel = level;
        killedEnemies[level]++;
        Items pickup = getPickupItem();
        if (pickup != null) {
            System.out.println("item recieved!");
            System.out.println(pickup);
            createItem(x, y, pickup);
        }
    }

    public void createItem(int x, int y, Items item){
        PickUpItem pickup;
        if (weapon == null) {
            System.out.println("null");
            pickup = new PickUpItem(player, x, y, item, maps);
        } else {
            pickup = new PickUpItem(player, x, y, item, maps, weapon);
        }
        itemsList.add(pickup);
    }

    private Items getPickupItem() {
        System.out.println("wolves killed per level is " + killedEnemies[lastLevel]);
        Items item = null;
        int indexKilled = killedEnemies[lastLevel];
        if (indexKilled == 1) {
            return Items.KEY;
        }
        ArrayList indices = pickupIndices.get(lastLevel);
        System.out.println("indices are " + indices);
        if(indices.contains(indexKilled) && currentPickUps < levelPickUps.size()){
            item = levelPickUps.get(currentPickUps);
            currentPickUps++;
            System.out.println("current pick up is " + currentPickUps);
        }
        return item;
    }

    public void stopTimers(){
        velocity_timer.stop();
    }

    public void startTimers(){
        velocity_timer.start();
    }

}
