package com.example.airplane;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.Objects;

public class PlayActivity extends AppCompatActivity {

    private DrawThread drawThread;
    private Dialog loose;

    protected SurfaceView play_field;
    protected Handler handler;

    private int number, current_size = 1;

    private Context context;

    private Drawable green_pressed, green_not_pressed,
            red_pressed, red_not_pressed,
            yellow_pressed, yellow_not_pressed,
            blue_pressed, blue_not_pressed;


    private int width, height;

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Params(getApplicationContext());
        Objects.requireNonNull(getSupportActionBar()).hide(); //убираем title
        setContentView(R.layout.playactivity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = getApplicationContext();
        number = getIntent().getExtras().getInt("number");

        ImageButton up_button = (ImageButton) findViewById(R.id.up_button);
        ImageButton down_button = (ImageButton) findViewById(R.id.down_button);
        ImageButton shot = (ImageButton) findViewById(R.id.shot2);

        ProgressBar hp_samolet = findViewById(R.id.samolet_hp);
        ProgressBar hp_base = findViewById(R.id.base_hp);

        Dialog loose = new Dialog(this);
        loose.requestWindowFeature(Window.FEATURE_NO_TITLE); // убираем заголовок
        loose.setCancelable(false); // нельзя закрыто окно кнопкой назад
        loose.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loose.setContentView(R.layout.looser);

        TextView haha = loose.findViewById(R.id.haha);
        TextView points = loose.findViewById(R.id.points);
        Button menu = loose.findViewById(R.id.menu);
        Button retry = loose.findViewById(R.id.retry);

        ImageButton green = findViewById(R.id.green);
        ImageButton red = findViewById(R.id.red);
        ImageButton yellow = findViewById(R.id.yellow);
        ImageButton blue = findViewById(R.id.blue);

        green_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.green_pressed);
        green_not_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.green_not_pressed);
        red_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.red_pressed);
        red_not_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.red_not_pressed);
        yellow_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.yellow_pressed);
        yellow_not_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.yellow_not_pressed);
        blue_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.blue_pressed);
        blue_not_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.blue_not_pressed);

        green.setImageDrawable(green_pressed);
        red.setBackground(red_not_pressed);
        blue.setBackground(blue_not_pressed);
        yellow.setBackground(yellow_not_pressed);


        ImageButton size = findViewById(R.id.size_button);
        int k = size.getHeight();
        System.out.println(k);
        size.setPadding(3*k/10,3*k/10, 3*k/10, 3*k/10);

       // Looper.prepare();
        Looper looper = Looper.myLooper();
        handler = new Handler(looper) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        drawThread.interrupt();
                        loose.show();
                    case 1:
                        hp_samolet.setProgress(msg.arg1);
                        break;
                    case 2:
                        hp_base.setProgress(msg.arg1);
                        break;
                    case 3:
                        int k = size.getHeight()/10;
                        System.out.println(k);
                        size.setPadding(3*k,3*k, 3*k, 3*k);
                        break;
                }
            }
        };

        play_field = (SurfaceView) findViewById(R.id.play_field); //поле для рисования

        play_field.setZOrderOnTop (true); // Установить фон холста прозрачным
        play_field.getHolder().setFormat(PixelFormat.TRANSLUCENT);

        play_field.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                width = play_field.getWidth();
                height = play_field.getHeight();
                drawThread = create_new_thread(width, height);
                drawThread.start();
                drawThread.set_bullet_mode(1);
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

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawThread = create_new_thread(width, height);
                hp_samolet.setProgress(drawThread.get_Samolet().get_hp());
                hp_base.setProgress(drawThread.get_base().get_hp());
                drawThread.start();
                green.setImageDrawable(green_pressed);
                red.setBackground(red_not_pressed);
                blue.setBackground(blue_not_pressed);
                yellow.setBackground(yellow_not_pressed);
                size.setImageBitmap(Params.Bullets[0]);
                loose.hide();
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loose.hide();
                Intent i = new Intent(PlayActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        ImageButton turret = findViewById(R.id.turret);

        turret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawThread.create_turret();
            }
        });

        View.OnClickListener Colors_b = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.green:
                        drawThread.set_bullet_mode(1);
                        green.setImageDrawable(green_pressed);
                        red.setImageDrawable(red_not_pressed);
                        yellow.setImageDrawable(yellow_not_pressed);
                        blue.setImageDrawable(blue_not_pressed);
                        size.setImageBitmap(Params.Bullets[0]);
                        break;
                    case R.id.red:
                        drawThread.set_bullet_mode(2);
                        green.setImageDrawable(green_not_pressed);
                        red.setImageDrawable(red_pressed);
                        yellow.setImageDrawable(yellow_not_pressed);
                        blue.setImageDrawable(blue_not_pressed);
                        size.setImageBitmap(Params.Bullets[1]);
                        break;
                    case R.id.yellow:
                        drawThread.set_bullet_mode(3);
                        green.setImageDrawable(green_not_pressed);
                        red.setImageDrawable(red_not_pressed);
                        yellow.setImageDrawable(yellow_pressed);
                        blue.setImageDrawable(blue_not_pressed);
                        size.setImageBitmap(Params.Bullets[2]);
                        break;
                    case R.id.blue:
                        drawThread.set_bullet_mode(4);
                        green.setImageDrawable(green_not_pressed);
                        red.setImageDrawable(red_not_pressed);
                        yellow.setImageDrawable(yellow_not_pressed);
                        blue.setImageDrawable(blue_pressed);
                        size.setImageBitmap(Params.Bullets[3]);
                        break;
                }
            }
        };
        // 0 - большой; 1 - нормальный; 2 - маленький

        size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k = size.getHeight()/10;
                switch(current_size){
                    case 0:
                        current_size = 2;
                        size.setPadding(4*k,4*k,4*k,4*k);
                        break;
                    case 1:
                        current_size = 0;
                        size.setPadding(2*k, 2*k, 2*k, 2*k);
                        break;
                    case 2:
                        current_size = 1;
                        size.setPadding(3*k, 3*k, 3*k, 3*k);
                        break;
                }
                drawThread.set_bullet_size(current_size);
            }
        });

        green.setOnClickListener(Colors_b);
        red.setOnClickListener(Colors_b);
        yellow.setOnClickListener(Colors_b);
        blue.setOnClickListener(Colors_b);
    }


    public static Drawable getTintedDrawable(Context context, int drawableRes, int colorRes) {
        Drawable d = ContextCompat.getDrawable(context, drawableRes);
        d = DrawableCompat.wrap(d);
        DrawableCompat.setTint(d.mutate(), ContextCompat.getColor(context, colorRes));
        return d;
    }

    public DrawThread create_new_thread(int width, int height){
        return new DrawThread(play_field.getHolder(), getApplicationContext(), width, height, handler, number);
    }

    @Override
    protected void onStop() {
        //Looper.myLooper().quitSafely();
        drawThread.interrupt();
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

    private void Levels(int number){ // а - номер уровня; в зависимости от уровня делаем что то

    }
}
