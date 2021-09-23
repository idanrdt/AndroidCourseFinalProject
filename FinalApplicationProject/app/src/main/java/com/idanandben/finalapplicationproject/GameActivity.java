package com.idanandben.finalapplicationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.idanandben.finalapplicationproject.utilities.Element;
import com.idanandben.finalapplicationproject.utilities.ElementCollection;
import com.idanandben.finalapplicationproject.widgets.BankTableBlock;
import com.idanandben.finalapplicationproject.widgets.ElementTableBlock;
import com.idanandben.finalapplicationproject.widgets.PeriodicTableView;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private PeriodicTableView tableView;

    private TextView timeLeftTextView;
    private TextView pointsTextView;
    private TextView lifeTextView;

    private CountDownTimer timer;
    private int pointsAmount;
    private int lifeAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemUI();
        setContentView(R.layout.activity_game);
        tableView = findViewById(R.id.tableView);

        loadTable();
        initializeAndStartTimer();
        resetPointsAndLife();
    }

    private void resetPointsAndLife() {
        pointsTextView = findViewById(R.id.points_text_view);
        lifeTextView = findViewById(R.id.life_text_view);

        pointsAmount = 0;
        lifeAmount = 3; // load from settings

        pointsTextView.setText("Points: " + pointsAmount);
        lifeTextView.setText("Life: " + lifeAmount);
    }

    private void initializeAndStartTimer() {

        timeLeftTextView = findViewById(R.id.time_left_text_view);
        StringBuilder timeMessage = new StringBuilder();
        timeMessage.append("Time Left: ");
        timeMessage.append("01:00"); // load total time from settings
        timeLeftTextView.setText(timeMessage);
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000 % 60;
                long minutes = millisUntilFinished / 1000 / 60;
                timeMessage.setLength(0);
                timeMessage.append("Time Left: ");
                timeMessage.append("0" + minutes + ":");
                if(seconds < 10) {
                    timeMessage.append("0");
                }
                timeMessage.append(seconds);

                timeLeftTextView.setText(timeMessage);
            }

            @Override
            public void onFinish() {
                timeLeftTextView.setText("Gave Over!");
                //stop table processing
            }
        }.start();
    }

    private void loadTable() {
        final ArrayList<ElementTableBlock> tableBlocks = new ArrayList<>();
        final ArrayList<BankTableBlock> bankBlocks = new ArrayList<>();
        ElementCollection collection = new ElementCollection();
        int bankAmount = 10;
        int rndAmount = 0;
        Random rand = new Random();
        for(Element element : collection.GetElements().values()) {
            ElementTableBlock block = new ElementTableBlock(element);
            if(rand.nextInt(2) == 1 && rndAmount < bankAmount) {
                rndAmount++;
                BankTableBlock bank = new BankTableBlock(element.symbol);
                bank.setRow(9);
                bank.setCol(rndAmount);
                bankBlocks.add(bank);
                block.setVisable(false);
            }

            tableBlocks.add(block);
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);

        tableView.setBlocks(tableBlocks, metrics.widthPixels, metrics.heightPixels, bankBlocks);
        setTableListeners();
    }

    private void setTableListeners() {
        tableView.setTableListeners(new PeriodicTableView.TableStateListeners() {
            @Override
            public void onPointGained(int amountOfPoints) {
                pointsAmount += amountOfPoints;
                pointsTextView.setText("Points: "+ pointsAmount);
            }

            @Override
            public void onLifeLoss(int lifeLeft) {
                lifeAmount -= lifeLeft;
                lifeTextView.setText("Life: " + lifeAmount);
            }

            @Override
            public void onTableCompleted() {
                //stop table processing
            }
        });
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

        getSupportActionBar().hide();
    }
}