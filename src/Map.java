import java.awt.*;

public class Map {
    int[][] map = new int[32][24];

    public Map() {
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 24; j++) {
                if (i == 0 | i == 31 | j == 0 | j == 23) {
                    map[i][j] = 1;
                } else if (i % 8 == 0 & j % 8 == 0) {
                    map[i][j] = 1;
                } else {
                    map[i][j] = 0;
                }
            }
        }
    }

    public void paint(Graphics g) {
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 24; j++) {
                switch (map[i][j]) {
                    case 0:
                        g.fillRect(i * 40, j * 40, 40, 40);
                        g.setColor(Color.GREEN);
                    case 1:
                        g.fillRect(i * 40, j * 40, 40, 40);
                        g.setColor(Color.BLACK);
                }
            }
        }
    }
}
