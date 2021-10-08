package com.idanandben.finalapplicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.idanandben.finalapplicationproject.fragments.CustomGameFragment;
import com.idanandben.finalapplicationproject.fragments.MainMenuFragment;
import com.idanandben.finalapplicationproject.utilities.BackgroundMusic;
import com.idanandben.finalapplicationproject.utilities.ConstProperties;
import com.idanandben.finalapplicationproject.utilities.UserSettings;

public class MainActivity extends AppCompatActivity {

    //uiux - main screen-Ben
    //leaderboard->resetscore->confirmation
    //music - change string to enum
    //level 3 - idan
    //add dialog between levels
    //change colors
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
                Toast.makeText(MainActivity.this,"powered by Ben Machlev& Idan Arditi",Toast.LENGTH_LONG).show();
                 return true;
            case R.id.rateusmenu:
                RateingDialog();
                return true;
            case R.id.music:
                BackgroundMusic.changeMuteState();
                if(!BackgroundMusic.isPlaying()) {
                    BackgroundMusic.onStart(this,"start");
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
            @Override
            public void onleaderboardClicked(){leaderboard();}

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
        onDestroy();
        dialogBuilder.setPositiveButton("Yes", (dialog, which) -> finish());
        dialogBuilder.setNegativeButton("No", null);
        AlertDialog endDialog = dialogBuilder.create();
        endDialog.show();
    }

    private void RateingDialog(){
        final AlertDialog.Builder builderdialograte=new AlertDialog.Builder(this);
        ConstraintLayout constraintLayout=new ConstraintLayout(this);
        final RatingBar ratingBar=new RatingBar(this);
        ConstraintLayout.LayoutParams lp=new ConstraintLayout.LayoutParams(
          ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        ratingBar.setLayoutParams(lp);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize(1);
        constraintLayout.addView(ratingBar);
        builderdialograte.setIcon(android.R.drawable.btn_star_big_on);
        builderdialograte.setTitle("Rate us");
        builderdialograte.setMessage("rate us please");
        builderdialograte.setView(constraintLayout);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                String message=null;
                if(rating<3.5){
                    message="sorry to hear that";
                }
                else{
                    message="thank you!";
                }
                Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        });

        builderdialograte.setPositiveButton("Done",null);
        builderdialograte.setNegativeButton("return", null);
        AlertDialog endDialog = builderdialograte.create();
        endDialog.show();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (!hasWindowFocus() && BackgroundMusic.isPlaying()) {
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