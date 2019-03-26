import java.awt.Color;
import java.awt.Graphics;

public class Shape {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color c;
    private Boolean isRenderable;

    public Shape(int x, int y, int w, int h, Color c, boolean render) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.c = c;
        this.isRenderable = render;
    }

    public Shape(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setIsRenderable(Boolean r){
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

    public Boolean isRenderable(){
        return this.isRenderable;
    }

    public void setX(int value) {
        this.x = value;
    }

    public void setY(int value) {
        this.y = value;
    }

    public void setHeight(int value) {
        this.height = value;
    }

    public void setWidth(int value) {
        this.width = value;
    }

    //method called when ready to be repainted.
    public void renderShape(Graphics g) {
        if (this.c == null) {
            this.c = Color.BLUE;
        }

        g.setColor(this.c);
        g.fillRect(this.x, this.y, this.width, this.height);
    }
}