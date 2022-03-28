package com.example.airplane;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

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
                        Level_one();
                        break;
                    case R.id.two:
                        //Level_one();
                        break;
                    case R.id.three:
                        // Level_one();
                        break;
                    case R.id.four:
                        //Level_one();
                        break;
                    case R.id.five:
                        // Level_one();
                        break;
                    case R.id.six:
                        //  Level_one();
                        break;
                    case R.id.seven:
                        //  Level_one();
                        break;
                    case R.id.eight:
                        // Level_one();
                        break;
                    case R.id.nine:
                        //Level_one();
                        break;
                    case R.id.ten:
                        // Level_one();
                        break;
                    }
                }
            };

            Button one = (Button) findViewById(R.id.one);
            Button two = findViewById(R.id.two);
            Button three = findViewById(R.id.three);
            Button four = findViewById(R.id.four);
            Button five = findViewById(R.id.five);
            Button six = findViewById(R.id.six);
            Button seven = findViewById(R.id.seven);
            Button eight = findViewById(R.id.eight);
            Button nine = findViewById(R.id.nine);
            Button ten = findViewById(R.id.ten);

            one.setOnClickListener(Levels);
        }
    }

    private void Level_one(){
        Intent i = new Intent(Levels_activity.this, PlayActivity.class);
        i.putExtra("number", 1);
        startActivity(i);
    }
}