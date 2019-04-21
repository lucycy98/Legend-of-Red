package maps;

import graphics.TileShape;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Map {

    private int xTiles = 17;
    private int yTiles = 12;
    private TileShape[][] _map = new TileShape[xTiles][yTiles];
    private ArrayList<TileShape> _grass = new ArrayList<>();
    private ArrayList<TileShape> _obstacles = new ArrayList<>();
    private ArrayList<TileShape> _bportals = new ArrayList<>();
    private ArrayList<TileShape> _fportals = new ArrayList<>();

    private TileShape fportal;
    private TileShape bportal;
    int tileSize = 60;

    private String houseGround = "floor.png";
    private String houseObs = "brick.png";

    private String forestGround = "grass.jpg";
    private String forestObs = "tree.png";


    public Map(int num_obs, Boolean isForest) {
        String ground;
        String obs;
        if (isForest) {
            ground = forestGround;
            obs = forestObs;
        } else {
            ground = houseGround;
            obs = houseObs;
        }

        for (int i = 0; i < xTiles; i++) {
            for (int j = 0; j < yTiles; j++) {
                if (i == 0 | i == xTiles - 1 | j == 0 | j == yTiles - 1) {
                    TileShape tree = new TileShape(i * tileSize, j * tileSize, tileSize, tileSize, obs, true);
                    _obstacles.add(tree);
                    _map[i][j] = tree;
                }
                TileShape grass = new TileShape(i * tileSize, j * tileSize, tileSize, tileSize, ground, true);
                _grass.add(grass);
                _map[i][j] = grass;
            }
        }

        for (int k = 0; k < num_obs; k++) {
            int x = new Random().nextInt(xTiles);
            int y = new Random().nextInt(yTiles);
            if (x != (xTiles - 2) * tileSize & y != yTiles / 2 * tileSize) {
                TileShape tile = new TileShape(x * tileSize, y * tileSize, tileSize, tileSize, obs, true);
                _obstacles.add(tile);
            } else {
                k--;
            }
        }
    }


    public void addForwardsPortal() {
        System.out.println("forwards portal added");
        TileShape portal = new TileShape((xTiles - 2) * tileSize, yTiles / 2 * tileSize, tileSize, tileSize, "portal.png", true);
        _fportals.add(portal);
        fportal = portal;
    }

    public void addBackwardsPortal() {
        System.out.println("backwards portal added");
        TileShape portal = new TileShape(tileSize, yTiles / 2 * tileSize, tileSize, tileSize, "portal.png", true);
        _bportals.add(portal);
        bportal = portal;
    }

    public void paint(Graphics g) {
        for (int i = 0; i < xTiles; i++) {
            for (int j = 0; j < yTiles; j++) {
                _map[i][j].renderShape(g);
            }
        }
        for (TileShape obs : _obstacles) {
            obs.renderShape(g);
        }
        for (TileShape portal : _fportals) {
            portal.renderShape(g);
        }
        for (TileShape portal : _bportals) {
            portal.renderShape(g);
        }

    }

    public ArrayList<TileShape> getObstacles() {
        return this._obstacles;
    }

    public void setObstacles(ArrayList<TileShape> _obstacles) {
        this._obstacles = _obstacles;
    }

    public TileShape getForwardPortals() {
        return fportal;
    }

    public TileShape getBackwardPortals() {
        return bportal;
    }
}
