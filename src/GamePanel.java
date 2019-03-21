import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements KeyListener{

    JPanel panel = this;

    ProjectileHandler projectiles;
    Protagonist player;

    // Game timer for repaint
    Timer paint_timer, player_timer;
    static int paint_updateInterval = 300;

    // gameScreen Constructor
    public GamePanel() {

        player = new Protagonist(10, 10);
        projectiles = new ProjectileHandler();

        this.paint_timer = new Timer(1000 / paint_updateInterval, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        }));

        //player_timer.start();
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();
        paint_timer.start();
    }

    @Override
    public void paint(Graphics gr) {
        // Clears window
        super.paint(gr);
        Graphics2D window = (Graphics2D) gr;
        player.paint(window);
        projectiles.paint(window);
    }

    public static void main(String[] args) {

        JFrame mainFrame = new JFrame("Game 1.1");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBounds(10, 10, 400, 400);
        mainFrame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);

        GamePanel paintPanel = new GamePanel();

        mainFrame.add(mainPanel, BorderLayout.PAGE_START);
        mainFrame.add(paintPanel, BorderLayout.CENTER);

        mainFrame.setVisible(true);

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                player.setReleased(Protagonist.Direction.LEFT);
                System.out.println("left rel");
                break;
            case KeyEvent.VK_RIGHT:
                player.setReleased(Protagonist.Direction.RIGHT);
                System.out.println("right rel");
                break;
            case KeyEvent.VK_UP:
                player.setReleased(Protagonist.Direction.UP);
                System.out.println("up rel");
                break;
            case KeyEvent.VK_DOWN:
                player.setReleased(Protagonist.Direction.DOWN);
                System.out.println("down rel");
                break;
        }

    }

    @Override
    public void keyPressed(KeyEvent evt) {

        switch (evt.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                player.setDirection(Protagonist.Direction.LEFT);
                System.out.println("left");
                break;
            case KeyEvent.VK_RIGHT:
                player.setDirection(Protagonist.Direction.RIGHT);
                System.out.println("right");
                break;
            case KeyEvent.VK_UP:
                player.setDirection(Protagonist.Direction.UP);
                System.out.println("up");
                break;
            case KeyEvent.VK_DOWN:
                player.setDirection(Protagonist.Direction.DOWN);
                System.out.println("down");
                break;
            case KeyEvent.VK_SPACE:
                projectiles.start();
                projectiles.shoot(2, 2, player.getX(), player.getY());
                System.out.println("shoot");
        }

    }
}
