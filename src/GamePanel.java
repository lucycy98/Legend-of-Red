import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener {

    JPanel panel = this;
    ProjectileHandler projectiles;
    Protagonist player;
    int speed = 8;
    int tileSize = 60;
    EnemyHandler enemies;
    MapHandler maps;
    WeaponHandler weapons;
    //PickUpItemHandler pickup;

    // gameScreen Constructor
    public GamePanel() {
        maps = new MapHandler();
        //projectiles = new ProjectileHandler(maps);
        //pickup = new PickUpItemHandler(maps);
        enemies = new EnemyHandler(tileSize, maps, projectiles);
        player = new Protagonist(tileSize, tileSize, tileSize, tileSize, "player.jpg", tileSize, maps, projectiles, enemies);
        enemies.addPlayer(player);
        //pickup.addPlayer(player);
        weapons = new WeaponHandler(maps, projectiles, player, enemies);
        enemies.addWeaponHandler(weapons);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();
    }

    @Override
    public void paint(Graphics gr) {
        // Clears window
        super.paint(gr);
        Graphics2D window = (Graphics2D) gr;
        maps.paint(gr);
        player.paint(window);
        enemies.paint(window);
        weapons.paint(window);
        //pickup.paint(window);
        repaint();
    }

    public static void main(String[] args) {

        JFrame gameWindow = new JFrame("Game 1.1");
        gameWindow.setSize(1030, 790);
        //mainFrame.setLayout(new BorderLayout());
        gameWindow.setVisible(true);
        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel playScreen;
        playScreen = new GamePanel();
        playScreen.setBackground(Color.WHITE);
        gameWindow.add(playScreen);
        gameWindow.validate();
        playScreen.requestFocus();

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    @Override
    public void keyPressed(KeyEvent evt) {

        switch (evt.getKeyCode()) {

            case KeyEvent.VK_SPACE:
                weapons.attack();
                break;
            case KeyEvent.VK_S:
                weapons.changeWeapon();
                break;
            default:
                player.keyPressed(evt);
        }

    }
}
