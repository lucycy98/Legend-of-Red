import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

/**
 * keep incrementing x, y until key release OR another arrow is pressed.
 */
public class Protagonist {

    enum Direction {UP, DOWN, LEFT, RIGHT};

    // co-ordinates of player
    int x;
    int y;
    int width; int height;
    private Rectangle2D rec;
    private Direction direction;
    Timer move_timer;
    Boolean releasedUp = true; Boolean releasedDown = true; Boolean releasedLeft = true; Boolean releasedRight = true;


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
        this.move_timer = new Timer(1000/300, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer();
            }
        }));
        move_timer.start();
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

    public void movePlayer(){
        if (!releasedRight){
            x = x + 1;
        }
        if (!releasedLeft){
            x = x - 1;
        }
        if (!releasedDown){
            y = y + 1;
        }
        if (!releasedUp){
            y = y - 1;
        }
        rec.setRect(x, y, width, height);
    }

    public void setDirection(Direction d){
        direction = d;
        switch (direction) {
            case UP:
                releasedUp = false;
                break;
            case DOWN:
                releasedDown = false;
                break;
            case LEFT:
                releasedLeft = false;
                break;
            case RIGHT:
                releasedRight = false;
                break;

            default:
                break;
        }

    }

    public void setReleased(Direction direction){
        switch (direction) {
            case UP:
                releasedUp = true;
                break;
            case DOWN:
                releasedDown = true;
                break;
            case LEFT:
                releasedLeft = true;
                break;
            case RIGHT:
                releasedRight = true;
                break;

            default:
                break;
        }
    }

}
