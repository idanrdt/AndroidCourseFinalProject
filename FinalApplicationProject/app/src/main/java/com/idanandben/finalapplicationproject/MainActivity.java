package com.idanandben.finalapplicationproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.OrientationEventListener;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.idanandben.finalapplicationproject.fragments.CustomGameFragment;
import com.idanandben.finalapplicationproject.fragments.MainMenuFragment;
import com.idanandben.finalapplicationproject.utilities.ConstProperties;
import com.idanandben.finalapplicationproject.utilities.UserSettings;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playAudio();
        showMainMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void showMainMenu() {
        MainMenuFragment mainMenuFragment = new MainMenuFragment();
        mainMenuFragment.setButtonListeners(new MainMenuFragment.MainMenuButtonsListeners() {
            @Override
            public void onQuickStartButtonClicked() {
                startQuickGame();
            }

            @Override
            public void onCustomGameButtonClicked() {
                startCustomGame();
            }
        });

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                .replace(R.id.fragment_container, mainMenuFragment).addToBackStack(null).commit();
    }

    private void startGame(UserSettings settings) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(ConstProperties.USER_SETTINGS_MSG, settings);
        startActivity(intent);
    }

    private void startQuickGame() {
        UserSettings quickSettings = new UserSettings(1);
        startGame(quickSettings);
    }

    private void startCustomGame() {
        CustomGameFragment customGameFragment = new CustomGameFragment();

        customGameFragment.setGameSelectedListener((level, difficulty) -> {
            UserSettings customSettings = new UserSettings(level, difficulty);
            startGame(customSettings);
        });

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                .add(R.id.fragment_container, customGameFragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        if(!(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof MainMenuFragment)) {
            showMainMenu();
        } else {
            showExitDialog();
        }
    }

    private void showExitDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Exit");
        dialogBuilder.setMessage("Are you sure you want to exit?");

        dialogBuilder.setPositiveButton("Yes", (dialog, which) -> finish());
        dialogBuilder.setNegativeButton("No", null);

        AlertDialog endDialog = dialogBuilder.create();
        endDialog.show();
    }
    private void playAudio() {

        String audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";

        // initializing media player
        mediaPlayer = new MediaPlayer();

        // below line is use to set the audio
        // stream type for our media player.
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // below line is use to set our
        // url to our media player.
        try {
            mediaPlayer.setDataSource(audioUrl);
            // below line is use to prepare
            // and start our media player.
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // below line is use to display a toast message.
        Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show();
    }
}