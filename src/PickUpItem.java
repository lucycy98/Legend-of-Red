import java.awt.*;
import java.util.ArrayList;

/**
 * this class represents a tile shape with an image e.g hedge, enemy etc
 */
public class PickUpItem extends TileShape {

    private EnemyHandler enemies;
    private ProjectileHandler projectileHandler;

    public PickUpItem(int xPos, int yPos, String image, EnemyHandler enemies) {
        super(xPos, yPos, 40, 40, image, true);
        this.enemies = enemies;
    }


}
