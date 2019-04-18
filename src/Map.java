import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Map {

    int xTiles = 17;
    int yTiles = 12;
    TileShape[][] _map = new TileShape[xTiles][yTiles];
    //    TileShape[][] _grass = new TileShape[xTiles][yTiles];
    ArrayList<TileShape> _grass = new ArrayList<TileShape>();
    ArrayList<TileShape> _obstacles = new ArrayList<TileShape>();
    ArrayList<TileShape> _portals = new ArrayList<TileShape>();
    int tileSize = 60;


    public Map(int num_obs) {
        for (int i = 0; i < xTiles; i++) {
            for (int j = 0; j < yTiles; j++) {
                if (i == 0 | i == xTiles - 1 | j == 0 | j == yTiles - 1) {
                    TileShape tree = new TileShape(i * tileSize, j * tileSize, tileSize, tileSize, "tree.png", true);
                    _obstacles.add(tree);
                    _map[i][j] = tree;
                }
//                else{
                TileShape grass = new TileShape(i * tileSize, j * tileSize, tileSize, tileSize, "grass.jpg", true);
                _grass.add(grass);
                _map[i][j] = grass;
//                }

            }
        }

        for (int k = 0; k < num_obs; k++) {
            int x = new Random().nextInt(xTiles);
            int y = new Random().nextInt(yTiles);
            if (x != (xTiles - 2) * tileSize & y != yTiles / 2 * tileSize) {
                TileShape tile = new TileShape(x * tileSize, y * tileSize, tileSize, tileSize, "tree.png", true);
                _obstacles.add(tile);
            } else {
                k--;
            }
//            _map[x][y] = tile;
        }
    }

    public void addPortal() {
        System.out.println("portal added");
        TileShape portal = new TileShape((xTiles - 2) * tileSize, yTiles / 2 * tileSize, tileSize, tileSize, "portal.png", true);
        _portals.add(portal);
//        _map[xTiles-2][yTiles/2] = portal;
    }

    public void paint(Graphics g) {
        for (int i = 0; i < xTiles; i++) {
            for (int j = 0; j < yTiles; j++) {
                _map[i][j].renderShape(g);
//                _grass[i][j].renderShape(g);
            }
        }
        for (TileShape obs : _obstacles) {
            obs.renderShape(g);
        }
        for (TileShape portal : _portals) {
            portal.renderShape(g);
        }

    }

    public ArrayList<TileShape> getObstacles() {
        return this._obstacles;
    }

    public void setObstacles(ArrayList<TileShape> _obstacles) {
        this._obstacles = _obstacles;
    }

    public ArrayList<TileShape> getPortals() {
        return _portals;
    }
}
