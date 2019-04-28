package sound;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
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
            if (clip == null){
                URL url = getClass().getClassLoader().getResource(file);
                InputStream input = url.openStream();
                InputStream audio = new BufferedInputStream(input);
                audioInput = AudioSystem.getAudioInputStream(audio);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
            }
            clip.start();
            clip.setFramePosition(0);
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

    private void playSoundOld() {
        AudioInputStream audioInput = null;
        try {
            URL url = getClass().getClassLoader().getResource(file);
            System.out.println(url);
            InputStream input = url.openStream();
            InputStream audio = new BufferedInputStream(input);
            audioInput = AudioSystem.getAudioInputStream(audio);
        } catch (Exception ex) {
            System.out.println("Sound error");
        }
        AudioFormat audioFormat = audioInput.getFormat();
        SourceDataLine line = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(audioFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        line.start();
        int nBytesRead = 0;
        byte[] abData = new byte[128000];
        while (nBytesRead != -1) {
            try {
                nBytesRead = audioInput.read(abData, 0, abData.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (nBytesRead >= 0) {
                int nBytesWritten = line.write(abData, 0, nBytesRead);
            }
        }
        line.drain();
        line.close();
    }

    /**
     * Plays the {@code Practice} or {@code Original}
     * depending on which constructor is called.
     */
    public void playFF() {
        //ClassLoader classLoader = new ReadResourceFileDemo().getClass().getClassLoader();

        File file1;

        //FileUtils.copyInputStreamToFile(getClass().getResourceAsStream(file), file1)

        //System.out.println(filde.exists());
        //InputStream input = url.openStream();
        //InputStream audio = new BufferedInputStream(input);
        //audioInput = AudioSystem.getAudioInputStream(audio);

        // Load the directory as a resource
        //URL dir_url = ClassLoader.getSystemResource(dir_path);
// Turn the resource into a File object





        //File file1 = new File(getClass().getClassLoader().getResourceAsStream(file));

        //String command = "ffplay -autoexit -nodisp -i " + filde;
        //process(command);
    }



    public static void process(String command) {
        ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", command);
        //pb.directory(directory);

        try {
            Process process = pb.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        playSound();
    }

}
