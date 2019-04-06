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
    int tileSize = 4;
    EnemyHandler enemies;

    // Game timer for repaint
    Timer paint_timer;
    static int paint_updateInterval = 900;

    // gameScreen Constructor
    public GamePanel() {
        map = new Map();
        player = new Protagonist(40, 40, "player.jpg", tileSize, map.getObstacles(), this);

        projectiles = new ProjectileHandler(map.getObstacles());
        enemies = new EnemyHandler(1, map, tileSize, this);


        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();
       // paint_timer.start();
    }

    public ArrayList<Projectile> getProjectiles(){
        return projectiles.getProjectiles();
    }

    @Override
    public void paint(Graphics gr) {
        // Clears window
        super.paint(gr);
        Graphics2D window = (Graphics2D) gr;
        map.paint(window);
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
