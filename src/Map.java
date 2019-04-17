import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Map {

    TileShape[][] _map = new TileShape[32][24];
    ArrayList<TileShape> _obstacles = new ArrayList<TileShape>();
    ArrayList<TileShape> _portals = new ArrayList<TileShape>();
    int tileSize = 40;

    public Map(int num_obs) {
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 24; j++) {
                if (i == 0 | i == 31 | j == 0 | j == 23) {
                    TileShape tile = new TileShape(i * tileSize, j * tileSize, 40, 40, "tree.png", true);
                    _obstacles.add(tile);
                    _map[i][j] = tile;
                } else {
                    _map[i][j] = new TileShape(i * tileSize, j * tileSize, 40, 40, "grass.jpg", true);
                }
            }
        }

        for (int k = 0; k < num_obs; k++) {
            int x = new Random().nextInt(32);
            int y = new Random().nextInt(24);
            TileShape tile = new TileShape(x * tileSize, y * tileSize, 40, 40, "tree.png", true);
            _obstacles.add(tile);
            _map[x][y] = tile;
        }
    }

    public void addPortal(){
        System.out.println("portal added");
        TileShape portal = new TileShape(30 * tileSize, 11 * tileSize, 40, 40, "portal.jpg", true);
        _portals.add(portal);
        _map[30][11] = portal;
    }

    public void paint(Graphics g) {
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 24; j++) {
                _map[i][j].renderShape(g);
            }
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
