package com.idanandben.finalapplicationproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.idanandben.finalapplicationproject.R;

import java.util.List;

public class ScoreViewAdapter extends RecyclerView.Adapter<ScoreViewAdapter.RecyclerViewHolder>{
    private final List<String> playerNames;
    private final List<Integer> playerScores;

    public ScoreViewAdapter(List<Integer> playerScores, List<String> playerNames) {
        this.playerScores = playerScores;
        this.playerNames = playerNames;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_score, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScoreViewAdapter.RecyclerViewHolder holder, int position) {
        holder.name.setText(playerNames.get(position));
        holder.score.setText(String.valueOf(playerScores.get(position)));

    }

    @Override
    public int getItemCount() {
        return playerNames.size();

    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        private final TextView name;
        private final TextView score;

        public RecyclerViewHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.player_score_name);
                score = itemView.findViewById(R.id.player_score);
            }
        }
}

