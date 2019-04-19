import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class TutorialLevel implements Timers {

    private final MapHandler maps;
    private PickUpItemHandler item;
    private TileShape mumSprite;

    public TutorialLevel(MapHandler maps, PickUpItemHandler item) {
        this.maps = maps;
        this.item = item;
        createSprites();
    }

    public void createSprites(){
        if (maps.getCurrentLevel() == 0){
            mumSprite = new TileShape(400,200, 60, 60, "mum.png", true);
            item.createItem(500, 200, Items.DAGGER);
        }
    }

    /**
     * player enters portal.
     */
    public void beginGame(){
        mumSprite.setIsRenderable(false);
    }

    /**
     * when player returns to tutorial level
     */
    public void backLevel(){
        mumSprite.setIsRenderable(true);
    }

    public void paint(Graphics2D win){
        if (mumSprite != null){
            mumSprite.renderShape(win);
        }
    }

    @Override
    public void stopTimers() {

    }

    @Override
    public void startTimers() {

    }
}
