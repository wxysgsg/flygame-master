package com.rxy;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

/**
 * 音乐播放的线程类
 */
public class SoundPool extends Thread{

    public static final String BG_MUSIC="game_music.wav";
    public static final String SHOOT="bullet.wav";
    public static final String BOOM="enemy0_down.wav";
    AudioClip ac;
    public SoundPool(String path){
        URL url =  this.getClass().getResource("/musics/"+path);
        ac =  Applet.newAudioClip(url);
    }

    public void play(){
        ac.play();
    }
    public void stopAc(){
        ac.stop();
    }
    public void loop(){
        ac.loop();
    }

    @Override
    public void run() {
        play();
    }


}
