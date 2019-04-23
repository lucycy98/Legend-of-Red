package GameStates;

import score.ScoreHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HighScorePanel extends JPanel {

    private int height;
    private int width;
    private JButton menuButton;
    private Gamestate option;
    private JLabel title;
    private JLabel imageLabel = null;
    private JButton highscore;
    private ScoreHandler scoreHandler;
    private JLabel score1;
    private JLabel score2;
    private JLabel score3;

    public HighScorePanel(ScoreHandler scoreHandler) {

        this.scoreHandler = scoreHandler;

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        try {
            makeComponents();
            addComponents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeComponents() throws IOException {

        title = new JLabel("HIGH SCORE");
        title.setFont(new Font("Helvetica", Font.PLAIN, 50));
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        title.setForeground(Color.pink);

        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource("../player.png"));
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

        score1 = new JLabel(getScoreString(0));
        score2 = new JLabel(getScoreString(1));
        score3 = new JLabel(getScoreString(2));

        createScores(score1);
        createScores(score2);
        createScores(score3);

        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("button.png"));
        menuButton = new JButton(icon){
            {
                setPreferredSize(new Dimension(400, 100));
                setMaximumSize(new Dimension(400, 100));
                setText("BACK");
                setHorizontalTextPosition(JButton.CENTER);
                setAlignmentX(JButton.CENTER_ALIGNMENT);
            }
        };
        menuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                option = Gamestate.MENU;
            }
        });
    }

    private String getScoreString(int index){
        String scoreval;
        if (scoreHandler.getTopScore(index) < 0){
            scoreval = "0";
        } else {
            scoreval = Integer.toString(scoreHandler.getTopScore(index));
        }
        return scoreval;
    }

    private void createScores(JLabel label){
        label.setFont(new Font("Helvetica", Font.PLAIN, 40));
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setForeground(Color.pink);
    }

    private void addComponents() {
        this.add(Box.createVerticalGlue());
        this.add(title);
        this.add(Box.createVerticalGlue());
        if (imageLabel != null){
            this.add(imageLabel);
        }
        this.add(Box.createVerticalGlue());

        this.add(score1);
        this.add(Box.createVerticalGlue());

        this.add(score2);
        this.add(Box.createVerticalGlue());

        this.add(score3);
        this.add(Box.createVerticalGlue());

        this.add(Box.createVerticalGlue());
        this.add(menuButton);
        this.add(Box.createVerticalGlue());
    }


    public Gamestate getOption(){
        return option;
    }

}
