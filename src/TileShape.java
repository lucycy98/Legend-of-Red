import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * this class represents a tile shape with an image e.g hedge, enemy etc
 */
public class TileShape extends Shape {

    private Image tileImg = null;

    public TileShape(int x, int y, String img, Boolean r) {
        super(x, y);
        tileImg = ImageHandler.getImage(img);
        setIsRenderable(r);
    }

    @Override
    public void renderShape(Graphics g) {
        if (isRenderable()) { //
            g.drawImage(tileImg, this.getX(), this.getY(), 40, 40, null);
        }
    }
}
