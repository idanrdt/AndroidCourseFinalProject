package com.idanandben.finalapplicationproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import java.util.Set;
import java.util.stream.Collectors;

public class GameActivity extends AppCompatActivity {

    private PeriodicTableView tableView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideSystemUI();
        setContentView(R.layout.activity_game);
        userSettings = getIntent().getParcelableExtra(ConstProperties.USER_SETTINGS_MSG);
        tableView = findViewById(R.id.tableView);
        instructionsTextView = findViewById(R.id.instruction_label);

        prefs = getSharedPreferences(ConstProperties.USERS_TABLE, MODE_PRIVATE);

        saveInstanceInPreferences();

        startNewGame();
    }

    @Override
    public void onBackPressed() {
        resetTimers();
        finish();
    }

    private void startNewGame() {
        resetTimers();
        hideSystemUI();
        loadTable();
        showInstructions();
    }

    private void resetPointsAndLife() {
        pointsTextView = findViewById(R.id.points_text_view);
        lifeTextView = findViewById(R.id.life_text_view);

        pointsAmount = userSettings.getScore();
        lifeAmount = ConstProperties.LIFE_AMOUNT_BY_DIFFICULTY[userSettings.getDifficulty() - 1];


        pointsTextView.setText(String.format("%s%s", getText(R.string.points_message), pointsAmount));
        lifeTextView.setText(String.format("%s%s", getText(R.string.life_message), lifeAmount));
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
                if((millisUntilFinished / 1000 % 60) == 10  && millisUntilFinished / 1000 / 60 == 0) {
                    BackgroundMusic.startLastSecondsMusic(getApplicationContext());
                }
            }

            @Override
            public void onFinish() {
                if(userSettings.getCurrentLevel() == 2) {
                    elementSwitchTimer.cancel();
                }
                finnishGame(false);
            }
        }.start();
    }

    private String updateTimeMessage(long minutes, long seconds){
        StringBuilder timeMessage = new StringBuilder();
        timeMessage.append(getString(R.string.time_left_message));
        timeMessage.append("0").append(minutes).append(":");
        if(seconds < 10) {
            timeMessage.append("0");
        }
        timeMessage.append(seconds);
        return String.valueOf(timeMessage);
    }

    /**
     * Loads and initialize the table settings.
     */
    private void loadTable() {
        final ArrayList<TableElementBlock> tableBlocks = new ArrayList<>();
        final ArrayList<BankTableBlock> bankBlocks;
        ElementCollection collection = new ElementCollection(getApplicationContext());
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
            case 3: {
                bankBlocks = prepareStage3(new ArrayList<>(tableBlocks), collection);
                break;
            }

            default:
                throw new IllegalArgumentException("wrong level number");
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);

        tableView.initializeTable(tableBlocks, metrics.widthPixels, metrics.heightPixels, bankBlocks, userSettings.getCurrentLevel());
        setTableListeners();
    }

    private ArrayList<BankTableBlock> prepareStage1(ArrayList<TableElementBlock> blockElements, ElementCollection collection){
        ArrayList<BankTableBlock> bankBlocks = new ArrayList<>();
        int maxBankAmount = ConstProperties.BLOCK_AMOUNT_BY_DIFFICULTY[userSettings.getDifficulty() - 1];
        int bankAmount = 0;
        boolean getFromWanted = userSettings.getDifficulty() > 2;

        Collections.shuffle(blockElements);

        for(TableElementBlock block : blockElements) {
            if((Integer.parseInt(block.getBlockAtomicNumber()) != 71 && Integer.parseInt(block.getBlockAtomicNumber()) != 103) &&
                    (collection.getWantedList().contains(Integer.parseInt(block.getBlockAtomicNumber()))) || getFromWanted) {

                bankAmount++;
                BankTableBlock bank = new BankTableBlock(block.getElementSymbol(), block.getElementSymbol());
                bank.setRow(9);
                bank.setCol(bankAmount);

                if(userSettings.getDifficulty() == 1) {
                    bank.setColor(block.getColor());
                    bank.setAtomicNumber(block.getBlockAtomicNumber());
                } else if(userSettings.getDifficulty() == 2) {
                    bank.setColor(block.getColor());
                }

                bankBlocks.add(bank);

                block.setVisibility(false);
            }

            if(bankAmount == maxBankAmount) {
                break;
            }
        }

        return bankBlocks;
    }

    private ArrayList<BankTableBlock> prepareStage2(ArrayList<TableElementBlock> tableElements, ElementCollection collection) {
        ArrayList<BankTableBlock> bankBlocks = new ArrayList<>();
        String[] familyNames = collection.getFamilyNames();
        int bankAmount = ConstProperties.COLOR_GROUPS_BY_DIFFICULTY_LEVEL2[userSettings.getDifficulty() - 1];

        ArrayList<Integer> colorsID = new ArrayList<>(collection.getColorMap().keySet());
        colorsID.remove(0);
        Collections.shuffle(colorsID);

        for(int i = 0; i < bankAmount; i++) {
            BankTableBlock bank = new BankTableBlock(familyNames[colorsID.get(i)], "");
            bank.setRow(9);
            bank.setCol(i + 1);
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

    private ArrayList<BankTableBlock> prepareStage3(ArrayList<TableElementBlock> blockElements, ElementCollection collection) {
        ArrayList<BankTableBlock> bankBlocks = new ArrayList<>();
        int maxBankAmount =  ConstProperties.BLOCK_AMOUNT_BY_DIFFICULTY[userSettings.getDifficulty() - 1];
        int bankAmount = 0;
        boolean getFromWanted = userSettings.getDifficulty() > 2;

        Collections.shuffle(blockElements);

        for(TableElementBlock block : blockElements) {
            if((Integer.parseInt(block.getBlockAtomicNumber()) != 71 && Integer.parseInt(block.getBlockAtomicNumber()) != 103) &&
                    (collection.getWantedList().contains(Integer.parseInt(block.getBlockAtomicNumber()))) || getFromWanted) {

                bankAmount++;
                BankTableBlock bank = new BankTableBlock(block.getElementName(), block.getElementSymbol());
                if(bankAmount <= 8) {
                    bank.setRow(8);
                    bank.setCol(bankAmount);
                } else {
                    bank.setRow(9);
                    bank.setCol(bankAmount - 8);
                }

                if(userSettings.getDifficulty() == 1) {
                    bank.setColor(block.getColor());
                } else {
                    bank.setColor(ConstProperties.GENERIC_COLOR);
                }

                bankBlocks.add(bank);
            }

            if(bankAmount == maxBankAmount) {
                break;
            }
        }

        return bankBlocks;
    }

    /**
     * Register to table events.
     */
    private void setTableListeners() {

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

        tableView.addTableListener(new PeriodicTableView.TableStateListeners() {
            @Override
            public void onCorrectElementPlaced() {
                pointsAmount += ConstProperties.POINTS_MULTIPLIER_BY_DIFFICULTY[userSettings.getDifficulty() - 1];
                pointsTextView.setText(String.format("%s%s", getText(R.string.points_message), pointsAmount));
            }

            @Override
            public void onWrongElementPlaced() {
                BackgroundMusic.startWrongItemPlacedMusic(getApplicationContext());
                lifeAmount --;
                lifeTextView.setText(String.format("%s%s", getText(R.string.life_message), lifeAmount));
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

    /**
     * Handle the end of the game.
     * @param victorious - If the user win.
     */
    private void finnishGame(boolean victorious) {
        tableView.setTableEnabled(false);
        resetTimers();
        if (!victorious) {
            BackgroundMusic.startGameLossMusic(getApplicationContext());
            showLossDialog();
        } else {
            BackgroundMusic.startGameWonMusic(getApplicationContext());
            showWinningDialog();
        }
    }

    /**
     * Pops up the Name insert dialog.
     */
    private void showNameInsertDialog(){
        NameInsertDialog nameDialog = new NameInsertDialog(this);
        SharedPreferences.Editor edit = prefs.edit();
        Set<String> scores = new HashSet<>(prefs.getStringSet(ConstProperties.SCORES_PREFERENCES, new HashSet<>()));

        nameDialog.setDoneButtonListener(name -> {
            String nameScoreBuilder = name +
                    " " +
                    userSettings.getScore();
            scores.add(nameScoreBuilder);
            edit.putStringSet(ConstProperties.SCORES_PREFERENCES, scores).apply();
            initializeScoreBoard();
        });

        nameDialog.setOnCancelListener(dialog -> initializeScoreBoard());
        nameDialog.show();
    }

    private void initializeScoreBoard(){
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
        finish();
    }

    private void showLossDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setTitle(R.string.game_over_string);
        dialogBuilder.setMessage(R.string.play_again_string);
        dialogBuilder.setPositiveButton(R.string.yes_string, (dialog, which) -> {
            userSettings.setScore(0);
            startNewGame();
        });
        dialogBuilder.setNegativeButton(R.string.no_string, (dialog, which) -> initializeScoreBoard());
        dialogBuilder.setOnCancelListener(dialog -> finish());
        AlertDialog endDialog = dialogBuilder.create();

        endDialog.show();
    }

    private void showWinningDialog() {
        userSettings.setScore(pointsAmount);
        CountDownTimer timer = new CountDownTimer(3500, 3500) {
            @Override
            public void onTick(long millisUntilFinished) {
                instructionsTextView.setText(getString(R.string.winning_string_info));
                instructionsTextView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                int currentLevel = userSettings.getCurrentLevel();
                instructionsTextView.setVisibility(View.GONE);
                currentLevel++;
                userSettings.setCurrentStage(currentLevel);
                saveInstanceInPreferences();
                if (currentLevel <= ConstProperties.MAX_LEVEL_EXIST) {
                    startNewGame();
                } else {
                    showNameInsertDialog();
                }
            }
        }.start();
    }

    /**
     * Set full screen
     */
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

    /**
     * Shows the level instructions.
     */
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
                resetPointsAndLife();
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
                instructions = getString(R.string.level_1_instructions);
                break;
            }
            case 2: {
                instructions = getString(R.string.level_2_instructions);
                break;
            }
            case 3: {
                instructions = getString(R.string.level_3_instructions);
                break;
            }
        }

        return instructions;
    }

    private void saveInstanceInPreferences() {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt(ConstProperties.CURRENT_LEVEL_PREFERENCES, userSettings.getCurrentLevel())
                .putInt(ConstProperties.CURRENT_DIFFICULTY_PREFERENCES, userSettings.getDifficulty());

        if(prefs.getInt(ConstProperties.MAX_ALLOWED_LEVEL_PREFERENCES, 1) < userSettings.getCurrentLevel()) {
            prefsEditor.putInt(ConstProperties.MAX_ALLOWED_LEVEL_PREFERENCES, userSettings.getCurrentLevel());
        }

        prefsEditor.apply();
    }

    /**
     * Initialize the element popup (increased element in table).
     * @param bankBlocks - The bank blocks.
     * @param collection - The whole element collections.
     */
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

    private void resetTimers() {
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
        if(elementSwitchTimer != null) {
            elementSwitchTimer.cancel();
            elementSwitchTimer = null;
        }
    }

    @Override
    protected void onPause() {
        resetTimers();
        finish();
        super.onPause();
    }

    @Override
    public void onTrimMemory(int level) {
        if(level == TRIM_MEMORY_BACKGROUND || level == TRIM_MEMORY_UI_HIDDEN) {
            BackgroundMusic.pauseBackgroundMusic();
        }
        super.onTrimMemory(level);
    }
}