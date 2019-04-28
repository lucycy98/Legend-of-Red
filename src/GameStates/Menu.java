package GameStates;

import graphics.TileShape;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import static score.ScoreHandler.updateScoreFile;

public class Menu extends JPanel {

    private  int height;
    private int width;
    private JButton startButton;
    private JButton difficultyButton;
    private Gamestate option;
    private JLabel title;
    private JLabel imageLabel = null;
    private JButton highscore;
    private JButton saveButton;


    public Menu(int width, int height) {
        this.height = height;
        this.width = width;

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        try {
            makeComponents();
            addComponents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeComponents() throws IOException {

        title = new JLabel("MAIN MENU");
        title.setFont(new Font("Helvetica", Font.PLAIN, 50));
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        title.setForeground(Color.pink);

        BufferedImage image = null;
        try {
            System.out.println("classpath is: " + System.getProperty("java.class.path"));
            URL location = getClass().getResource("player.png");
            System.out.println(getClass().getClassLoader().getResource("player.png"));
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


        highscore = new JButton(icon){
            {
                setPreferredSize(new Dimension(300, 100));
                setMaximumSize(new Dimension(300, 100));
                setText("HIGH SCORE");
                setHorizontalTextPosition(JButton.CENTER);
                setAlignmentX(JButton.CENTER_ALIGNMENT);
            }
        };
        highscore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                option = Gamestate.HIGHSCORE;
            }
        });


        startButton = new JButton(icon){
            {
                setPreferredSize(new Dimension(300, 100));
                setMaximumSize(new Dimension(300, 100));
                setText("START");
                setHorizontalTextPosition(JButton.CENTER);
                setAlignmentX(JButton.CENTER_ALIGNMENT);
            }
        };
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                option = Gamestate.GAME;
            }
        });

        difficultyButton = new JButton(icon){
            {
                setPreferredSize(new Dimension(300, 100));
                setMaximumSize(new Dimension(300, 100));
                setText("DIFFICULTY");
                setHorizontalTextPosition(JButton.CENTER);
                setAlignmentX(JButton.CENTER_ALIGNMENT);
            }
        };
        difficultyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                option = Gamestate.DIFFICULTY;
            }
        });
        saveButton = new JButton(icon){
            {
                setPreferredSize(new Dimension(300, 100));
                setMaximumSize(new Dimension(300, 100));
                setText("SAVE");
                setHorizontalTextPosition(JButton.CENTER);
                setAlignmentX(JButton.CENTER_ALIGNMENT);
            }
        };
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateScoreFile();
                /**
                 * save button
                 */
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
        this.add(startButton);
        this.add(Box.createVerticalGlue());
        this.add(difficultyButton);
        this.add(Box.createVerticalGlue());
        this.add(highscore);
        this.add(Box.createVerticalGlue());
        this.add(saveButton);
        this.add(Box.createVerticalGlue());
    }


    public Gamestate getOption(){
        return option;
    }

}
