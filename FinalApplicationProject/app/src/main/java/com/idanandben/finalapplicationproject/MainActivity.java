package com.idanandben.finalapplicationproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

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
    //PPT-idan & ben
    //poster-idan

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BackgroundMusic.initializeBackgroundMusic(getApplicationContext());
        prefs = getSharedPreferences(ConstProperties.USERS_TABLE, MODE_PRIVATE);
        showMainMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_page_menu, menu);

        for(int i = 0; i < menu.size(); i++) {
            if(menu.getItem(i).getItemId() == R.id.music) {
                boolean muted = prefs.getBoolean(ConstProperties.MUSIC_ENABLE_PREFERENCES, true);
                menu.getItem(i).setChecked(!muted);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result;

        switch (item.getItemId()) {
            case R.id.about:
                Toast.makeText(MainActivity.this,"Powered by Ben Machlev & Idan Arditi",Toast.LENGTH_LONG).show();
                result = true;
                break;
            case R.id.music:
                boolean muted = item.isChecked();
                prefs.edit().putBoolean(ConstProperties.MUSIC_ENABLE_PREFERENCES, muted).apply();
                BackgroundMusic.setMuteState(muted);
                item.setChecked(!muted);
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

    private void showExitDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(R.string.exit_string);
        dialogBuilder.setMessage(R.string.exit_confirmation);
        dialogBuilder.setPositiveButton(R.string.yes_string, (dialog, which) -> {
            BackgroundMusic.stopAndDisposeBackgroundMusic();
            finish();
        });
        dialogBuilder.setNegativeButton(R.string.no_string, null);
        AlertDialog endDialog = dialogBuilder.create();
        endDialog.show();
    }

    @Override
    public void onBackPressed() {
        if(!(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof MainMenuFragment)) {
            showMainMenu();
        } else {
            showExitDialog();
        }
    }

    @Override
    protected void onResume() {
        BackgroundMusic.startBackgroundMusic();
        BackgroundMusic.setMuteState(prefs.getBoolean(ConstProperties.MUSIC_ENABLE_PREFERENCES, true));
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