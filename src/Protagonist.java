import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Protagonist {

    enum Direction {UP, DOWN, LEFT, RIGHT};

    // co-ordinates of player
    int x;
    int y;
    int width; int height;
    private Rectangle2D rec;


    public Protagonist(int width, int height) {
        // call main constructor with default values
        this(30, 30, width, height);

    }

    public Protagonist(int xPos, int yPos, int w, int h) {
        x = xPos;
        y = yPos;
        width = w;
        height = h;
        rec = new Rectangle2D.Double(x, y, w, h);
    }



    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void paint(Graphics2D win){
        win.draw(rec);
    }

    public void move(Direction direction){
        switch (direction) {
            case LEFT:
                x = x - 2;
                break;
            case RIGHT:
                x = x + 2;
                break;
            case UP:
                y = y - 2;
                break;
            case DOWN:
                y = y + 2;
                break;
        }
        rec.setRect(x, y, width, height);
    }

}
