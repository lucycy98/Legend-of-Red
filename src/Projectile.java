import java.awt.*;

public class Projectile {

    private int xVelocity;
    private int yVelocity;

    private int currentXPos;
    private int currentYPos;

    public Projectile(int xVel, int yVel, int xPos, int yPos){
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
    }

    public void start(){

    }
}
