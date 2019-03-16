import Engine.GameEngine;

/**
 * this class responds to user inputs such as keys pressed
 */

public class GameController {

    private GameEngine game;
    private GameView view;

    public GameController(GameEngine game, GameView view){
        this.game = game;
        this.view = view;

    }


}
