import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MapHandler {

    HashMap<Integer, Map> maps;
    private int currentLevel;

    // Constructor initialises array of bullets
    public MapHandler() {
        maps = new HashMap<>();
        for (int i = 0; i < 3; i++){
            maps.put(i, new Map());
        }
        currentLevel = 1;
    }

    public int setNextLevel(){
        currentLevel++;
        return currentLevel;
    }

    public int getCurrentLevel(){
        return currentLevel;
    }

    public ArrayList<TileShape> getCurrentObstacles(){
        return maps.get(currentLevel).getObstacles();
    }

    public ArrayList<TileShape> getCurrentPortal() {
        return maps.get(currentLevel).getPortals();
    }

    public void paint(Graphics win){
        maps.get(currentLevel).paint(win);
    }
}
