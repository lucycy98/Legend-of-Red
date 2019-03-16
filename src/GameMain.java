import Engine.GameEngine;

/**
 * where the game starts
 */
public class GameMain {

    private GameController control;
    private GameView view;
    private GameEngine game;
    public GameMain() {

        game = new GameEngine();
        view = new GameView(game);
        control = new GameController(game, view);
    }

    public static void main(String ... args) {
        new GameMain();
    }
}
