import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener {

    JPanel panel = this;
    ProjectileHandler projectiles;
    Protagonist player;
    Map map;
    Map map1;
    Map map2;
    int tileSize = 4;
    EnemyHandler enemies;
    MapHandler maps;

    // gameScreen Constructor
    public GamePanel() {

        maps = new MapHandler();
        player = new Protagonist(40, 40, "player.jpg", tileSize, maps, projectiles);
        projectiles = new ProjectileHandler(maps);
        enemies = new EnemyHandler(4, maps, projectiles);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles.getProjectiles();
    }

    @Override
    public void paint(Graphics gr) {
        // Clears window
        super.paint(gr);
        Graphics2D window = (Graphics2D) gr;
        maps.paint(gr);
        player.paint(window);
        enemies.paint(window);
        projectiles.paint(window);
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
                projectiles.start();
                projectiles.shoot(player.getDir(), player.getX(), player.getY(), tileSize);
                System.out.println("shoot");
                break;
            default:
                player.keyPressed(evt);
        }

    }
}
