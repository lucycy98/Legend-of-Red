import java.awt.*;

public class Map {

//    Shape[][] map = new Shape[32][24];
    TileShape[][] map = new TileShape[32][24];
    int tileSize = 40;

    public Map() {
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 24; j++) {
                if (i == 0 | i == 31 | j == 0 | j == 23) {
//                    map[i][j] = new Shape(i*tileSize, j*tileSize, tileSize, tileSize, Color.BLACK, true);
                    map[i][j] = new TileShape(i*tileSize, j*tileSize,"tree.png", true);
                } else if (i % 8 == 0 & j % 8 == 0) {
//                    map[i][j] = new Shape(i*tileSize, j*tileSize, tileSize, tileSize, Color.DARK_GRAY, true);
                    map[i][j] = new TileShape(i*tileSize, j*tileSize,"tree.png", true);
                } else {
                    map[i][j] = new TileShape(i*tileSize, j*tileSize,"grass.jpg", true);
//                    map[i][j] = new Shape(i*tileSize, j*tileSize, tileSize, tileSize, Color.GREEN, true);
                }
            }
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
