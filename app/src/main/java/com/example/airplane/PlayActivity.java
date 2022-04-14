package com.example.airplane;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.Objects;

public class PlayActivity extends AppCompatActivity {

    private DrawThread drawThread;
    private Dialog loose_or_win;

    protected SurfaceView play_field;
    protected Handler handler;

    private int number, current_size = 1;

    private Context context;

    private Drawable green_pressed, green_not_pressed,
            red_pressed, red_not_pressed,
            yellow_pressed, yellow_not_pressed,
            blue_pressed, blue_not_pressed;


    private int width, height;

    private Button next;
    private ImageButton many_bullets, turret, megabullet;

    private View background_win_or_loose;

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

        loose_or_win = new Dialog(this);
        loose_or_win.requestWindowFeature(Window.FEATURE_NO_TITLE); // убираем заголовок
        loose_or_win.setCancelable(false); // нельзя закрыто окно кнопкой назад
        loose_or_win.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loose_or_win.setContentView(R.layout.looser_or_winner);


        background_win_or_loose = loose_or_win.findViewById(R.id.frameLayout);
        Button menu = loose_or_win.findViewById(R.id.menu);
        Button retry = loose_or_win.findViewById(R.id.retry);
        next = loose_or_win.findViewById(R.id.next);
        next.setBackgroundColor(Color.BLUE);

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

        green.setBackground(green_pressed);
        red.setBackground(red_not_pressed);
        blue.setBackground(blue_not_pressed);
        yellow.setBackground(yellow_not_pressed);


        ImageButton size = findViewById(R.id.size_button);
        int k = size.getHeight();
        size.setPadding(3*k/10,3*k/10, 3*k/10, 3*k/10);

        // Looper.prepare();
        Looper looper = Looper.myLooper();
        handler = new Handler(looper) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0: // проиграл или выиграл
                        drawThread.interrupt();
                        win_or_loose_dialog(msg.arg1);
                        break;
                    case 1:
                        switch(msg.arg1) {
                            case 0:
                                hp_samolet.setProgress(msg.arg2);
                                break;
                            case 1:
                                hp_base.setProgress(msg.arg2);
                                break;
                        }
                        break;
                    case 2:
                        next.setBackgroundColor(Color.BLUE); // TODO: не забыть изменить
                        next.setClickable(true);
                        many_bullets.setVisibility(View.INVISIBLE);
                        megabullet.setVisibility(View.INVISIBLE);
                        turret.setVisibility(View.INVISIBLE);
                        hp_samolet.setProgress(drawThread.get_Samolet().get_hp());
                        hp_base.setProgress(drawThread.get_base().get_hp());
                        green.setImageDrawable(green_pressed);
                        red.setImageDrawable(red_not_pressed);
                        blue.setImageDrawable(blue_not_pressed);
                        yellow.setImageDrawable(yellow_not_pressed);
                        size.setImageBitmap(Params.Bullets[0]);
                        int k = size.getHeight()/10;
                        size.setPadding(3*k,3*k, 3*k, 3*k);
                        break;
                    case 3:
                        update_abilities(msg.arg1);
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
                drawThread = create_new_thread(width, height, number);
                drawThread.start();
                drawThread.change_bullet_color(1);
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
                drawThread = create_new_thread(width, height, number);
                drawThread.start();
                loose_or_win.hide();
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loose_or_win.hide();
                Intent i = new Intent(PlayActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawThread = create_new_thread(width, height, number+1);
                number++;
                drawThread.start();
                loose_or_win.hide();
            }
        });

        turret = findViewById(R.id.turret);

        turret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawThread.create_turret();
                drawThread.set_last_udpate_time(System.currentTimeMillis());
                turret.setVisibility(View.INVISIBLE);
                megabullet.setVisibility(View.INVISIBLE);
                many_bullets.setVisibility(View.INVISIBLE);
            }
        });

        View.OnClickListener Colors_b = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.green:
                        drawThread.change_bullet_color(1);
                        green.setImageDrawable(green_pressed);
                        red.setImageDrawable(red_not_pressed);
                        yellow.setImageDrawable(yellow_not_pressed);
                        blue.setImageDrawable(blue_not_pressed);
                        size.setImageBitmap(Params.Bullets[0]);
                        break;
                    case R.id.red:
                        drawThread.change_bullet_color(2);
                        green.setImageDrawable(green_not_pressed);
                        red.setImageDrawable(red_pressed);
                        yellow.setImageDrawable(yellow_not_pressed);
                        blue.setImageDrawable(blue_not_pressed);
                        size.setImageBitmap(Params.Bullets[1]);
                        break;
                    case R.id.yellow:
                        drawThread.change_bullet_color(3);
                        green.setImageDrawable(green_not_pressed);
                        red.setImageDrawable(red_not_pressed);
                        yellow.setImageDrawable(yellow_pressed);
                        blue.setImageDrawable(blue_not_pressed);
                        size.setImageBitmap(Params.Bullets[2]);
                        break;
                    case R.id.blue:
                        drawThread.change_bullet_color(4);
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

        many_bullets = findViewById(R.id.many_bullets);
        megabullet = findViewById(R.id.megabullet);

        many_bullets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawThread.create_many_bullet();
                drawThread.set_last_udpate_time(System.currentTimeMillis());
                turret.setVisibility(View.INVISIBLE);
                megabullet.setVisibility(View.INVISIBLE);
                many_bullets.setVisibility(View.INVISIBLE);
            }
        });

        megabullet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawThread.create_megabullet();
                drawThread.set_last_udpate_time(System.currentTimeMillis());
                turret.setVisibility(View.INVISIBLE);
                megabullet.setVisibility(View.INVISIBLE);
                many_bullets.setVisibility(View.INVISIBLE);
            }
        });

        green.setOnClickListener(Colors_b);
        red.setOnClickListener(Colors_b);
        yellow.setOnClickListener(Colors_b);
        blue.setOnClickListener(Colors_b);
    }

    private void update_abilities(int last_ability_time) {
        if (last_ability_time >= Params.time_manybullets){
            many_bullets.setVisibility(View.VISIBLE);
        }
        if (last_ability_time >= Params.time_turret){
            turret.setVisibility(View.VISIBLE);
        }
        if (last_ability_time >= Params.time_megabullet){
            megabullet.setVisibility(View.VISIBLE);
        }
    }


    public static Drawable getTintedDrawable(Context context, int drawableRes, int colorRes) {
        Drawable d = ContextCompat.getDrawable(context, drawableRes);
        d = DrawableCompat.wrap(d);
        DrawableCompat.setTint(d.mutate(), ContextCompat.getColor(context, colorRes));
        return d;
    }

    public DrawThread create_new_thread(int width, int height, int number){
        return new DrawThread(play_field.getHolder(), getApplicationContext(), width, height, handler, number);
    }

    public void win_or_loose_dialog(int win_or_loose){
        switch(win_or_loose){
            case 0:
                next.setBackgroundColor(Color.GRAY);
                next.setClickable(false);
                background_win_or_loose.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.dialog_loose, null));
                loose_or_win.show();
                break;
            case 1:
                if (number == 10){
                    next.setBackgroundColor(Color.GRAY);
                    next.setClickable(false);
                }
                background_win_or_loose.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.dialog_win, null));
                loose_or_win.show();
                break;
        }
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

}
