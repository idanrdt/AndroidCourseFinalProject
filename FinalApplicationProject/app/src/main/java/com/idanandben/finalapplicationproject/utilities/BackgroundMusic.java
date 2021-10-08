package com.idanandben.finalapplicationproject.utilities;

import android.content.Context;
import android.media.MediaPlayer;

import com.idanandben.finalapplicationproject.R;

public class BackgroundMusic  {
    static MediaPlayer player;
    private static boolean muted=false;

    public static void startGamemusic(Context context){
        if(!muted) {
        onDestroy();
        player=MediaPlayer.create(context.getApplicationContext(),R.raw.bkg);
        player.setLooping(true);
        player.start();
        }
    }
    public static void startWinningusic(Context context){
        if(!muted) {
            onDestroy();
            player=MediaPlayer.create(context.getApplicationContext(),R.raw.victory);
            player.start();
        }
    }

  /*  public static void startFailmusic(Context context){
        if(!muted) {
            onDestroy();
            player=MediaPlayer.create(context.getApplicationContext(),R.raw.wrongtune);
            player.start();
        }
    }*/

    public static void startLosemusic(Context context){
        if(!muted) {
            onDestroy();
            player=MediaPlayer.create(context.getApplicationContext(),R.raw.lose);
            player.start();
        }
    }

    public static void startTimermusic(Context context){
        if(!muted) {
            onDestroy();
            player=MediaPlayer.create(context.getApplicationContext(),R.raw.timetune);
            player.start();
        }
    }

    public static void onStop() {
        player.stop();
        player.release();
    }
    public static void onPause(){
        player.pause();
    }
    public static void  onResume(){
        if (!isPlaying()) {
            player.start();}

    }
    public static void onDestroy (){
        if (player != null) {
            try {
                onStop();
                } finally {
                player = null;
            }
        }
    }

    public static void changeMuteState(){
        if(!muted) {
            muted = true;
            onDestroy();
        }
        else {
            muted=false;
        }
    }
    public static boolean isMuted(){
        return muted;
    }

    public static boolean isPlaying()
    {
        return player != null && player.isPlaying();
    }
}