package com.idanandben.finalapplicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.idanandben.finalapplicationproject.fragments.CustomGameFragment;
import com.idanandben.finalapplicationproject.fragments.MainMenuFragment;
import com.idanandben.finalapplicationproject.utilities.BackgroundMusic;
import com.idanandben.finalapplicationproject.utilities.ConstProperties;
import com.idanandben.finalapplicationproject.utilities.UserSettings;

public class MainActivity extends AppCompatActivity {

    //add dialog between levels
    //add translation
    //add instructions(VISAULIATY&TEXT)
    //add gifs
    //think about timer trail
    //PPT-idan&ben
    //poster-idan

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BackgroundMusic.startBackgroundMusic(this);
        showMainMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean result;

        switch (item.getItemId()) {
            case R.id.about:
                Toast.makeText(MainActivity.this,"Powered by Ben Machlev & Idan Arditi",Toast.LENGTH_LONG).show();
                 result = true;
                 break;
            case R.id.music:
                BackgroundMusic.changeMuteState();
                result = true;
                break;
            default:
                result = super.onOptionsItemSelected(item);
        }

        return result;
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

            @Override
            public void onLeaderboardClicked(){leaderboard();}
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
    private void leaderboard(){
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);

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
        dialogBuilder.setPositiveButton("Yes", (dialog, which) -> {
            BackgroundMusic.stopBackgroundMusic();
            finish();
                });
        dialogBuilder.setNegativeButton("No", null);
        AlertDialog endDialog = dialogBuilder.create();
        endDialog.show();
    }

    @Override
    protected void onResume() {
        BackgroundMusic.startBackgroundMusic();
        super.onResume();
    }

    @Override
    public void onTrimMemory(int level) {
        if(level == TRIM_MEMORY_BACKGROUND || level == TRIM_MEMORY_UI_HIDDEN) {
            BackgroundMusic.pauseBackgroundMusic();
        }
        super.onTrimMemory(level);

    }
}