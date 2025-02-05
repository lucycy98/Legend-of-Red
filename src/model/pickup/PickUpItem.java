package model.pickup;

import model.attacks.WeaponHandler;
import model.being.Protagonist;
import model.TileShape;
import model.maps.MapHandler;

import java.awt.*;

/**
 * this class represents a pick up item icon when in the place of the enemy killed
 */
public class PickUpItem extends TileShape {

    private Protagonist player;
    private Items item;
    private MapHandler map;
    private WeaponHandler weapon;

    public PickUpItem(Protagonist player, int x, int y, Items item, MapHandler map, WeaponHandler weapon) {
        super(x, y, 60, 60, item, true);
        this.player = player;
        this.item = item;
        this.map = map;
        this.weapon = weapon;
    }

    public PickUpItem(Protagonist player, int x, int y, Items item, MapHandler map) {
        super(x, y, 60, 60, item, true);
        this.player = player;
        this.item = item;
        this.map = map;
    }

    public Items getItem() {
        return item;
    }

    public void collectItem() {
        switch (item) {
            case KEY:
                map.addPortal();
                break;
            case HEALTH:
                player.healthUp();
                break;
            case WOLFSKIN:
                player.setInvincible(100);
                break;
            case DAGGER:
                weapon.addWeapon(item);
                map.addPortal();
                break;
            default:
                weapon.addWeapon(item);
                break;
        }
        setIsRenderable(false);
    }

    public void paint(Graphics2D win) {
        this.renderShape(win);
    }

}