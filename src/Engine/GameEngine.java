package Engine;

import Menu.MainMenu;

/**
 *  This class represents the Game: Legend of Red
 */
public class GameEngine {
    private final MainMenu scene;

    public GameEngine() {
        scene = new MainMenu(); //starting scene

    }

    public void quit() {
        System.exit(0);
    }

    /**
     * changes the scene
     * @param sceneName
     * @return
     */
    public GameScene initScene(String sceneName) {
       return null;
    }

}
