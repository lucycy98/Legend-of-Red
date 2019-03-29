import java.awt.*;

/**
 * this class represents a tile shape with an image e.g hedge, enemy etc
 */
public class TileShape extends Shape {

    private Image tileImg = null;

    public TileShape(int x, int y, String img, Boolean r) {
        super(x, y, 40, 40);
        tileImg = ImageHandler.getImage(img);
        setIsRenderable(r);
    }

    public void renderShape(Graphics g) {
        if (isRenderable()) { //
            g.drawImage(tileImg, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
        }
    }
}
