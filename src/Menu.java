import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu extends JPanel {

    int height;
    int width;
    JButton startButton;
    JButton quitButton;
    JPanel panel = this;
    Gamestate option;


    public Menu(int width, int height) {

        this.height = height;
        this.width = width;

        try {
            makeButtons();
            addButtons();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeButtons() throws IOException {
        ImageIcon startIcon = new ImageIcon(getClass().getResource("button.png"));
        startButton = new JButton(startIcon){
            {
                setPreferredSize(new Dimension(400, 100));
                setText("START");
                setHorizontalTextPosition(JButton.CENTER);
            }
        };
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                option = Gamestate.GAME;
            }
        });


        ImageIcon quitIcon = new ImageIcon(getClass().getResource("button.png"));
        quitButton = new JButton(quitIcon){
            {
                setPreferredSize(new Dimension(400, 100));
                setText("QUIT");
                setHorizontalTextPosition(JButton.CENTER);
            }
        };
        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                option = Gamestate.QUIT;
            }
        });
    }

    private void addButtons() {
        Box box = Box.createVerticalBox();
        box.add(Box.createVerticalStrut(200));
        box.add(startButton);
        box.add(Box.createVerticalStrut(100));
        box.add(quitButton);
        this.add(box, BorderLayout.CENTER);
    }

    public Gamestate getOption(){
        return option;
    }

    public static void main(String[] args) {

        JFrame gameWindow = new JFrame("Game 1.1");
        gameWindow.setSize(1030, 790);
        gameWindow.setVisible(true);
        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Menu menu;
        menu = new Menu(2,2);
        menu.setBackground(Color.BLACK);
        gameWindow.add(menu);
        gameWindow.validate();
        menu.requestFocus();

    }

}
