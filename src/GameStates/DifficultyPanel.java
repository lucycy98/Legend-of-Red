package GameStates;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class DifficultyPanel extends JPanel {

    private  int height;
    private int width;
    private JButton easy;
    private JButton medium;
    private JButton hard;
    private Gamestate option;
    private JLabel title;
    private JLabel imageLabel = null;
    private JButton highscore;
    private int diff;


    public DifficultyPanel(int width, int height, int diff) {
        this.height = height;
        this.width = width;
        this.diff = diff;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        try {
            makeComponents();
            addComponents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeComponents() throws IOException {

        title = new JLabel("DIFFICULTY MENU");
        title.setFont(new Font("Helvetica", Font.PLAIN, 50));
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        title.setForeground(Color.pink);

        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        ImageIcon imageIcon = null;
        if (image != null){
            Image dimg = image.getScaledInstance(120,120, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(dimg);
        }

        if (imageIcon != null){
            imageLabel = new JLabel(imageIcon);
            imageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        }

        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("button.png"));


        easy = new JButton(icon){
            {
                setPreferredSize(new Dimension(300, 100));
                setMaximumSize(new Dimension(300, 100));
                setText("EASY");
                setHorizontalTextPosition(JButton.CENTER);
                setAlignmentX(JButton.CENTER_ALIGNMENT);
            }
        };
        easy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                diff = 1;
                option = Gamestate.MENU;
            }
        });


        medium = new JButton(icon){
            {
                setPreferredSize(new Dimension(300, 100));
                setMaximumSize(new Dimension(300, 100));
                setText("MEDIUM");
                setHorizontalTextPosition(JButton.CENTER);
                setAlignmentX(JButton.CENTER_ALIGNMENT);
            }
        };
        medium.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                diff = 2;
                option = Gamestate.MENU;
            }
        });

        hard = new JButton(icon){
            {
                setPreferredSize(new Dimension(300, 100));
                setMaximumSize(new Dimension(300, 100));
                setText("HARD");
                setHorizontalTextPosition(JButton.CENTER);
                setAlignmentX(JButton.CENTER_ALIGNMENT);
            }
        };
        hard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                diff = 3;
                option = Gamestate.MENU;
            }
        });
    }

    private void addComponents() {
        this.add(Box.createVerticalGlue());
        this.add(title);
        this.add(Box.createVerticalGlue());
        if (imageLabel != null){
            this.add(imageLabel);
        }
        this.add(Box.createVerticalGlue());
        this.add(easy);
        this.add(Box.createVerticalGlue());
        this.add(medium);
        this.add(Box.createVerticalGlue());
        this.add(hard);
        this.add(Box.createVerticalGlue());
    }

    public int getDiff(){
        return diff;
    }

    public Gamestate getOption(){
        return option;
    }

}
