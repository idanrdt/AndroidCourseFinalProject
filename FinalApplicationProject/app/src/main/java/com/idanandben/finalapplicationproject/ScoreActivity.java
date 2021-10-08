package com.idanandben.finalapplicationproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idanandben.finalapplicationproject.adapter.ScoreViewAdapter;
import com.idanandben.finalapplicationproject.utilities.BackgroundMusic;
import com.idanandben.finalapplicationproject.utilities.ConstProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ScoreActivity extends AppCompatActivity {
    private ScoreViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Button clearbutton=findViewById(R.id.clearleader);
        SharedPreferences preferences = getSharedPreferences(ConstProperties.USERS_TABLE_MSG, Context.MODE_PRIVATE);
        Set<String> userScores = preferences.getStringSet(ConstProperties.SCORES, new HashSet<>());

        Map<Integer, String> playersScore = new TreeMap<>(Collections.reverseOrder());
        for (String userScore : userScores) {
            String playerName = userScore.substring(0, userScore.indexOf(" "));
            Integer score = Integer.parseInt(userScore.substring(userScore.indexOf(" ") + 1));
            playersScore.put(score, playerName);
        }

        clearbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ScoreActivity.this);
                dialogBuilder.setTitle("clear all");
                dialogBuilder.setMessage("Are you sure you want to clear board?");
                onDestroy();
                dialogBuilder.setPositiveButton("Yes", (dialog, which) -> confirmDelete(userScores,playersScore));
                dialogBuilder.setNegativeButton("No", null);
                AlertDialog endDialog = dialogBuilder.create();
                endDialog.show();
            }
        });
        List<String> players = new ArrayList<>(playersScore.values());
        List<Integer> scores = new ArrayList<>(playersScore.keySet());

        RecyclerView recyclerView = findViewById(R.id.leader);

        adapter=new ScoreViewAdapter(scores, players);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
    public void confirmDelete(Set<String> userScores, Map<Integer, String> playersScore){

        userScores.clear();
        playersScore.clear();
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!hasWindowFocus() && BackgroundMusic.isPlaying()) {
            BackgroundMusic.onPause();
            //finish();
        }
    }
}




