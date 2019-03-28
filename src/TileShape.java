import javax.swing.*;
import java.awt.Graphics;
import java.net.URL;

/**
 * this class represents a tile shape with an image e.g hedge, enemy etc
 */
public class TileShape extends Shape {

    private java.awt.Image tileImg = null;

    public TileShape(int x, int y, String img, Boolean r) {
        super(x, y);
        URL location = TileShape.class.getResource("/" + img);
        ImageIcon im = new ImageIcon(location);
        tileImg = im.getImage();
        setIsRenderable(r);
    }

    @Override
    public void renderShape(Graphics g) {
        if (isRenderable()) { //
//            g.drawimage();
        }


    }
}
