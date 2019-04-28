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
    private String spaceContinue = "Press SPACE BAR to continue.";
    private String skipContinue = "Press ENTER to skip.";
    public enum States {TUTORIAL, INSTRUCTIONS, FINISHED};
    private int width;
    private boolean isBeginning;

    public TutorialLevel(MapHandler maps, PickUpItemHandler item, int width, Boolean isBeginning) {
        this.isBeginning = isBeginning;
        currentState = States.TUTORIAL;
        inTutorialLevel = true;
        this.maps = maps;
        this.width = width;
        this.item = item;
        if (isBeginning){
            addTutorialMsgBeginning();
        } else {
            addTutorialMsgEnd();
        }
        addInstructionsMsg();
        createSprites();
        currentMsg = 0;
        canMove = false;
        dialogueBox = new TileShape(60, 500, 900, 160, "dialogue/dialogueBox.png", true);
    }

    public Boolean canMove(){
        return canMove;
    }

    public void skip(){
        switch(currentState){
            case TUTORIAL:
                break;
            case INSTRUCTIONS:
                currentState = States.FINISHED;
                canMove = true;
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

                    if (currentMsg == 4 && isBeginning){ //Here is a dagger to protect yourself
                        item.createItem(500, 200, Items.DAGGER);
                    }

                } else {
                    currentMsg = 0;

                    if (isBeginning){
                        currentState = States.INSTRUCTIONS;
                    } else {
                        currentState = States.FINISHED;
                    }

                }
                break;
            case INSTRUCTIONS:
                if (currentMsg < instructionsMsg.size() - 1) {
                    currentMsg++;
                    if (currentMsg == 7){ //Use your arrow keys to move the player towards the dagger to collect your first weapon.
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

    private TileShape getPointer(){
        int x = -1; int y = -10;
        int ratio = width/7;
        switch (currentMsg){
            case 1://score
                x = ratio + 50;
                break;
            case 2: //level
                x = 2*ratio + 50;
                break;
            case 3://health
                x = 3*ratio + 50;
                break;
            case 4: //time
                x = 5*ratio + 150;
                break;
            case 5: //weapon
                x = 4*ratio + 110;
                break;
            case 6: //weapon
                x = 4*ratio + 110;
                break;
                default:
                    break;
        }
        if (x > -1){
            return new TileShape(x, y, 40, 50, "dialogue/pointer.png", true);
        }
        return null;
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

        win.setColor(Color.BLACK);
        win.setFont(new Font("TimesRoman", Font.PLAIN, 18));

        if (dialogueBox != null){
            dialogueBox.renderShape(win);
        }

        if (mumSprite != null){
            mumSprite.renderShape(win);
            win.drawString(spaceContinue, 650, 680);
            if (currentState == States.INSTRUCTIONS){
                win.drawString(skipContinue, 720, 580);
            }
        }

        win.setFont(new Font("TimesRoman", Font.BOLD, 18));
        switch(currentState){
            case TUTORIAL:
                win.drawString(tutorialMsg.get(currentMsg), 100, 610);
                //dialogueBox = new TileShape(60, 500, 900, 160, tutorialMsg.get(currentMsg), true);
                System.out.println("tut");
                break;
            case INSTRUCTIONS:
                win.drawString(instructionsMsg.get(currentMsg), 100, 610);
                TileShape pointer = getPointer();
                if (pointer != null){
                    pointer.renderShape(win);
                }
                //dialogueBox = new TileShape(60, 500, 900, 160, instructionsMsg.get(currentMsg), true);
                System.out.println("inst");
                break;
            default: //finished
                //dialogueBox = null;
                break;
        }
    }

    private void addTutorialMsgBeginning(){
        tutorialMsg = new ArrayList<>();
        tutorialMsg.add("Little Red Riding Hood, I have some bad news.");
        tutorialMsg.add("Grandma has just been kidnapped by the big bad wolf...");
        tutorialMsg.add("And you’re the only one who can save her.");
        tutorialMsg.add("You’ll have to go through the woods to get to Grandma's house.");
        tutorialMsg.add("Here is a dagger to protect yourself.");
        tutorialMsg.add("Be careful of the wolves, especially the big bad one.");
        tutorialMsg.add("Good luck, my dear.");
    }

    private void addTutorialMsgEnd(){
        tutorialMsg = new ArrayList<>();
        tutorialMsg.add("Help!");
        tutorialMsg.add("Little Red Riding Hood, HELP!");
        tutorialMsg.add("The big bad wolf has kidnapped me");
        tutorialMsg.add("You must defeat him to set me free");
        tutorialMsg.add("Be ware:");
        tutorialMsg.add("He is BIG...");
        tutorialMsg.add("...BAD...");
        tutorialMsg.add("...and he shoots cannon balls at you");
    }


    private void addInstructionsMsg(){
        instructionsMsg = new ArrayList<>();
        instructionsMsg.add("The following is a tutorial for the game.");
        instructionsMsg.add("Your score will be displayed here. The more wolves you kill, the higher your score.");
        instructionsMsg.add("Your current level will be displayed here.");
        instructionsMsg.add("Your health will be displayed here. Try keep it above 0.");
        instructionsMsg.add("This is the time remaining for you to complete your mission.");
        instructionsMsg.add("Your current weapon will be displayed here.");
        instructionsMsg.add("When you upgrade your weapon, press (s) to switch between weapons.");
        instructionsMsg.add("Attack wolves with the SPACE BAR.");
        instructionsMsg.add("Use ARROW KEYS to move towards the dagger to collect your first weapon.");
        instructionsMsg.add("You will be rewarded with more power ups during the game.");
        instructionsMsg.add("Move towards the portal to enter the next level.");
        instructionsMsg.add("Take the other portal to return back to the previous level.");
        instructionsMsg.add("Good luck!");
    }

    @Override
    public void stopTimers() {

    }

    @Override
    public void startTimers() {

    }
}
