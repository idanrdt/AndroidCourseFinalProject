package com.idanandben.finalapplicationproject.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.idanandben.finalapplicationproject.R;
import com.idanandben.finalapplicationproject.utilities.ConstProperties;

public class CustomGameFragment extends Fragment {

    private SharedPreferences preferences;

    private LinearLayout levelsLayout;

    private int maxAllowedLevel;
    private int selectedLevel;

    private GameSelectedListener gameSelectedListener;

    public interface GameSelectedListener {
        void onLevelSelectedListener(int level, int difficulty);
    }

    public CustomGameFragment() {}

    public void setGameSelectedListener(GameSelectedListener listener) {
        this.gameSelectedListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getActivity().getSharedPreferences(ConstProperties.USERS_TABLE, Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.new_custom_game_fragment, container, false);
        levelsLayout = fragmentView.findViewById(R.id.levels_layout);
        maxAllowedLevel = preferences.getInt(ConstProperties.MAX_ALLOWED_LEVEL_PREFERENCES, 1);
        addButtons();

        return fragmentView;
    }

    private void addButtons() {
        MaterialButton levelButton;
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics()); //convert dp to px
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(px, px);

        for (int i = 1; i <= ConstProperties.MAX_LEVEL_EXIST; i++) {

            levelButton = new MaterialButton(getActivity());
            params.setMargins(20, 10, 20, 10);

            levelButton.setLayoutParams(params);
            levelButton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.hexagon, null));

            if (i <= maxAllowedLevel) {
                levelButton.setText(String.valueOf(i));
                levelButton.setTextSize(30);
                levelButton.setOnClickListener(this::showDifficultyDialog);
            } else {
                levelButton.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.level_locked, null));
                levelButton.setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_START);
                levelButton.setIconPadding(0);
                levelButton.setEnabled(false);
            }

            levelsLayout.addView(levelButton);

        }
    }

    private void showDifficultyDialog(View v) {

        MaterialButton button = (MaterialButton)v;
        selectedLevel = Integer.parseInt((button.getText().toString()));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.difficulty_selector);

        String[] difficulties = new String[] { getString(R.string.easy_difficulty), getString(R.string.medium_difficulty), getString(R.string.hard_difficulty) };

        builder.setItems(difficulties, (dialog, which) -> gameSelectedListener.onLevelSelectedListener(selectedLevel, ++which));

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
