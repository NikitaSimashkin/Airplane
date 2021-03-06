package com.example.airplane.Activities;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.airplane.MusicResorces;
import com.example.airplane.R;

import java.util.ArrayList;
import java.util.Objects;

public class Levels_activity extends AppCompatActivity {
    private final ArrayList<Button> level_buttons = new ArrayList<>();
    private final boolean[] level_acces = new boolean[10];
    private View.OnClickListener on_click;
    private SharedPreferences pref;
    private boolean close = true;

    @Override
    protected void onResume() {
        if (close){
            MusicResorces.menu_s.start();
        }
        close = true;
        super.onResume();
        update();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Levels_activity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        close = false;
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_levels);

//        OnBackPressedDispatcher onBackPressedDispatcher = this.getOnBackPressedDispatcher();
//        onBackPressedDispatcher.addCallback(new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                Intent i = new Intent(Levels_activity.this, MainActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                close = false;
//                startActivity(i);
//            }
//        });

        on_click = v -> {
            MusicResorces.soundPool.play(MusicResorces.button_s1, 1, 1, 0, 0, 1);
            switch (v.getId()){
                case R.id.one:
                    Levels(1);
                    break;
                case R.id.two:
                    Levels(2);
                    break;
                case R.id.three:
                    Levels(3);
                    break;
                case R.id.four:
                    Levels(4);
                    break;
                case R.id.five:
                    Levels(5);
                    break;
                case R.id.six:
                    Levels(6);
                    break;
                case R.id.seven:
                    Levels(7);
                    break;
                case R.id.eight:
                    Levels(8);
                    break;
                case R.id.nine:
                    Levels(9);
                    break;
                case R.id.ten:
                    Levels(10);
                    break;
                }
        };

        level_buttons.add(findViewById(R.id.one));
        level_buttons.add(findViewById(R.id.two));
        level_buttons.add(findViewById(R.id.three));
        level_buttons.add(findViewById(R.id.four));
        level_buttons.add(findViewById(R.id.five));
        level_buttons.add(findViewById(R.id.six));
        level_buttons.add(findViewById(R.id.seven));
        level_buttons.add(findViewById(R.id.eight));
        level_buttons.add(findViewById(R.id.nine));
        level_buttons.add(findViewById(R.id.ten));

        Button level_inf = findViewById(R.id.button_inf);

        level_inf.setOnClickListener(v -> {
            MusicResorces.soundPool.play(MusicResorces.button_s1, 1, 1, 0, 0, 1);
            Levels(99);});

        pref = getSharedPreferences("Main", MODE_PRIVATE);
        pref.edit().putBoolean("level_1", true).apply();

        update();

    }

    private void Levels(int number){
        Intent i = new Intent(Levels_activity.this, PlayActivity.class);
        i.putExtra("number", number);
        close = false;
        startActivity(i);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        if (close){
            MusicResorces.menu_s.pause();
        }
        super.onPause();
    }

    private void update(){
        for (int i = 0; i < level_acces.length-1; i++){
            Button current_button = level_buttons.get(i);
            current_button.setOnClickListener(on_click);
            level_acces[i] = pref.getBoolean("level_" + (i + 1), false);
            if (!level_acces[i]){
                current_button.setClickable(false);
                current_button.setBackgroundColor(Color.GRAY);
            } else {
                current_button.setBackgroundColor(getResources().getColor(R.color.level_active, null));
            }
        }
        Button ten = level_buttons.get(level_buttons.size()-1);
        ten.setOnClickListener(on_click);
        if (!pref.getBoolean("level_10", false)){
            ten.setClickable(false);
            ten.setBackgroundColor(Color.GRAY);
        } else {
            ten.setBackgroundColor(Color.RED);
        }

        Button level_inf = findViewById(R.id.button_inf);
        if (!pref.getBoolean("level_inf", false)){
            level_inf.setClickable(false);
            level_inf.setBackgroundColor(Color.GRAY);
        } else {
            level_inf.setBackgroundColor(getResources().getColor(R.color.level_inf, null));
        }

        TextView record = findViewById(R.id.your_record);
        String text = getResources().getString(R.string.your_record) + " " + pref.getInt("record", 0);
        record.setText(text);
    }
}