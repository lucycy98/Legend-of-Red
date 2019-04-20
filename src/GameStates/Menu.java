package GameStates;

import GameStates.Gamestate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        ImageIcon startIcon = new ImageIcon(getClass().getResource("../button.png"));
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

        ImageIcon quitIcon = new ImageIcon(getClass().getResource("../button.png"));
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

}
