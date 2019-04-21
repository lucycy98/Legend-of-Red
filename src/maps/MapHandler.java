package maps;

import GameStates.GamePanel;
import being.EnemyHandler;
import graphics.TileShape;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MapHandler {

    HashMap<Integer, Map> maps;
    private int currentLevel;
    private int tutorialLevel = 0;
    private int bossLevel = 4;
    private int totalLevels = 5;
    int xTiles = 17;
    int yTiles = 12;
    private EnemyHandler enemies;
    private Boolean gameComplete;
    private GamePanel game;

    // Constructor initialises array of bullets
    public MapHandler(GamePanel game) {
        this.game = game;
        gameComplete = false;
        maps = new HashMap<>();
        for (int i = 0; i < totalLevels; i++) {
            int level = i;
            Map map;
            if (level == tutorialLevel || level == bossLevel) {
                map = new Map(0, false);
            } else {
                map = new Map(20, true);
            }

            if (level > tutorialLevel) {
                map.addBackwardsPortal();
            }
            maps.put(level, map);
        }
        currentLevel = tutorialLevel;
    }

    public int getTotalLevels() {
        return totalLevels;
    }

    public Map getMap(int level){
        if (level < 0 || level > totalLevels){
            return null;
        }
        return maps.get(level);
    }


    public void setFinalLevel() {
        currentLevel = bossLevel;
        if (enemies != null) {
            enemies.setEnemy();
        }
    }

    public void setNextLevel() {
        if (currentLevel + 1 < totalLevels){
            currentLevel++;
        } else {
            gameComplete = true;
            game.gameWon();
        }
    }

    public boolean gameIsWon(){
        return gameComplete;
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

    public void addEnemyHandler(EnemyHandler enemy) {
        enemies = enemy;
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
