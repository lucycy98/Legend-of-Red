import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Dagger extends TileShape implements Weapon {

    private int currentXPos;
    private int currentYPos;
    private Direction direction;
    private Protagonist player;
    private EnemyHandler enemies;
    private Items item = Items.DAGGER;

    public Dagger(Protagonist player, int x, int y, String img, Boolean r, EnemyHandler enemies) {
        super(x, y, 60, 60, img, false);
        this.player = player;
        this.enemies = enemies;
    }

    public Rectangle getBounds() {
        return new Rectangle(currentXPos, currentYPos, 40, 40);
    }

    @Override
    public Items getItems() {
        return item;
    }

    @Override
    public void attack() {
        this.setIsRenderable(true);
        showDagger();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Your database code here
                setIsRenderable(false);

            }
        }, 100);
    }

    @Override
    public void enemyRangeAttack(Enemy enemy) {

    }

    @Override
    public void checkEnemyCollision(){

    }

    @Override
    public void paint(Graphics2D win) {
        this.renderShape(win);
        showDagger();
    }

    @Override
    public void checkCollision() {
        if (this.isRenderable()) {
            Rectangle daggerRec = this.getBounds();
            ArrayList<Enemy> es = enemies.getCurrentEnemies();
            for (int i = 0; i < es.size(); i++) {
                Rectangle enemyRec = es.get(i).getBounds();
                if (daggerRec.intersects(enemyRec)) {
                    enemies.damageEnemy(es.get(i));
                }
            }
        }
    }

    private void showDagger() {
        direction = player.getDir();
        int xPos = player.getX();
        int yPos = player.getY();
        switch (direction) {
            case NORTH:
                this.changeImage("daggerNorth.png");
                currentXPos = xPos;
                currentYPos = yPos - 60;
                break;
            case SOUTH:
                this.changeImage("daggerSouth.png");
                currentXPos = xPos;
                currentYPos = yPos + 60;
                break;
            case EAST:
                this.changeImage("daggerEast.png");
                currentXPos = xPos + 60;
                currentYPos = yPos;
                break;
            case WEST:
                this.changeImage("daggerWest.png");
                currentXPos = xPos - 60;
                currentYPos = yPos;
                break;
            default:
                currentXPos = xPos;
                currentYPos = yPos - 60;
        }
        this.setX(currentXPos);
        this.setY(currentYPos);

    }

}