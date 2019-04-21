package sound;

import java.util.HashMap;

public class SoundHandler {

    HashMap<String, SoundEffect> sounds;

    public SoundHandler(){
        sounds = new HashMap<>();
        SoundEffect sound1 = new SoundEffect("bow_release.wav");
        SoundEffect sound2 = new SoundEffect("damageEnemy.wav");
        SoundEffect sound3 = new SoundEffect("collect.wav");
        sounds.put("bow_release.wav", sound1);
        sounds.put("damageEnemy.wav", sound2);
        sounds.put("collect.wav", sound3);
    }

    public void play(String name){
        SoundEffect sound = sounds.get(name);
        if (sound == null){
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
