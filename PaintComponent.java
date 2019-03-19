import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import javax.swing.*;


public class PaintComponent extends JPanel implements KeyListener, ActionListener {

    Timer timer;


    enum Direction {
        UP, DOWN, LEFT, RIGHT;
    }

    Direction currentDirection = Direction.UP; //todo: set default value
    int dx = 200;
    int dy = 300;
    int mx = 200;
    int my = 300;
    public Rectangle2D rec = new Rectangle2D.Double(dx, dy, 30, 10);
    public Rectangle2D bullet = new Rectangle2D.Double(mx, my, 5, 5);

    public PaintComponent() {
        this.addKeyListener(this);
        this.setBackground(Color.white);
        this.setFocusable(true);

    }

    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        //System.out.println("Hello");
        repaint();
    }

    public void shoot(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            int current;
            switch (currentDirection) {
                case LEFT:
                    current = dx;
                    mx = current - 2;
                    break;
                case RIGHT:
                    current = dx;
                    mx = current + 2;
                    break;
                case UP:
                    current = dy;
                    my = current - 2;
                    break;
                case DOWN:
                    current = dy;
                    my = current + 2;
                    break;


            }
            repaint();
        }
    }

    public void moveRec(KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                currentDirection = Direction.LEFT;
                System.out.println("left");
                dx -= 2;
                break;
            case KeyEvent.VK_RIGHT:
                currentDirection = Direction.RIGHT;
                System.out.println("right");
                dx += 2;
                break;
            case KeyEvent.VK_UP:
                currentDirection = Direction.UP;
                System.out.println("up");
                dy -= 2;
                break;
            case KeyEvent.VK_DOWN:
                currentDirection = Direction.DOWN;
                System.out.println("down");
                dy += 2;
                break;
        }
        rec.setRect(dx, dy, 30, 10);
        repaint();
    }


    @Override
    protected void paintComponent(Graphics grqphics) {
        super.paintComponent(grqphics);
        Graphics2D gr = (Graphics2D) grqphics;
        gr.draw(rec);
        gr.draw(bullet);
    }


    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("2");
        shoot(e);
    }


    @Override
    public void keyPressed(KeyEvent e) {
        moveRec(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {

        JFrame mainFrame = new JFrame("Game 1.1");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBounds(10, 10, 400, 400);
        mainFrame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);

        PaintComponent paintPanel = new PaintComponent();

        mainFrame.add(mainPanel, BorderLayout.PAGE_START);
        mainFrame.add(paintPanel, BorderLayout.CENTER);

        mainFrame.setVisible(true);

    }
}