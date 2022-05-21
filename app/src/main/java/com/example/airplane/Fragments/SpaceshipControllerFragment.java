package com.example.airplane.Fragments;

import android.app.Fragment;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.airplane.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpaceshipControllerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpaceshipControllerFragment extends Fragment {

    public final static String TAG = "test";

    public SpaceshipControllerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_spaceship_controller, container, false);
        return layout;
    }
}