package GameStates;

import score.Score;
import sound.SoundHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LosePanel extends JPanel {

    private JButton startButton;
    private Gamestate option;
    private JLabel title;
    private Score score;
    private JLabel imageLabel = null;
    private JButton menuButton;
    private SoundHandler sound;


    public LosePanel(Score score, SoundHandler sound) {

        this.score = score;
        this.sound = sound;

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        try {
            makeComponents();
            addComponents();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sound.play("loseGame.wav");
    }

    private void makeComponents() throws IOException {
        //title
        title = new JLabel("MISSION UNSUCCESSFUL");
        title.setFont(new Font("Helvetica", Font.PLAIN, 50));
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        title.setForeground(Color.pink);

        //image
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("wolf.png"));
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

        //buttons
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("button.png"));
        startButton = new JButton(icon);
        buttonSettings(startButton, "START");
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                option = Gamestate.GAME;
            }
        });

        menuButton = new JButton(icon);
        buttonSettings(menuButton, "MAIN MENU");
        menuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                option = Gamestate.MENU;
            }
        });

    }

    private void buttonSettings(JButton button, String text){
        button.setPreferredSize(new Dimension(400, 80));
        button.setMaximumSize(new Dimension(400, 80));
        button.setText(text);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setAlignmentX(JButton.CENTER_ALIGNMENT);
    }

    private void addComponents() {
        this.add(Box.createVerticalGlue());
        this.add(title);
        this.add(Box.createVerticalGlue());
        if (imageLabel != null){
            this.add(imageLabel);
            this.add(Box.createVerticalGlue());
        }
        this.add(startButton);
        this.add(Box.createVerticalGlue());
        this.add(menuButton);
        this.add(Box.createVerticalGlue());
    }

    public Gamestate getOption(){
        return option;
    }

}
