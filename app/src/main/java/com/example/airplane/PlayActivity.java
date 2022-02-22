package com.example.airplane;

import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Objects;

public class PlayActivity extends AppCompatActivity {
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

        up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        { //создание самолета и прорисовка его
            Samolet samolet = new Samolet();

            ConstraintLayout play_layout = (ConstraintLayout) findViewById(R.id.play_layout); //делаем прозрачный фон
            Play_field play_field = new Play_field(PlayActivity.this);

            play_layout.addView(play_field);


        }

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
