import java.awt.*;
import java.util.Random;

public class Map {

    TileShape[][] map = new TileShape[32][24];
    int tileSize = 40;

    public Map() {
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 24; j++) {
                if (i == 0 | i == 31 | j == 0 | j == 23) {
                    map[i][j] = new TileShape(i*tileSize, j*tileSize,"tree.png", true);
                } else {
                    map[i][j] = new TileShape(i*tileSize, j*tileSize,"grass.jpg", true);
                }
            }
        }

        for (int k = 0; k < 20; k++){
            int x = new Random().nextInt(32);
            int y = new Random().nextInt(24);
            map[x][y] = new TileShape(x*tileSize, y*tileSize,"tree.png", true);
        }
    }

    public void paint(Graphics g) {
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 24; j++) {
                map[i][j].renderShape(g);
            }
        }
    }
}
