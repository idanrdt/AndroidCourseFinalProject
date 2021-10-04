package com.idanandben.finalapplicationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.idanandben.finalapplicationproject.utilities.ConstProperties;
import com.idanandben.finalapplicationproject.utilities.UserSettings;

import java.util.HashSet;
import java.util.Set;

public class ScoreActivity extends AppCompatActivity {

    private Set<String> userScores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        SharedPreferences preferences = getSharedPreferences(ConstProperties.USERS_TABLE_MSG, Context.MODE_PRIVATE);
        //userScores = preferences.getStringSet(ConstProperties.SCORES, new HashSet<>());
        userScores = new HashSet<String>();
        userScores.add("idan - 100");
        userScores.add("Ben - 4500");

        //userName - score(int)
        //ben - 500

    }
}