package com.idanandben.finalapplicationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.View;

import com.idanandben.finalapplicationproject.utilities.Element;
import com.idanandben.finalapplicationproject.utilities.ElementCollection;
import com.idanandben.finalapplicationproject.widgets.BankTableBlock;
import com.idanandben.finalapplicationproject.widgets.ElementTableBlock;
import com.idanandben.finalapplicationproject.widgets.PeriodicTableView;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private PeriodicTableView tableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tableView = findViewById(R.id.tableView);
        hideSystemUi();
        loadTable();
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

    }

    private void hideSystemUi() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
    }
}