package sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

public abstract class Sound{

    protected Clip clip;

    protected boolean loaded = false;

    protected final String file;

    protected Sound(String filename) {
        this.file = "../audio/"+filename;
        loadSound();
    }

    public abstract void play();

    public abstract void stop();


    public String file() {
        return file;
    }

    private void loadSound() {
        try {
            URL url = this.getClass().getResource(file);
            System.out.println(url);
            InputStream input = url.openStream();
            InputStream audio = new BufferedInputStream(input);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(audio);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
        } catch (Exception ex) {
            System.out.println("Sound error");
        }
    }



}
