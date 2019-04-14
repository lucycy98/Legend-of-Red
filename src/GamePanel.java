import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener {

    JPanel panel = this;
    ProjectileHandler projectiles;
    Protagonist player;
    int tileSize = 4;
    EnemyHandler enemies;
    MapHandler maps;
    WeaponHandler weapons;

    // gameScreen Constructor
    public GamePanel() {
        maps = new MapHandler();
        //projectiles = new ProjectileHandler(maps);
        enemies = new EnemyHandler(4, maps, projectiles);
        player = new Protagonist(40, 40, 40, 40, "player.jpg", tileSize, maps, projectiles, enemies);
        enemies.addPlayer(player);
        weapons = new WeaponHandler(maps, projectiles, player, enemies);
        //dagger = new Dagger(player, 40, 40, "dagger.jpg", false);
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
        repaint();
    }

    public static void main(String[] args) {

        JFrame mainFrame = new JFrame("Game 1.1");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBounds(10, 10, 1280, 960);
        mainFrame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);

        GamePanel paintPanel = new GamePanel();

        mainFrame.add(mainPanel, BorderLayout.PAGE_START);
        mainFrame.add(paintPanel, BorderLayout.CENTER);
        mainFrame.setResizable(true);

        mainFrame.setVisible(true);
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
