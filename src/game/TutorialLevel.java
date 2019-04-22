package game;

import maps.MapHandler;
import pickup.Items;
import pickup.PickUpItemHandler;
import graphics.TileShape;

import java.awt.*;
import java.util.ArrayList;

public class TutorialLevel implements Timers {

    private final MapHandler maps;
    private PickUpItemHandler item;
    private TileShape mumSprite;
    private ArrayList<String> tutorialMsg;
    private int currentMsg;
    private boolean messageFinished;
    private boolean tutFinished;
    private TileShape dialogueBox;
    private TileShape tutBox;
    private Boolean inTutorialLevel;

    public TutorialLevel(MapHandler maps, PickUpItemHandler item) {
        inTutorialLevel = true;
        this.maps = maps;
        this.item = item;
        createSprites();
        tutorialMsg = new ArrayList<>();
        tutorialMsg.add("dialogue/tut1.png");
        tutorialMsg.add("dialogue/tut2.png");
        tutorialMsg.add("dialogue/tut3.png");
        tutorialMsg.add("dialogue/tut4.png");
        tutorialMsg.add("dialogue/tut5.png");
        tutorialMsg.add("");
        currentMsg = 0;
        messageFinished = false;
        tutFinished = false;
        tutBox = new TileShape(100, 500, 900, 160, "dialogue/ins1.png", true);
    }

    public void createSprites() {
        if (maps.getCurrentLevel() == 0) {
            mumSprite = new TileShape(100, 400, 100, 100, "mum.png", true);
            item.createItem(500, 200, Items.DAGGER);
        }
    }

    /**
     * player enters portal.
     */
    public void beginGame() {
        mumSprite.setIsRenderable(false);
        inTutorialLevel = false;
    }

    /**
     * when player returns to tutorial level
     */
    public void backLevel() {
        mumSprite.setIsRenderable(true);
        inTutorialLevel = true;
    }

    public void paint(Graphics2D win) {
        if (inTutorialLevel) {
            dialogueBox = new TileShape(60, 500, 900, 160, tutorialMsg.get(currentMsg), true);
            if (!messageFinished) {
                dialogueBox.renderShape(win);
                if (mumSprite != null) {
                    mumSprite.renderShape(win);
                }
            }
            else {
                if (!tutFinished) {
                    tutBox.renderShape(win);
                }
            }
        }
    }

    public void nextMessage() {
        if (messageFinished){
            tutFinished = true;
        }
        if (currentMsg < tutorialMsg.size() - 1) {
            currentMsg++;
        } else {
            messageFinished = true;
        }
    }

    @Override
    public void stopTimers() {

    }

    @Override
    public void startTimers() {

    }
}
