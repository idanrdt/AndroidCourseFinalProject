package com.idanandben.finalapplicationproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
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
    private List<String> players;
    private List<Integer> scores;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        prefs = getSharedPreferences(ConstProperties.USERS_TABLE, Context.MODE_PRIVATE);
        Set<String> userScores = prefs.getStringSet(ConstProperties.SCORES_PREFERENCES, new HashSet<>());

        Map<Integer, String> playersScore = new TreeMap<>(Collections.reverseOrder());

        for (String userScore : userScores) {
            String playerName = userScore.replaceAll("[0-9]", "");
            Integer score = Integer.parseInt(userScore.replaceAll("[^0-9]",""));

            playersScore.put(score, playerName);
        }

        players = new ArrayList<>(playersScore.values());
        scores = new ArrayList<>(playersScore.keySet());

        RecyclerView recyclerView = findViewById(R.id.leaderboard_recycler_view_table);

        adapter = new ScoreViewAdapter(scores, players);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.score_page_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.clear_all_menu_button) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ScoreActivity.this);
            dialogBuilder.setTitle(R.string.clear_all_string);
            dialogBuilder.setMessage(R.string.clear_all_confirmation);
            dialogBuilder.setPositiveButton(R.string.yes_string, (dialog, which) -> confirmDelete());
            dialogBuilder.setNegativeButton(R.string.no_string, null);
            AlertDialog endDialog = dialogBuilder.create();
            endDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void confirmDelete(){
        players.clear();
        scores.clear();
        adapter.notifyDataSetChanged();

        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putStringSet(ConstProperties.SCORES_PREFERENCES, new HashSet<>()).apply();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onTrimMemory(int level) {
        if(level == TRIM_MEMORY_BACKGROUND || level == TRIM_MEMORY_UI_HIDDEN) {
            BackgroundMusic.pauseBackgroundMusic();
        }
        super.onTrimMemory(level);
    }
}