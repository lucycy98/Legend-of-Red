import java.awt.*;

public class PickUpItem extends TileShape{

    private Protagonist player;
    int x;
    int y;
    Items item;
    MapHandler map;
    private WeaponHandler weapon;

    public PickUpItem(Protagonist player, int x, int y, Items item, MapHandler map, WeaponHandler weapon) {
        super(x,y, 40, 40, item,true);
        this.player = player;
        this.item = item;
        this.map = map;
        this.weapon = weapon;
    }

    public PickUpItem(Protagonist player, int x, int y, Items item, MapHandler map) {
        super(x,y, 40, 40, item,true);
        this.player = player;
        this.item = item;
        this.map = map;
    }

    public void collectItem(){
        switch(item){
            case KEY:
                map.addPortal();
                break;
            case HEALTH:
                break;
            case DAGGER:
                break;
            case WOLFSKIN:
                break;
            default:
                weapon.addWeapon(item);
                break;
        }
        setIsRenderable(false);
    }

    public void paint(Graphics2D win){
        this.renderShape(win);
    }

}