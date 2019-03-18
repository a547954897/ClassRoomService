package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private MediaPlayer mediaPlayer;

    public enum Control{
        PLAY,PAUSE,STOP
    }

    public MyService() {
        super();
        Log.d("MyServie", "MyService: MyServie");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyServie", "MyService:onCreate ");
        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this,R.raw.music);
            mediaPlayer.setLooping(false);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyServie", "MyService: onStartCommand");

        Bundle bundle = intent.getExtras();

        if (bundle != null){
            Control control =  (Control)bundle.getSerializable("Key");
            if (control!=null){
                switch (control){
                    case PLAY:
                        Log.d("test11", "onStartCommand:play ");
                        play();
                        break;
                    case PAUSE:
                        pause();
                        break;
                    case STOP:
                        stop();
                        break;
                }
            }
        }


        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public void onDestroy() {
        Log.d("MyServie", "MyService: onDestroy");
        /*if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }*/
        super.onDestroy();

    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("MyServie", "MyService:onUnbind ");
        return super.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("MyServie", "MyService:onBind ");
        return null;
    }


    private void play(){
        if (!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }

    private void pause(){
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    private void stop(){
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
    }

}
