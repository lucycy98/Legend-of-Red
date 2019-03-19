public class Protagonist {

    // co-ordinates of player
    int x;
    int y;
    int width; int height;

    public Protagonist(int width, int height) {
        // call main constructor with default values
        this(30, 30, width, height);

    }

    public Protagonist(int xPos, int yPos, int w, int h) {
        x = xPos;
        y = yPos;
        width = w;
        height = h;
    }

}
