import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageHandler {

    public static Image getImage(String  name){
        URL location = TileShape.class.getResource("/" + name);
        ImageIcon im = new ImageIcon(location);
        return im.getImage();
    }
}
