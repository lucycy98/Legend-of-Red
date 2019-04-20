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
    private TileShape mumIcon;
    private ArrayList<String> tutorialMsg;
    private int currentMsg;
    private boolean messageFinished;
    private TileShape dialogueBox;
    private Boolean inTutorialLevel;

    public TutorialLevel(MapHandler maps, PickUpItemHandler item) {
        inTutorialLevel = true;
        this.maps = maps;
        this.item = item;
        createSprites();
        tutorialMsg = new ArrayList<>();
        tutorialMsg.add("Mum: Little Red Riding Hood, could you pay your grandma a visit?");
        tutorialMsg.add("Mum: She hasn't seen you in ages and really misses you.");
        tutorialMsg.add("Mum: You'll have to go through the woods to see her.");
        tutorialMsg.add("Mum: Here is a dagger, so you can protect yourself.");
        tutorialMsg.add("Mum: You can walk towards it to pick it up.");
        tutorialMsg.add("Mum: Be careful of the wolves!");
        tutorialMsg.add("");
        currentMsg = 0;
        messageFinished = false;
        dialogueBox = new TileShape(60, 500, 900, 160, "dialogueBox.png", true);
    }

    public void createSprites() {
        if (maps.getCurrentLevel() == 0) {
            mumSprite = new TileShape(100, 400, 100, 100, "mum.png", true);
//            mumIcon = new TileShape(100, 530, 30, 30, "mum.png", true);
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
            win.setColor(Color.BLACK);
            win.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            if (!messageFinished) {
                dialogueBox.renderShape(win);
//            mumIcon.renderShape(win);
                if (mumSprite != null) {
                    mumSprite.renderShape(win);
                }
                win.drawString("Press space to continue", 700, 680);
            }
            win.setFont(new Font("TimesRoman", Font.BOLD, 20));
            win.drawString(tutorialMsg.get(currentMsg), 100, 600);
        }
    }

    public void nextMessage() {
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
