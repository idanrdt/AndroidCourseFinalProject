package com.idanandben.finalapplicationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.idanandben.finalapplicationproject.utilities.ConstProperties;
import com.idanandben.finalapplicationproject.utilities.UserSettings;

public class ScoreActivity extends AppCompatActivity {

    private UserSettings userSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        userSettings = getIntent().getParcelableExtra(ConstProperties.USER_SETTINGS_MSG);

    }
}