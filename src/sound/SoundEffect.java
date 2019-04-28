package sound;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

public class SoundEffect implements Runnable {

    private String file;
    private Clip clip = null;

    public SoundEffect(String filename) {
        this.file = "audio/" + filename;
    }

    public void play() {
        Thread t = new Thread(this);
        t.start();
    }

    private void playSound() {
        AudioInputStream audioInput = null;
        try {
            URL url = getClass().getClassLoader().getResource(file);
            InputStream input = url.openStream();
            InputStream audio = new BufferedInputStream(input);
            audioInput = AudioSystem.getAudioInputStream(audio);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        } catch (LineUnavailableException e) {
            throw new RuntimeException("Sound: Line Unavailable: " + e);
        }catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Sound error");
        }
    }

    public void stop() {
        try {
            if (clip != null){
                clip.stop();
            }
        } catch(Exception ex){
            System.out.println("error with playing sound");
        }
    }

    @Override
    public void run() {
        playSound();
    }

}
