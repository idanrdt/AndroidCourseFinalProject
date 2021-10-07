package com.idanandben.finalapplicationproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.idanandben.finalapplicationproject.R;

import java.util.List;

public class ScoreViewAdapter extends RecyclerView.Adapter<ScoreViewAdapter.RecyclerViewHolder>{
    private  List<String> namesplayer;
    private List<Integer> scoresplayers;
    public ScoreViewAdapter(List<Integer> scoresplayers, List<String> namesplayer) {
        this.scoresplayers=scoresplayers;
        this.namesplayer=namesplayer;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_score, parent, false);

        RecyclerViewHolder viewHolder =new RecyclerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ScoreViewAdapter.RecyclerViewHolder holder, int position) {
        holder.name.setText(namesplayer.get(position));
        holder.score.setText(scoresplayers.get(position));

    }

    @Override
    public int getItemCount() {
        return namesplayer.size();

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

