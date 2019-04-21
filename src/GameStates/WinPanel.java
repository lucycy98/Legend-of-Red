package GameStates;

import game.Score;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class WinPanel extends JPanel {

    int height;
    int width;
    JButton startButton;
    JButton quitButton;
    Gamestate option;
    JLabel title;
    JLabel scoreLabel;
    Score score;
    JLabel imageLabel = null;


    public WinPanel(Score score) {

        this.score = score;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        try {
            makeComponents();
            addComponents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeComponents() throws IOException {

        title = new JLabel("YOU SAVED GRANDMA!");
        title.setFont(new Font("Helvetica", Font.PLAIN, 40));
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        title.setForeground(Color.pink);

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

        ImageIcon startIcon = new ImageIcon(getClass().getResource("../button.png"));
        startButton = new JButton(startIcon){
            {
                setPreferredSize(new Dimension(400, 80));
                setMaximumSize(new Dimension(400, 80));
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

        ImageIcon quitIcon = new ImageIcon(getClass().getResource("../button.png"));
        quitButton = new JButton(quitIcon){
            {
                setPreferredSize(new Dimension(400, 80));
                setMaximumSize(new Dimension(400, 80));
                setText("QUIT");
                setHorizontalTextPosition(JButton.CENTER);
                setAlignmentX(JButton.CENTER_ALIGNMENT);
            }
        };
        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                option = Gamestate.QUIT;
            }
        });
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
        this.add(quitButton);
        this.add(Box.createVerticalGlue());
    }

    public Gamestate getOption(){
        return option;
    }

}
