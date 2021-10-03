package com.idanandben.finalapplicationproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.idanandben.finalapplicationproject.utilities.ConstProperties;
import com.idanandben.finalapplicationproject.utilities.Element;
import com.idanandben.finalapplicationproject.utilities.ElementCollection;
import com.idanandben.finalapplicationproject.utilities.UserSettings;
import com.idanandben.finalapplicationproject.widgets.BankTableBlock;
import com.idanandben.finalapplicationproject.widgets.ElementTableBlock;
import com.idanandben.finalapplicationproject.widgets.PeriodicTableView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private PeriodicTableView tableView;
    private MediaPlayer timeout,wrong;
    private UserSettings userSettings;

    private MainActivity md;

    private TextView timeLeftTextView;
    private TextView pointsTextView;
    private TextView lifeTextView;
    private TextView instructionsTextView;

    private CountDownTimer timer;
    private int pointsAmount;
    private int lifeAmount;

    //TODO:
    //1. Get Settings from intent (level, life points, time).
    //2. Add time icon.
    //3. Add life icon.
    //4. Add level title.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemUI();
        setContentView(R.layout.activity_game);
       // md.onResume();
        userSettings = getIntent().getParcelableExtra(ConstProperties.USER_SETTINGS_MSG);
        tableView = findViewById(R.id.tableView);
        instructionsTextView = findViewById(R.id.instruction_label);

        saveInstanceInPrefrences();

        startNewGame();
    }

    private void startNewGame() {
        hideSystemUI();
        loadTable();
        resetPointsAndLife();
        showInstructions();
    }

    //TODO:
    //1. Set strings in file
    private void resetPointsAndLife() {
        pointsTextView = findViewById(R.id.points_text_view);
        lifeTextView = findViewById(R.id.life_text_view);

        pointsAmount = userSettings.getScore();

        switch (userSettings.getDifficulty()) {
            case 1: {
                lifeAmount = ConstProperties.EASY_LIFE_AMOUNT;
                break;
            }
            case 2: {
                lifeAmount = ConstProperties.MEDIUM_LIFE_AMOUNT;
                break;
            }
            case 3: {
                lifeAmount = ConstProperties.HARD_LIFE_AMOUNT;
            }
        }

        pointsTextView.setText("Points: " + pointsAmount);
        lifeTextView.setText("Life: " + lifeAmount);
    }

    private void initializeAndStartTimer(int minutes, int seconds) {
        timeLeftTextView = findViewById(R.id.time_left_text_view);
        String timeMessage = updateTimeMessage(minutes, seconds);
        timeLeftTextView.setText(timeMessage);
        long totalTime = minutes * 60000 + seconds * 1000;
        timer = new CountDownTimer(totalTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String timeMessage = updateTimeMessage(millisUntilFinished / 1000 / 60, millisUntilFinished / 1000 % 60);
                timeLeftTextView.setText(timeMessage);
            }

            @Override
            public void onFinish() {
                finnishGame(false);
            }
        }.start();
    }

    //TODO:
    //1. load time from settings
    private String updateTimeMessage(long minutes, long seconds){
        StringBuilder timeMessage = new StringBuilder();
        timeMessage.append("Time Left: 0");
        timeMessage.append(minutes).append(":");
        if(seconds < 10) {
            timeMessage.append("0");
        }
        timeMessage.append(seconds);
        return String.valueOf(timeMessage);
    }


    //TODO:
    //1. Generate 10-15 random items from wanted list.
    private void loadTable() {
        final ArrayList<ElementTableBlock> tableBlocks = new ArrayList<>();
        final ArrayList<BankTableBlock> bankBlocks = new ArrayList<>();
        ElementCollection collection = new ElementCollection();
        int bankAmount = 10;
        int rndAmount = 0;
        Random rand = new Random();
        for(Element element : collection.getElements().values()) {
            ElementTableBlock block = new ElementTableBlock(element, collection.getColorMap().get(element.colorGroup));
            if(rand.nextInt(3) == 1 && rndAmount < bankAmount && (element.atomicNumber != 71 && element.atomicNumber != 103)) {
                rndAmount++;
                BankTableBlock bank = new BankTableBlock(element.symbol);
                bank.setRow(9);
                bank.setCol(rndAmount);
                bank.setColor(collection.getColorMap().get(element.colorGroup));
                bankBlocks.add(bank);
                block.setVisibility(false);
            }

            tableBlocks.add(block);
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);

        tableView.initializeTable(tableBlocks, metrics.widthPixels, metrics.heightPixels, bankBlocks);
        setTableListeners();
    }

    //TODO:
    //1. Set strings in file
    private void setTableListeners() {
        tableView.setTableListeners(new PeriodicTableView.TableStateListeners() {
            @Override
            public void onCorrectElementPlaced() {
                pointsAmount ++;
                pointsTextView.setText("Points: "+ pointsAmount);
            }

            @Override
            public void onWrongElementPlaced() {
                lifeAmount --;

                wrong = MediaPlayer.create(getApplicationContext(), R.raw.wrongtune);
                wrong.start();
                lifeTextView.setText("Life: " + lifeAmount);
                if(lifeAmount <= 0 ) {
                    finnishGame(false);
                }
            }

            @Override
            public void onTableCompleted() {
                finnishGame(true);
            }
        });
    }

    private void finnishGame(boolean victorious) {
        tableView.stopTableProcessing();
        timer.cancel();
        int currentLevel = userSettings.getCurrentLevel();
        if (!victorious) {
            showLossDialog();
        } else {
            showWinningDialog();
            currentLevel++;
            userSettings.setCurrentStage(currentLevel);
            saveInstanceInPrefrences();
        }

        if(currentLevel <= ConstProperties.MAX_LEVEL_EXIST) {
            startNewGame();
        } else {
            //show score board;
        }
    }

    //TODO:
    //1. Set strings in file
    //2. Click "No" -> return to main screen without save.
    private void showLossDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Game Over");
        dialogBuilder.setMessage("Play again?");

        dialogBuilder.setPositiveButton("Yes", (dialog, which) -> startNewGame());
        dialogBuilder.setNegativeButton("No", (dialog, which) -> finish());
        AlertDialog endDialog = dialogBuilder.create();
        endDialog.show();
    }

    private void showWinningDialog() {
        userSettings.setScore(pointsAmount);
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    private void showInstructions() {
        instructionsTextView.setVisibility(View.VISIBLE);
        instructionsTextView.setText(getInstructionsForTextView());
        tableView.stopTableProcessing();
        CountDownTimer timer = new CountDownTimer(35 * 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                instructionsTextView.setVisibility(View.GONE);
                initializeAndStartTimer(getTimerMinutes(), getTimerSeconds());
                tableView.startTableProcessing();
            }
        }.start();
    }

    private int getTimerMinutes() {
        int minutes = 0;
        switch (userSettings.getDifficulty()) {
            case 1: {
                minutes = ConstProperties.EASY_TIME_MINUTES;
                break;
            }
            case 2: {
                minutes = ConstProperties.MEDIUM_TIME_MINUTES;
                break;
            }
            case 3: {
                minutes = ConstProperties.HARD_TIME_MINUTES;
            }
        }
        return minutes;
    }

    private int getTimerSeconds() {
        int seconds = 0;
        switch (userSettings.getDifficulty()) {
            case 1: {
                seconds = ConstProperties.EASY_TIME_SECONDS;
                break;
            }
            case 2: {
                seconds = ConstProperties.MEDIUM_TIME_SECONDS;
                break;
            }
            case 3: {
                seconds = ConstProperties.HARD_TIME_SECONDS;
            }
        }
        return seconds;    }

    private String getInstructionsForTextView() {
        String instructions = "";
        switch (userSettings.getCurrentLevel()) {
            case 1: {
                instructions = ConstProperties.LEVEL1_INSTRUCTIONS;
                break;
            }
            case 2: {
                instructions = ConstProperties.LEVEL2_INSTRUCTIONS;
                break;
            }
            case 3: {
                instructions = ConstProperties.LEVEL3_INSTRUCTIONS;
                break;
            }
        }

        return instructions;
    }

    private void saveInstanceInPrefrences() {
        getSharedPreferences(ConstProperties.USER_SETTINGS_MSG, MODE_PRIVATE).edit().putInt(ConstProperties.CURRENT_LEVEL_MSG, userSettings.getCurrentLevel())
                .putInt(ConstProperties.CURRENT_DIFFICULTY_MSG, userSettings.getDifficulty()).apply();
    }
}