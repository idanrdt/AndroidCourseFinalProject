package com.idanandben.finalapplicationproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

    private UserSettings userSettings;

    private TextView timeLeftTextView;
    private TextView pointsTextView;
    private TextView lifeTextView;

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

        userSettings = getIntent().getParcelableExtra(ConstProperties.USER_SETTINGS_MSG);
        tableView = findViewById(R.id.tableView);
        startNewGame();
    }

    private void startNewGame() {
        hideSystemUI();
        loadTable();
        initializeAndStartTimer(1, 0);
        resetPointsAndLife();
    }

    //TODO:
    //1. Set strings in file
    private void resetPointsAndLife() {
        pointsTextView = findViewById(R.id.points_text_view);
        lifeTextView = findViewById(R.id.life_text_view);

        pointsAmount = 0; // load from settings if different from level 1.
        lifeAmount = 3; // load from settings.

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
            if(rand.nextInt(2) == 1 && rndAmount < bankAmount) {
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
        if (!victorious) {
            timer.cancel();
            showLossDialog();
        } else {
            //update max level in preferences
        }

        //advance to next level?
    }

    //TODO:
    //1. Set strings in file
    //2. Click "No" -> return to main screen without save.
    private void showLossDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Game Over");
        dialogBuilder.setMessage("Play again?");

        dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startNewGame();
            }
        });
        dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog endDialog = dialogBuilder.create();
        endDialog.show();
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
}