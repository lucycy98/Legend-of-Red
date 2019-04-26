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
    private ArrayList<String> instructionsMsg;
    private int currentMsg;
    private TileShape dialogueBox;
    private Boolean inTutorialLevel;
    private States currentState;
    private Boolean canMove;
    public enum States {TUTORIAL, INSTRUCTIONS, FINISHED};

    public TutorialLevel(MapHandler maps, PickUpItemHandler item) {
        currentState = States.TUTORIAL;
        inTutorialLevel = true;
        this.maps = maps;
        this.item = item;
        addTutorialMsg();
        addInstructionsMsg();
        createSprites();
        currentMsg = 0;
        canMove = false;
    }

    public Boolean canMove(){
        return canMove;
    }

    public void skip(){
        switch(currentState){
            case TUTORIAL:
                currentState = States.INSTRUCTIONS;
                currentMsg = 0;
                break;
            case INSTRUCTIONS:
                currentState = States.FINISHED;
                canMove = true;
                item.createItem(500, 200, Items.DAGGER);
                break;
            default:
                break;
        }
    }

    public void nextMessage() {
        switch(currentState){
            case TUTORIAL:
                if (currentMsg < tutorialMsg.size() - 1) {
                    currentMsg++;
                } else {
                    currentMsg = 0;
                   currentState = States.INSTRUCTIONS;
                }
                break;
            case INSTRUCTIONS:
                if (currentMsg < instructionsMsg.size() - 1) {
                    currentMsg++;
                    if (currentMsg == 6){ //Use your arrow keys to move the player towards the dagger to collect your first weapon.
                        item.createItem(500, 200, Items.DAGGER);
                        canMove = true;
                    }
                } else {
                    currentState = States.FINISHED;
                    canMove = true;
                }
                break;
            default:
                break;
        }
    }

    public void createSprites() {
        if (maps.getCurrentLevel() == 0) {
            mumSprite = new TileShape(100, 400, 100, 100, "mum.png", true);
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
        if (!inTutorialLevel || currentState == States.FINISHED) {
            return;
        }

        if (mumSprite != null){
            mumSprite.renderShape(win);
        }

        switch(currentState){
            case TUTORIAL:
                dialogueBox = new TileShape(60, 500, 900, 160, tutorialMsg.get(currentMsg), true);
                System.out.println("tut");
                break;
            case INSTRUCTIONS:
                dialogueBox = new TileShape(60, 500, 900, 160, instructionsMsg.get(currentMsg), true);
                System.out.println("inst");
                break;
            default: //finished
                dialogueBox = null;
                break;
        }

        if (dialogueBox != null){
            dialogueBox.renderShape(win);
        }
    }

    private void addTutorialMsg(){
        tutorialMsg = new ArrayList<>();
        tutorialMsg.add("dialogue/tut1.png");
        tutorialMsg.add("dialogue/tut2.png");
        tutorialMsg.add("dialogue/tut3.png");
    }

    private void addInstructionsMsg(){
        instructionsMsg = new ArrayList<>();
        instructionsMsg.add("dialogue/tut1.png");
        instructionsMsg.add("dialogue/tut2.png");
        instructionsMsg.add("dialogue/tut3.png");
        instructionsMsg.add("dialogue/tut4.png");
        instructionsMsg.add("dialogue/tut5.png");
        instructionsMsg.add("dialogue/tut1.png");
        instructionsMsg.add("dialogue/tut2.png");
        instructionsMsg.add("dialogue/tut3.png");
        instructionsMsg.add("dialogue/tut4.png");
        instructionsMsg.add("dialogue/tut5.png");
        instructionsMsg.add("dialogue/tut1.png");
    }

    @Override
    public void stopTimers() {

    }

    @Override
    public void startTimers() {

    }
}
