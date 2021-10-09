package com.idanandben.finalapplicationproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.idanandben.finalapplicationproject.R;

public class InstructionsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.instructions_fragment, container, false);
        ImageView gif1 = fragmentView.findViewById(R.id.instructions_gif_1);
        ImageView gif2 = fragmentView.findViewById(R.id.instructions_gif_2);
        ImageView gif3 = fragmentView.findViewById(R.id.imageView3);

        Glide.with(this).load(R.drawable.level_1_instruction).into(gif1);
        Glide.with(this).load(R.drawable.level_2_instruction).into(gif2);
        Glide.with(this).load(R.drawable.level_3_instruction).into(gif3);


        return fragmentView;
    }
}
