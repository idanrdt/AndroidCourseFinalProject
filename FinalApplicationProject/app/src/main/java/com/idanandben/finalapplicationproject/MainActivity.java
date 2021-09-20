package com.idanandben.finalapplicationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.idanandben.finalapplicationproject.utilities.Element;
import com.idanandben.finalapplicationproject.utilities.ElementCollection;
import com.idanandben.finalapplicationproject.widgets.ElementTableBlock;
import com.idanandben.finalapplicationproject.widgets.PeriodicTableView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PeriodicTableView tableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableView = findViewById(R.id.tableView);
        hideSystemUi();
        loadTable();
    }

    private void loadTable() {
        final ArrayList<ElementTableBlock> tableBlocks = new ArrayList<>();
        ElementTableBlock block;
        ElementCollection colletion = new ElementCollection();
        for(Element element : colletion.GetElements().values()) {
            block = new ElementTableBlock(element);

            tableBlocks.add(block);
        }

        tableView.setBlocks(tableBlocks);

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