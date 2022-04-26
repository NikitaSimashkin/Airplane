package com.example.airplane;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Objects;

public class Levels_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_levels);

        // КНОПКИ
        {
            View.OnClickListener Levels = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                }
            };

            ArrayList<Button> level_buttons = new ArrayList<>();
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

            SharedPreferences pref = getSharedPreferences("Main", MODE_PRIVATE);
            pref.edit().putBoolean("level_1", true).apply();

            boolean[] level_acces = new boolean[level_buttons.size()];
            for (int i = 0; i < level_acces.length; i++){
                Button current_button = level_buttons.get(i);
                current_button.setOnClickListener(Levels);
                level_acces[i] = pref.getBoolean("level_" + (i + 1), false);
                if (!level_acces[i]){
                    current_button.setClickable(false);
                    current_button.setBackgroundColor(Color.GRAY);
                } else {
                    current_button.setBackgroundColor(Color.BLUE);
                }
            }

            Button level_inf = findViewById(R.id.button_inf);
            if (!pref.getBoolean("level_inf", false)){
                level_inf.setClickable(false);
                level_inf.setBackgroundColor(Color.GRAY);
            } else {
                level_inf.setBackgroundColor(Color.BLUE);
            }
        }
    }

    private void Levels(int number){
        Intent i = new Intent(Levels_activity.this, PlayActivity.class);
        i.putExtra("number", number);
        startActivity(i);
    }
}