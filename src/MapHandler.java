import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MapHandler {

    HashMap<Integer, Map> maps;
    private int currentLevel;
    int xTiles = 17;
    int yTiles = 12;

    // Constructor initialises array of bullets
    public MapHandler() {
        maps = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            Map map = new Map(20);
            if (i != 0) {
                map.addBackwardsPortal();
            }
            maps.put(i, map);
        }
        Map map = new Map(0);
        map.addBackwardsPortal();
        maps.put(3, map);
        currentLevel = 0;
    }

    public int setNextLevel() {
        currentLevel++;
        return currentLevel;
    }

    public int setPreviousLevel() {
        currentLevel--;
        return currentLevel;
    }

    public int getxTiles() {
        return xTiles;
    }

    public int getyTiles() {
        return yTiles;
    }

    public void addPortal() { //when key is found
        maps.get(currentLevel).addForwardsPortal();
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public ArrayList<TileShape> getCurrentObstacles() {
        return maps.get(currentLevel).getObstacles();
    }

    public ArrayList<TileShape> getCurrentForwardPortal() {
        return maps.get(currentLevel).getForwardPortals();
    }

    public ArrayList<TileShape> getCurrentBackwardPortal() {
        return maps.get(currentLevel).getBackwardPortals();
    }

    public void paint(Graphics win) {
        maps.get(currentLevel).paint(win);
    }
}
