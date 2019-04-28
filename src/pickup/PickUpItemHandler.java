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

/**
 * this class deals with generating pick up items
 * it decides which item and at what number of enemies killed should the player obtain the pick up item.
 */
public class PickUpItemHandler {

    private final MapHandler maps;
    private Timer velocity_timer;
    private int levels;
    private int[] killedEnemies = {0,0,0,0,0}; //number of enemies killed per level.
    private int[] numberOfEnemies = new int[5];
    private int lastLevel = 1;
    private ArrayList<PickUpItem> itemsList;
    //this array list represents the order of items to be recieved.
    private ArrayList<Items> levelPickUps = new ArrayList<>(Arrays.asList(Items.PROJECTILE, Items.CUPIDBOW, Items.WOLFSKIN, Items.HEALTH));
    private int currentPickUps;
    //this hashmap will represent the allocated time when pick up item will be generated (at which wolf index killed)
    private HashMap<Integer, ArrayList> pickupIndices = new HashMap<>();
    private WeaponHandler weapon;
    private Protagonist player;
    private SoundHandler sound;

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


    /**
     * called by enemy handler when new enemies are created
     */
    public void addNumberOfEnemies(int level, int number){
        if (level > levels || number < 1){
            return;
        }
        numberOfEnemies[level] = number;
        assignPickUp(level);
    }

    /**
     * based on the level, this method defines logic for which index of wolf killed should result in a pick up item
     */
    public void assignPickUp(int level){
        if (level >= levels - 1 || levels < 0){
            return;
        }
        ArrayList indices = new ArrayList();
        int index;
        switch(level){
            case 0: //tutorial level
                break;
            case 4:
                break;
            case 2: //level 2: 2 pick ups here
                index = ThreadLocalRandom.current().nextInt(2, numberOfEnemies[level]);
                int index_2 = index;
                while (index_2 == index){
                    index_2 = ThreadLocalRandom.current().nextInt(2, numberOfEnemies[level]);
                }
                indices.add(index);
                indices.add(index_2);
                break;
            default: //levels 1, 3
                index = ThreadLocalRandom.current().nextInt(2, numberOfEnemies[level]);
                indices.add(index);
                break;
        }
        pickupIndices.put(level, indices);
    }

    /**
     * called when enemy is killed. checks which level and updates model of enemies killed
     */
    public void addEnemiesKilled(int level, int x, int y){
        if (level > levels || level < 1){
            return;
        }
        lastLevel = level;
        killedEnemies[level]++; //updates values.
        Items pickup = getPickupItem(); //checks if this level and index results in a pick up item.
        if (pickup != null) {
            createItem(x, y, pickup); //create item and x y location
        }
    }

    /**
     * creates a pick up item object based on item.
     */
    public void createItem(int x, int y, Items item){
        PickUpItem pickup;
        if (weapon == null) {
            pickup = new PickUpItem(player, x, y, item, maps);
        } else {
            pickup = new PickUpItem(player, x, y, item, maps, weapon);
        }
        itemsList.add(pickup);
    }

    /**
     * this method checks whether the index killed at certain level results in a pick up item
     * returns pick up item if valid otherwise null
     * @return
     */
    private Items getPickupItem() {
        Items item = null;
        int indexKilled = killedEnemies[lastLevel];
        if (indexKilled == 1) {
            return Items.KEY;
        }
        ArrayList indices = pickupIndices.get(lastLevel);
        if(indices.contains(indexKilled) && currentPickUps < levelPickUps.size()){
            item = levelPickUps.get(currentPickUps);
            currentPickUps++;
        }
        return item;
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

    public void collectDagger(){
        for (int i = 0; i < itemsList.size(); i++){
            PickUpItem item = itemsList.get(i);
            if (item.getItem() == Items.DAGGER){
                item.collectItem();
                itemsList.remove(item);

            }
        }
    }

    public void addPlayer(Protagonist player) {
        this.player = player;
    }

    public void addWeaponHandler(WeaponHandler weapon){
        this.weapon = weapon;
    }

    public void stopTimers(){
        velocity_timer.stop();
    }

    public void startTimers(){
        velocity_timer.start();
    }

}
