package com.example.pruebas;

import android.content.Context;
import android.media.MediaPlayer;

public class MediaManager {
    public MediaPlayer music;
    public int paused;
    private Context context;

    public MediaManager(Context context){
        this.context = context;
    }

    public int getPaused() {
        return paused;
    }

    public void setPaused(int paused) {
        this.paused = paused;
    }

    public MediaPlayer getMusic() {
        return music;
    }

    public void setMusic(MediaPlayer music) {
        this.music = music;
    }

    public void startMusic(){
        if (music == null){
            music = MediaPlayer.create(context, R.raw.music);
            music.start();
            music.setVolume(0,50);
            music.setLooping(true);
        }else if(!music.isPlaying()){
            music.seekTo(paused);
            music.start();
            music.setVolume(0,50);
        }
    }
    public void startMusicShowPill(){
        if (music == null){
            music = MediaPlayer.create(context, R.raw.showpill);
            music.start();
            music.setVolume(0,50);
            music.setLooping(true);
        }else if(!music.isPlaying()){
            music.seekTo(paused);
            music.start();
            music.setVolume(0,50);
        }
    }
    public void startMusicModifyPill(){
        if (music == null){
            music = MediaPlayer.create(context, R.raw.modifypill);
            music.start();
            music.setVolume(0,100);
            music.setLooping(true);
        }else if(!music.isPlaying()){
            music.seekTo(paused);
            music.start();
            music.setVolume(0,100);
        }
    }

    public void stopMuscic(){
        music.release();
        music = null;
    }

    public void pauseMusic(){
        music.pause();
    }

    public int time(){
        int time = music.getCurrentPosition();
        return time;
    }

    public boolean isPlaying(){
        return music.isPlaying();
    }
}
