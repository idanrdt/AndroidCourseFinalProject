package com.idanandben.finalapplicationproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idanandben.finalapplicationproject.adapter.ScoreViewAdapter;
import com.idanandben.finalapplicationproject.utilities.ConstProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ScoreActivity extends AppCompatActivity {
    private ArrayList<String> itemsList = new ArrayList<>();
    private ListView listView;
    private Set<String> userScores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        SharedPreferences preferences = getSharedPreferences(ConstProperties.USERS_TABLE_MSG, Context.MODE_PRIVATE);
        //userScores = preferences.getStringSet(ConstProperties.SCORES, new HashSet<>());
        userScores = new HashSet<String>();
        userScores.add("idan 100");
        userScores.add("Ben 4500");
        userScores.add("Ben 500");
        userScores.add("Ben 9000");
        userScores.add("Ben 1");
        Map<Integer, String> playersScore = new TreeMap<>(Collections.reverseOrder());
        for (String userScore : userScores) {
            String playerName = userScore.substring(0, userScore.indexOf(" "));
            Integer score = Integer.parseInt(userScore.substring(userScore.indexOf(" ") + 1));
            playersScore.put(score, playerName);
        }
        List<String> players = new ArrayList<>(playersScore.values());
        List<Integer> scores = new ArrayList<>(playersScore.keySet());

        RecyclerView recyclerView = findViewById(R.id.leader);

        ScoreViewAdapter adapter=new ScoreViewAdapter(scores, players);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
}




