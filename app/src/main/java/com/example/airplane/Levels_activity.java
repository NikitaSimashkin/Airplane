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
            two.setOnClickListener(Levels);
            three.setOnClickListener(Levels);
            four.setOnClickListener(Levels);
            five.setOnClickListener(Levels);
            six.setOnClickListener(Levels);
            seven.setOnClickListener(Levels);
            eight.setOnClickListener(Levels);
            nine.setOnClickListener(Levels);
            ten.setOnClickListener(Levels);
        }
    }

    private void Levels(int number){
        Intent i = new Intent(Levels_activity.this, PlayActivity.class);
        i.putExtra("number", number);
        startActivity(i);
    }
}