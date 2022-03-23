package com.example.airplane;

import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

        ImageView hp_samolet_image = findViewById(R.id.samolet_hp);
        ImageView hp_base_image = findViewById(R.id.base_hp);
        Bitmap hp_bitmap = ImageResource.HP.getBitmap(getApplicationContext()); //хп самолета и базы
        hp_samolet_image.setImageBitmap(Bitmap.createBitmap(hp_bitmap, 0, 0, hp_bitmap.getWidth(), hp_bitmap.getHeight()));
        hp_base_image.setImageBitmap(Bitmap.createBitmap(hp_bitmap, 0, 0, hp_bitmap.getWidth(), hp_bitmap.getHeight()));

        Looper looper = Looper.myLooper();
        Handler handler = new Handler(looper) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        Bitmap new_hp = Bitmap.createBitmap(hp_bitmap, 0, 0, hp_bitmap.getWidth()*msg.arg1/1000, hp_bitmap.getHeight());
                        hp_samolet_image.setImageBitmap(new_hp);
                        break;
                    case 2:
                        Bitmap new_hp1 = Bitmap.createBitmap(hp_bitmap, 0, 0, hp_bitmap.getWidth()*msg.arg1/1000, hp_bitmap.getHeight());
                        hp_base_image.setImageBitmap(new_hp1);
                        break;
                }
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
