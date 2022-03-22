package com.example.airplane;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Objects;

public class PlayActivity extends AppCompatActivity {
    private DrawThread drawThread;

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
        ImageButton shot = (ImageButton) findViewById(R.id.shot);



        Looper looper = Looper.myLooper();
        Handler handler = new Handler(looper) {

            @Override
            public void handleMessage(@NonNull Message msg) {
                ImageView hp_samolet_image = findViewById(R.id.imageView);
                Bitmap hp_samolet_bitmap = ImageResource.HP_SAMOLET.getBitmap(getApplicationContext());
                Bitmap new_hp = Bitmap.createBitmap(hp_samolet_bitmap, 0, 0, hp_samolet_bitmap.getWidth()/msg.what, hp_samolet_bitmap.getHeight());
                hp_samolet_image.setImageBitmap(new_hp);
            }
        };

        SurfaceView play_field = (SurfaceView) findViewById(R.id.play_field); //поле для рисования


        play_field.setZOrderOnTop (true); // Установить фон холста прозрачным
        play_field.getHolder().setFormat(PixelFormat.TRANSLUCENT);

        play_field.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                int width = play_field.getWidth();
                int height = play_field.getHeight();
                drawThread = new DrawThread(holder, getApplicationContext(), width, height, handler);
                drawThread.start();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

            }
        });

        { //обработка нажатий

            down_button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            drawThread.get_Samolet().set_updown(true, 1);
                            break;
                        case MotionEvent.ACTION_UP:
                            drawThread.get_Samolet().set_updown(false, 1);
                            break;
                    }
                    return true;
                }
            });

            up_button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            drawThread.get_Samolet().set_updown(true, -1);
                            break;
                        case MotionEvent.ACTION_UP:
                            drawThread.get_Samolet().set_updown(false, -1);
                            break;
                    }
                    return true;
                }
            });


            shot.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            drawThread.create_bullets();
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                    }
                    return true;
                }
            });
        }
    }

    @Override
    protected void onStop() {
        drawThread.interrupt();
        Looper.myLooper().quitSafely();
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
