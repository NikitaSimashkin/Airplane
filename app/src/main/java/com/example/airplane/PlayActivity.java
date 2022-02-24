package com.example.airplane;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Objects;

public class PlayActivity extends AppCompatActivity {
    private Play_field play_field;

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Objects.requireNonNull(getSupportActionBar()).hide(); //убираем title
        setContentView(R.layout.playactivity);

        ImageButton up_button = (ImageButton) findViewById(R.id.up_button);
        ImageButton down_button = (ImageButton) findViewById(R.id.down_button);

         //создание самолета и прорисовка его
            Samolet samolet = new Samolet();

            ConstraintLayout play_layout = (ConstraintLayout) findViewById(R.id.play_layout); //делаем прозрачный фон
            play_field = new Play_field(PlayActivity.this); //добавляем surfaceview в лэйаут
            play_layout.addView(play_field);

        { //блок обработки кнопок
            down_button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    change_xy_ship(play_field.getHeight()/30, play_field.getHeight()/30);
                }
            });

            up_button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    change_xy_ship(-play_field.getHeight()/30, -play_field.getHeight()/30);
                }
            });
        }
    }

    public void change_xy_ship(int top, int bottom){
        play_field.change_xy_ship(top, bottom);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
