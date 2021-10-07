package com.idanandben.finalapplicationproject;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.idanandben.finalapplicationproject.fragments.CustomGameFragment;
import com.idanandben.finalapplicationproject.fragments.MainMenuFragment;
import com.idanandben.finalapplicationproject.utilities.BackgroundMusic;
import com.idanandben.finalapplicationproject.utilities.ConstProperties;
import com.idanandben.finalapplicationproject.utilities.UserSettings;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* if(BackgroundMusic.isPlaying()){
            BackgroundMusic.onDestroy();
        }*/
        BackgroundMusic.onStart(this,"start");
        showMainMenu();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu); //your file name
        return super.onCreateOptionsMenu(menu);    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                //your code
                // EX : call intent if you want to swich to other activity
                return true;
            case R.id.music:
                if (BackgroundMusic.isPlaying()){
                   BackgroundMusic.onPause();
                }
                else{
                    BackgroundMusic.onResume();

                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        //mediaPlayer.pause();
        onDestroy();
        dialogBuilder.setPositiveButton("Yes", (dialog, which) -> finish());
        dialogBuilder.setNegativeButton("No", null);
        AlertDialog endDialog = dialogBuilder.create();
        endDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (hasWindowFocus() ) {
            BackgroundMusic.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        onWindowFocusChanged(hasWindowFocus());
        BackgroundMusic.onResume();
    }
}