import java.awt.*;

/**
 * this class represents a tile shape with an image e.g hedge, enemy etc
 */
public class TileShape {

    private Image tileImg = null;
    private int x;
    private int y;
    private int width;
    private int height;
    private Boolean isRenderable;

    public TileShape(int x, int y, int width, int height, String img, Boolean r) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isRenderable = r;
        tileImg = ImageHandler.getImage(img);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void setIsRenderable(Boolean r) {
        this.isRenderable = r;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Boolean isRenderable() {
        return this.isRenderable;
    }

    public void setX(int value) {
        this.x = value;
    }

    public void setY(int value) {
        this.y = value;
    }

    public void renderShape(Graphics g) {
        if (isRenderable()) { //
            g.drawImage(tileImg, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
        }
    }
}
