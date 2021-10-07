package com.idanandben.finalapplicationproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.Preference;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.idanandben.finalapplicationproject.utilities.BackgroundMusic;
import com.idanandben.finalapplicationproject.utilities.ConstProperties;
import com.idanandben.finalapplicationproject.utilities.Element;
import com.idanandben.finalapplicationproject.utilities.ElementCollection;
import com.idanandben.finalapplicationproject.utilities.UserSettings;
import com.idanandben.finalapplicationproject.widgets.BankTableBlock;
import com.idanandben.finalapplicationproject.widgets.NameInsertDialog;
import com.idanandben.finalapplicationproject.widgets.PeriodicTableView;
import com.idanandben.finalapplicationproject.widgets.TableElementBlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class GameActivity extends AppCompatActivity {

    private PeriodicTableView tableView;
    private MediaPlayer wrong;
    private UserSettings userSettings;

    private SharedPreferences prefs;

    private TextView timeLeftTextView;
    private TextView pointsTextView;
    private TextView lifeTextView;
    private TextView instructionsTextView;

    private CountDownTimer timer;
    private CountDownTimer elementSwitchTimer;
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
        instructionsTextView = findViewById(R.id.instruction_label);

        prefs = getSharedPreferences(ConstProperties.USERS_TABLE_MSG, MODE_PRIVATE);

        saveInstanceInPreferences();

        startNewGame();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void startNewGame() {
        BackgroundMusic.onRestart(this);
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
        lifeAmount = ConstProperties.LIFE_AMOUNT_BY_DIFFICULTY[userSettings.getDifficulty() - 1];


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
                if(userSettings.getCurrentLevel() == 2) {
                    elementSwitchTimer.cancel();
                }
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
            if(minutes==0) {
                BackgroundMusic.onDestroy();
                BackgroundMusic.onStart(this, "time");
            }
            timeMessage.append("0");
        }
        timeMessage.append(seconds);
        return String.valueOf(timeMessage);
    }

    //TODO:
    //1. Generate 10-15 random items from wanted list.
    private void loadTable() {
        final ArrayList<TableElementBlock> tableBlocks = new ArrayList<>();
        final ArrayList<BankTableBlock> bankBlocks;
        ElementCollection collection = new ElementCollection();
        for(Element element : collection.getElements().values()) {
            TableElementBlock block = new TableElementBlock(element, collection.getColorMap().get(element.colorGroup));
            tableBlocks.add(block);
        }

        switch (userSettings.getCurrentLevel()) {
            case 1: {
                bankBlocks = prepareStage1(new ArrayList<>(tableBlocks), collection);
                break;
            }
            case 2: {
                bankBlocks = prepareStage2(tableBlocks, collection);
                break;
            }
            default:
                throw new IllegalArgumentException("number not exist");
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);

        tableView.initializeTable(tableBlocks, metrics.widthPixels, metrics.heightPixels, bankBlocks, userSettings.getCurrentLevel());
        setTableListeners();
    }

    private ArrayList<BankTableBlock> prepareStage2(ArrayList<TableElementBlock> tableElements, ElementCollection collection) {
        ArrayList<BankTableBlock> bankBlocks = new ArrayList<>();
        int bankAmount = ConstProperties.COLOR_GROUPS_BY_DIFFICULTY_LEVEL2[userSettings.getDifficulty() - 1];

        ArrayList<Integer> colorsID = new ArrayList<>(collection.getColorMap().keySet());
        colorsID.remove(0);
        Collections.shuffle(colorsID);

        for(int i = 0; i < bankAmount; i++) {
            BankTableBlock bank = new BankTableBlock(ConstProperties.ELEMENTS_FAMILY_NAMES[colorsID.get(i)]);
            bank.setRow(9);
            bank.setCol(i);
            bank.setColor(collection.getColorMap().get(colorsID.get(i)));
            bank.setColorGroup(colorsID.get(i));
            bankBlocks.add(bank);
        }

        for(TableElementBlock block : tableElements) {
            block.setColor(ConstProperties.GENERIC_COLOR);
            block.setVisibility(false);
        }

        initiateElementsPopup(new ArrayList<>(bankBlocks), collection);

        return bankBlocks;
    }

    private ArrayList<BankTableBlock> prepareStage1(ArrayList<TableElementBlock> blockElements, ElementCollection collection){
        Random rand = new Random();
        ArrayList<BankTableBlock> bankBlocks = new ArrayList<>();
        int bankAmount = ConstProperties.BLOCK_AMOUNT_BY_DIFFICULTY[userSettings.getDifficulty() - 1];
        int rndAmount = 0;

        Collections.shuffle(blockElements);

        for(TableElementBlock block : blockElements) {
            if((Integer.parseInt(block.getBlockAtomicNumber()) != 71 && Integer.parseInt(block.getBlockAtomicNumber()) != 103) && rand.nextInt(2) == 1 &&
                    collection.getWantedList().contains(Integer.parseInt(block.getBlockAtomicNumber()))) {

                rndAmount++;
                BankTableBlock bank = new BankTableBlock(block.getElementSymbol());
                bank.setRow(9);
                bank.setCol(rndAmount);

                if(userSettings.getDifficulty() == 1) {
                    bank.setColor(block.getColor());
                    bank.setAtomicNumber(block.getBlockAtomicNumber());
                } else if(userSettings.getDifficulty() == 2) {
                    bank.setColor(block.getColor());
                }

                bankBlocks.add(bank);

                block.setVisibility(false);
            }

            if(rndAmount == bankAmount) {
                break;
            }
        }

        return bankBlocks;
    }

    //TODO:
    //1. Set strings in file
    private void setTableListeners() {
        tableView.addTableListener(new PeriodicTableView.TableStateListeners() {
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

        if(userSettings.getCurrentLevel() == 2) {
            tableView.addTableListener(new PeriodicTableView.TableStateListeners() {
                @Override
                public void onCorrectElementPlaced() {
                    elementSwitchTimer.onFinish();
                    tableView.setTableEnabled(false);
                }

                @Override
                public void onWrongElementPlaced() {
                    elementSwitchTimer.onFinish();
                    tableView.setTableEnabled(false);
                }

                @Override
                public void onTableCompleted() {
                    elementSwitchTimer.cancel();
                }
            });
        }
    }

    private void finnishGame(boolean victorious) {
        tableView.setTableEnabled(false);
        timer.cancel();
        int currentLevel = userSettings.getCurrentLevel();
        if (!victorious) {
            showLossDialog();
        } else {
            showWinningDialog();
            currentLevel++;
            userSettings.setCurrentStage(currentLevel);
            saveInstanceInPreferences();
            if(currentLevel <= ConstProperties.MAX_LEVEL_EXIST - 1) {
                startNewGame();
            } else {
                showNameInsertDialog();
            }
        }
    }

    private void showNameInsertDialog(){
        NameInsertDialog dialog = new NameInsertDialog(this);
        SharedPreferences.Editor edit = prefs.edit();
        Set<String> scores = new HashSet<>(prefs.getStringSet(ConstProperties.SCORES, new HashSet<>()));

        dialog.setDoneButtonListener(name -> {
            String nameScoreBuilder = name +
                    " " +
                    userSettings.getScore();
            scores.add(nameScoreBuilder);
            edit.putStringSet(ConstProperties.SCORES, scores).apply();
            initializeScoreBoard();
        });

        dialog.setCancelButtonListener(() -> initializeScoreBoard ());
        dialog.show();
    }

    private void initializeScoreBoard(){
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
        finish();
    }

    //TODO:
    //1. Set strings in file
    //2. Click "No" -> return to main screen without save.
    private void showLossDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Game Over");
        dialogBuilder.setMessage("Play again?");

        dialogBuilder.setPositiveButton("Yes", (dialog, which) -> startNewGame());
        dialogBuilder.setNegativeButton("No", (dialog, which) -> initializeScoreBoard());
        dialogBuilder.setOnCancelListener(dialog -> finish());
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

        getSupportActionBar().hide();
    }

    private void showInstructions() {
        instructionsTextView.setVisibility(View.VISIBLE);
        instructionsTextView.setText(getInstructionsForTextView());
        tableView.setTableEnabled(false);
        CountDownTimer timer = new CountDownTimer(35 * 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                instructionsTextView.setVisibility(View.GONE);
                initializeAndStartTimer(ConstProperties.TIME_MINUTES_BY_DIFFICULTY[userSettings.getDifficulty() - 1],ConstProperties.TIME_SECONDS_BY_DIFFICULTY[userSettings.getDifficulty() - 1]);
                tableView.setTableEnabled(true);
                if(userSettings.getCurrentLevel() == 2) {
                    elementSwitchTimer.start();
                }
            }
        }.start();
    }

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

    private void saveInstanceInPreferences() {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt(ConstProperties.CURRENT_LEVEL_MSG, userSettings.getCurrentLevel())
                .putInt(ConstProperties.CURRENT_DIFFICULTY_MSG, userSettings.getDifficulty());

        if(prefs.getInt(ConstProperties.MAX_ALLOWED_LEVEL_MSG, 1) < userSettings.getCurrentLevel()) {
            prefsEditor.putInt(ConstProperties.MAX_ALLOWED_LEVEL_MSG, userSettings.getCurrentLevel());
        }

        prefsEditor.apply();
    }

    private void initiateElementsPopup(ArrayList<BankTableBlock> bankBlocks, ElementCollection collection) {

        ArrayList<Element> selectedElements = (ArrayList<Element>)collection.getElements().values().stream()
                .filter(e -> bankBlocks.stream()
                .anyMatch(b -> b.getName().equals(e.familyName))).collect(Collectors.toList());

        elementSwitchTimer = new CountDownTimer(15000, 15000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tableView.setIncreasedElement(selectedElements.get(0).symbol, true);
                tableView.setTableEnabled(true);
            }

            @Override
            public void onFinish() {
                tableView.setIncreasedElement(selectedElements.get(0).symbol, false);
                selectedElements.remove(0);
                if(selectedElements.size() == 0) {
                    elementSwitchTimer.cancel();
                    finnishGame(true);
                } else {
                    Collections.shuffle(selectedElements);
                    tableView.setTableEnabled(true);
                    elementSwitchTimer.start();
                }
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!hasWindowFocus() ) {
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