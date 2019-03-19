import java.awt.*;

public class Bullet {

    private int xVelocity;
    private int yVelocity;

    private int currentXPos;
    private int currentYPos;

    public Bullet(int xVel, int yVel, int xPos, int yPos){
        xVelocity = xVel;
        yVelocity = yVel;
        currentXPos = xPos;
        currentYPos = yPos;
    }

    public void move(){
        currentXPos += xVelocity;
        currentYPos += yVelocity;
    }

    public void paint(Graphics2D win) {
        win.fillOval(currentXPos, currentYPos, 10, 10);
        // Main menu message
    }
}
