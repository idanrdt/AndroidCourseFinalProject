package com.idanandben.finalapplicationproject.utilities;

import android.content.Context;
import android.media.MediaPlayer;

import com.idanandben.finalapplicationproject.R;

public class BackgroundMusic  {
    static MediaPlayer player;
    private static boolean muted=false;

    public static void onStart(Context context,String name){
        onDestroy();
        if(!muted){
            if(name.equals("start")){
                player=MediaPlayer.create(context.getApplicationContext(),R.raw.bkg);
                player.setLooping(true);
            }
            else if(name.equals("time")) {
                player=MediaPlayer.create(context.getApplicationContext(),R.raw.timetune);
            }
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
    public static void onRestart(Context context)
    {
        onDestroy();
        onStart(context,"start");
    }
    public static boolean isPlaying()
    {
        return player != null && player.isPlaying();
    }
}