package graphics;

import pickup.Items;

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
    private int buffer = 40;

    private int small = 40;

    public TileShape() {

    }

    public TileShape(int x, int y, int width, int height, String img, Boolean r) {
        this.x = x;
        this.y = y + buffer;
        this.width = width;
        this.height = height;
        this.isRenderable = r;
        tileImg = ImageHandler.getImage(img);
    }

    public TileShape(int x, int y, int width, int height, Items item, Boolean r) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isRenderable = r;
        String image;
        System.out.println(item);
        switch (item) {
            case KEY:
                image = "key.png";
                break;
            case PROJECTILE:
                image = "bow.png";
                break;
            case CUPIDBOW:
                image = "cupid.png";
                break;
            case WOLFSKIN:
                image = "redCloak.png";
                break;
            case HEALTH:
                image = "heart.png";
                break;
            case DAGGER:
                image = "spear.png";
                break;
            default:
                image = "key.png";
        }
        tileImg = ImageHandler.getImage(image);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public Rectangle getSmallerBounds() {
        return new Rectangle(x+10, y+10, small, small);
    }

    public void changeImage(String img) {
        tileImg = ImageHandler.getImage(img);
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
