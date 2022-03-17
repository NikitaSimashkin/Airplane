package com.example.airplane;

import android.app.Fragment;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpaceshipControllerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpaceshipControllerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ButtonStateChangedHandler downButtonHandler;

    public SpaceshipControllerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment SpaceshipControllerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SpaceshipControllerFragment newInstance(String param1,
                                                          ButtonStateChangedHandler downButtonHandler) {
        SpaceshipControllerFragment fragment = new SpaceshipControllerFragment();
        fragment.downButtonHandler = downButtonHandler;
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("Sorry for TAG", "" + mParam1);
        View layout = inflater.inflate(R.layout.fragment_spaceship_controller, container, false);
        ImageButton btn = layout.findViewById(R.id.down_button);
        btn.setOnTouchListener((v, e) -> {
            Log.d("Sorry for TAG", "Button action");
            if (downButtonHandler == null) {
                return false;
            }
            switch (e.getAction()){
                case MotionEvent.ACTION_DOWN:
                    downButtonHandler.stateChanged(ButtonState.DOWN);
                    break;
                case MotionEvent.ACTION_UP:
                    downButtonHandler.stateChanged(ButtonState.UP);
                    break;
            }
            return true;
        });
        return layout;
    }
    public interface ButtonStateChangedHandler{
        void stateChanged(ButtonState state);
    }
    public enum ButtonState{
        UP,
        DOWN
    }
}