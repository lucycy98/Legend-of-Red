import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener {

    JPanel panel = this;
    ProjectileHandler projectiles;
    Protagonist player;
    int speed = 8;
    int tileSize = 60;
    EnemyHandler enemies;
    MapHandler maps;
    WeaponHandler weapons;
    int weaponLocation = 800;
    Gamestate option;

    // gameScreen Constructor
    public GamePanel() {
        option = Gamestate.GAME;
        maps = new MapHandler();
        enemies = new EnemyHandler(tileSize, maps, projectiles);
        player = new Protagonist(tileSize, tileSize, tileSize, tileSize, "player.png", tileSize, maps, projectiles, enemies);
        enemies.addPlayer(player);
        weapons = new WeaponHandler(maps, projectiles, player, enemies, this);
        enemies.addWeaponHandler(weapons);
        weapons.createImages(weaponLocation, -40, 40, 40);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();
    }

    @Override
    public void paint(Graphics gr) {
        // Clears window
        super.paint(gr);
        Graphics2D window = (Graphics2D) gr;
        window.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        maps.paint(gr);
        player.paint(window);
        enemies.paint(window);
        weapons.paint(window);

        window.setColor(Color.white);

        window.drawString("Level:", 400, 25);
        window.drawString(String.valueOf(maps.getCurrentLevel() + 1), 450, 25);

        window.drawString("Health:", 550, 25);
        window.drawString(String.valueOf(player.getHealth() + 1), 600, 25);

        window.drawString("Current Weapon:", 700, 25);
        repaint();
    }

    public Gamestate getOption(){
        return option;
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    @Override
    public void keyPressed(KeyEvent evt) {

        switch (evt.getKeyCode()) {

            case KeyEvent.VK_SPACE:
                weapons.attack();
                break;
            case KeyEvent.VK_S:
                weapons.changeWeapon();
                break;
            default:
                player.keyPressed(evt);
        }

    }
}
