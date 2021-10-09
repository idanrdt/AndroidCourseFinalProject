package com.idanandben.finalapplicationproject.utilities;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.idanandben.finalapplicationproject.R;

import java.io.IOException;

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
    private static MediaPlayer backgroundPlayer;
    private static MediaPlayer singlePlayer;
    private static boolean muted = false;
    private static MusicState playerState = MusicState.OFF;

    public static void initializeBackgroundMusic(Context context) {
        if(backgroundPlayer == null) {
            backgroundPlayer = MediaPlayer.create(context, R.raw.bkg);
            backgroundPlayer.setLooping(true);
            playerState = MusicState.PREPARED;
        }
    }

    public static void startBackgroundMusic() {
        if(backgroundPlayer != null && !muted && playerState != MusicState.STARTED) {
            backgroundPlayer.start();
            playerState = MusicState.STARTED;
        }
    }

    public static void pauseBackgroundMusic() {
        if(playerState != MusicState.PAUSED) {
            playerState = MusicState.PAUSED;
            backgroundPlayer.pause();
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

    public static void setMuteState(boolean isMuted) {
        muted = isMuted;
        if(muted) {
            pauseBackgroundMusic();
        } else {
            startBackgroundMusic();
        }
    }

    public static void stopAndDisposeBackgroundMusic() {
        if(backgroundPlayer != null) {
            backgroundPlayer.pause();
            disposePlayer();
            playerState = MusicState.OFF;
        }
    }

    private static void playSingleMusic(Context context, MusicType musicType) {
        backgroundPlayer.pause();
        if(singlePlayer != null) {
            try {
                singlePlayer.stop();
            } catch (IllegalStateException ignored) {}
        }

        final int location = backgroundPlayer.getCurrentPosition();
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
                singlePlayer.release();
                singlePlayer = null;
                backgroundPlayer.seekTo(location);
                backgroundPlayer.start();
            }
        });
        singlePlayer.start();
    }

    private static void disposePlayer() {
        try {
            backgroundPlayer.stop();
            backgroundPlayer.release();
        }
        finally {
            backgroundPlayer = null;
        }
    }
}