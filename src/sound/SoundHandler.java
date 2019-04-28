package sound;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class SoundHandler {

    private HashMap<String, SoundEffect> sounds;

    public SoundHandler(){
        sounds = new HashMap<>();
        SoundEffect sound1 = new SoundEffect("bow_release.wav");
        SoundEffect sound2 = new SoundEffect("damageEnemy.wav");
        SoundEffect sound3 = new SoundEffect("collect.wav");
        SoundEffect sound4 = new SoundEffect("portal.wav");
        SoundEffect sound5 = new SoundEffect("changeWeapon.wav");
        SoundEffect sound6 = new SoundEffect("loseHealth.wav");
        SoundEffect sound7 = new SoundEffect("loseGame.wav");
        SoundEffect sound8 = new SoundEffect("dagger.wav");
        SoundEffect sound9 = new SoundEffect("winGame.wav");
        SoundEffect sound10 = new SoundEffect("healthUp.wav");

        sounds.put("bow_release.wav", sound1);
        sounds.put("damageEnemy.wav", sound2);
        sounds.put("collect.wav", sound3);
        sounds.put("portal.wav", sound4);
        sounds.put("changeWeapon.wav", sound5);
        sounds.put("loseHealth.wav", sound6);
        sounds.put("loseGame.wav", sound7);
        sounds.put("dagger.wav", sound8);
        sounds.put("winGame.wav", sound9);
        sounds.put("healthUp.wav", sound10);
    }

    public void play(String name){
        SoundEffect sound = sounds.get(name);
        if (sound == null){
            System.out.println("null");
            return;
        }
        sound.play();
    }

    public void pause(){
        for (String name : sounds.keySet()){
            sounds.get(name).stop();
        }
    }

}
