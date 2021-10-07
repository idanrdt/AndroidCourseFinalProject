package com.idanandben.finalapplicationproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.idanandben.finalapplicationproject.R;

public class MainMenuFragment extends Fragment {

    private MainMenuButtonsListeners listener;

    public interface MainMenuButtonsListeners {
        void onQuickStartButtonClicked();

        void onCustomGameButtonClicked();

        void onleaderboardClicked();
    }

    public MainMenuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.main_menu_fragment, container, false);

        MaterialButton quickStartButton = fragmentView.findViewById(R.id.quick_start_button);
        quickStartButton.setOnClickListener(v -> listener.onQuickStartButtonClicked());

        MaterialButton customGameButton = fragmentView.findViewById(R.id.custom_game_button);
        customGameButton.setOnClickListener(v -> listener.onCustomGameButtonClicked());

        MaterialButton quickLeaderboard =fragmentView.findViewById((R.id.leaderboard_scores));
        quickLeaderboard.setOnClickListener(v -> listener.onleaderboardClicked());

        return fragmentView;
    }

    public void setButtonListeners(MainMenuButtonsListeners listener) {
        this.listener = listener;
    }
}
