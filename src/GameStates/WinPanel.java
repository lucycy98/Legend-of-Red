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

public class WinPanel extends JPanel {

    JButton startButton;
    JButton quitButton;
    Gamestate option;
    JLabel title;
    JLabel scoreLabel;
    Score score;
    JLabel imageLabel = null;
    JButton menuButton;
    SoundHandler sound;


    public WinPanel(Score score, SoundHandler sound) {

        this.score = score;
        this.sound = sound;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        try {
            makeComponents();
            addComponents();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sound.play("winGame.wav");
    }

    private void makeComponents() throws IOException {

        title = new JLabel("YOU SAVED GRANDMA!");
        title.setFont(new Font("Helvetica", Font.PLAIN, 40));
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        title.setForeground(Color.pink);

        //current score
        String scoreval;
        if (score.getScore() < 0){
            scoreval = "";
        } else {
            scoreval = Integer.toString(score.getScore());
        }

        scoreLabel = new JLabel("Score: " + scoreval);
        scoreLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        scoreLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        scoreLabel.setForeground(Color.pink);

        //image
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource("../grandma.png"));
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
        ImageIcon icon = new ImageIcon(getClass().getResource("../button.png"));
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

        quitButton = new JButton(icon);
        buttonSettings(quitButton, "QUIT");
        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                option = Gamestate.QUIT;
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
        this.add(scoreLabel);
        this.add(Box.createVerticalGlue());
        if (imageLabel != null){
            this.add(imageLabel);
            this.add(Box.createVerticalGlue());
        }
        this.add(startButton);
        this.add(Box.createVerticalGlue());
        this.add(menuButton);
        this.add(Box.createVerticalGlue());
        this.add(quitButton);
        this.add(Box.createVerticalGlue());
    }

    public Gamestate getOption(){
        return option;
    }

}
