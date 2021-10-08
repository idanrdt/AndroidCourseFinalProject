package com.idanandben.finalapplicationproject.utilities;

import android.content.Context;
import android.media.MediaPlayer;

import com.idanandben.finalapplicationproject.R;

enum MusicState {
    PREPARED,
    STARTED,
    PAUSED,
    OFF,
}

enum MusicType {
    WINNING,
    LOSING,
    WRONG_ITEM,
    NO_TIME_LEFT,
}

public class BackgroundMusic  {
    private static MediaPlayer player;
    private static MediaPlayer singlePlayer;
    private static boolean muted = false;
    private static MusicState playerState = MusicState.OFF;

    public static void startBackgroundMusic(Context context){
        if(player == null) {
            player = MediaPlayer.create(context, R.raw.bkg);
            player.setLooping(true);
        }

        startBackgroundMusic();
        playerState = MusicState.PREPARED;
    }

    public static void startBackgroundMusic() {
        if(player != null && !muted && playerState != MusicState.STARTED) {
            player.start();
            playerState = MusicState.STARTED;
        }
    }

    public static void pauseBackgroundMusic() {
        if(playerState != MusicState.PAUSED) {
            playerState = MusicState.PAUSED;
            player.pause();
        }
    }

    public static void startGameWonMusic(Context context){
        if(!muted) {
            playSingleMusic(context, MusicType.WINNING);
        }
    }

    public static void startGameLossMusic(Context context){
        if(!muted) {
            playSingleMusic(context, MusicType.LOSING);
        }
    }

    public static void startWrongItemPlacedMusic(Context context){
        if(!muted) {
            playSingleMusic(context, MusicType.WRONG_ITEM);
        }
    }

    public static void startLastSecondsMusic(Context context){
        if(!muted) {
            playSingleMusic(context, MusicType.NO_TIME_LEFT);
        }
    }

    private static void playSingleMusic(Context context, MusicType musicType) {
        player.pause();
        final int location = player.getCurrentPosition();
        switch (musicType) {
            case WINNING: {
                singlePlayer = MediaPlayer.create(context, R.raw.victory);
                break;
            }
            case LOSING:{
                singlePlayer = MediaPlayer.create(context.getApplicationContext(),R.raw.lose);
                break;
            }
            case WRONG_ITEM:{
                singlePlayer = MediaPlayer.create(context.getApplicationContext(),R.raw.wrongtune);
                break;
            }
            case NO_TIME_LEFT: {
                singlePlayer = MediaPlayer.create(context.getApplicationContext(),R.raw.timetune);
                break;
            }
        }
        singlePlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                mp = null;
                player.seekTo(location);
                player.start();
            }
        });
        singlePlayer.start();
    }
}