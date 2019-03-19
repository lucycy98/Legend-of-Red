import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Test {

    public static void main(String[] args) throws IOException, InterruptedException {
        new Test();
    }

    public Test() {
        EventQueue.invokeLater(new Runnable()   {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new RoomWars());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class RoomWars extends JPanel implements ActionListener {
        //public JPanel pane;

        int x = 0, y = 0, velx = 0, vely = 0;
        Timer t = new Timer(5, this);

        public RoomWars() {
            t.start();

            InputMap im = getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
            ActionMap am = getActionMap();

            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "up.pressed");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "up.released");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "down.pressed");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "down.released");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left.pressed");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "left.released");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right.pressed");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "right.released");

            am.put("up.pressed", new MoveAction(-1, 0));
            am.put("up.released", new MoveAction(0, 0));
            am.put("down.pressed", new MoveAction(1, 0));
            am.put("down.released", new MoveAction(0, 0));
            am.put("left.pressed", new MoveAction(0, -1));
            am.put("left.released", new MoveAction(0, 0));
            am.put("right.pressed", new MoveAction(0, 1));
            am.put("right.released", new MoveAction(0, 0));
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Color mypurple = new Color(34, 0, 56);
            g.setColor(mypurple);
            g.fillRect(x, y, 30, 30);
        }

        public class MoveAction extends AbstractAction {
            private int yDelta;
            private int xDelta;

            public MoveAction(int yDelta, int xDelta) {
                this.yDelta = yDelta;
                this.xDelta = xDelta;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                vely = yDelta;
                velx = xDelta;
            }
        }

        public void actionPerformed(ActionEvent e) {
            repaint();
            x += velx;
            y += vely;
        }

    }
}