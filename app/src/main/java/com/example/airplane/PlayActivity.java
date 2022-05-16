package com.example.airplane;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PlayActivity extends AppCompatActivity {

    private DrawThread drawThread;
    private Dialog loose_or_win, start;
    private TextView start_text;
    private ImageView helper;
    private int step = 1; // для диалогового начального окна
    private String[] start_phrases; // стартовые фразы

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
    private ImageButton many_bullets, turret, megabullet, green, yellow, blue, red, size;
    private ProgressBar hp_samolet, hp_base;
    private TextView textView, points;

    private View background_win_or_loose;

    private  SpaceshipControllerFragment button_fragment;
    private BlankFragment text_fragment;
    private  FragmentManager fragmentManager;

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

        loose_or_win = new Dialog(this);
        loose_or_win.requestWindowFeature(Window.FEATURE_NO_TITLE); // убираем заголовок
        loose_or_win.setCancelable(false); // нельзя закрыто окно кнопкой назад
        loose_or_win.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loose_or_win.setContentView(R.layout.looser_or_winner);

        start = new Dialog(this);
        start.requestWindowFeature(Window.FEATURE_NO_TITLE); // убираем заголовок
        start.setCancelable(false); // нельзя закрыто окно кнопкой назад
        start.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        start.setContentView(R.layout.start_dialog);
        start.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        start.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        start_text = start.findViewById(R.id.helper_text);
        helper = start.findViewById(R.id.helper);

        button_fragment = new SpaceshipControllerFragment();
        text_fragment = new BlankFragment();
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (number == 10){
            fragmentTransaction.add(R.id.container, text_fragment);
            fragmentTransaction.hide(text_fragment);
        }
        fragmentTransaction.add(R.id.container, button_fragment);
        fragmentTransaction.commit();

        Resources res = getResources();
        switch (number){
            case 1:
                start_phrases = res.getStringArray(R.array.start_1);
                start_phrases[0] +=
                        context.getSharedPreferences("Main", MODE_PRIVATE).getString(MainActivity.nickname, "") +
                        "!";
                helper.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.helper1, null));
                start_text.setMaxLines(2);
                break;
            case 2:
                start_phrases = res.getStringArray(R.array.start_2);
                helper.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.helper2, null));
                break;
            case 3:
                start_phrases = res.getStringArray(R.array.start_3);
                helper.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.helper3, null));
                break;
            case 4:
                start_phrases = res.getStringArray(R.array.start_4);
                helper.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.helper4, null));
                break;
            case 5:
                start_phrases = res.getStringArray(R.array.start_5);
                helper.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.helper5, null));
                break;
            case 6:
                start_phrases = res.getStringArray(R.array.start_6);
                helper.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.helper6, null));
                break;
            case 7:
                start_phrases = res.getStringArray(R.array.start_7);
                helper.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.helper7, null));
                break;
            case 8:
                start_phrases = res.getStringArray(R.array.start_8);
                helper.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.helper8, null));
                break;
            case 9:
                start_phrases = res.getStringArray(R.array.start_9);
                helper.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.helper9, null));
                break;
            case 10:
                start_phrases = res.getStringArray(R.array.start_10);
                start_phrases[1] += " " +
                        context.getSharedPreferences("Main", MODE_PRIVATE).getString(MainActivity.nickname, "") +
                                "!";
                break;
        }

        if (number != 99)
            start_text.setText(start_phrases[0]);

        ConstraintLayout start_dialog_layout = start.findViewById(R.id.start_dialog_layout);
        start_dialog_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (step < start_phrases.length){
                    start_text.setText(start_phrases[step]);
                    if (number == 1)
                        start_text.setMaxLines(10);
                }
                else if (step == start_phrases.length){
                    start.hide();
                    drawThread.start();
                }
                step++;
            }
        });


        background_win_or_loose = loose_or_win.findViewById(R.id.frameLayout);
        Button menu = loose_or_win.findViewById(R.id.menu);
        Button retry = loose_or_win.findViewById(R.id.retry);
        next = loose_or_win.findViewById(R.id.next);
        next.setBackgroundColor(Color.BLUE);

        green_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.green_pressed);
        green_not_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.green_not_pressed);
        red_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.red_pressed);
        red_not_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.red_not_pressed);
        yellow_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.yellow_pressed);
        yellow_not_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.yellow_not_pressed);
        blue_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.blue_pressed);
        blue_not_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.blue_not_pressed);

        size = findViewById(R.id.size_button);
        points = findViewById(R.id.points);

        // Looper.prepare();
        Looper looper = Looper.myLooper();
        handler = new Handler(looper) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0: // проиграл или выиграл
                        drawThread.interrupt();
                        win_or_loose_dialog(msg.arg1, msg.arg2);
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
                        start_options();
                        break;
                    case 3:
                        update_abilities(msg.arg1);
                        break;
                    case 4:
                        FragmentTransaction t = fragmentManager.beginTransaction();
                        t.hide(button_fragment);
                        t.show(text_fragment);
                        t.commit();
                        break;
                    case 5:
                        FragmentTransaction t1 = fragmentManager.beginTransaction();
                        t1.hide(text_fragment);
                        t1.show(button_fragment);
                        t1.commit();
                        break;
                    case 6: // arg1 - номер фразы
                        textView.setText(start_phrases[msg.arg1]);
                        if (msg.arg1 == 2){
                            textView.setTextColor(getResources().getColor(R.color.helper, null));
                        } else {
                            textView.setTextColor(getResources().getColor(R.color.boss, null));
                        }
                        break;
                    case 7:
                        points.setText(Integer.toString(msg.arg1));
                        break;
                    case 8:
                        change_base();
                        break;
                }
            }
        };

        play_field = findViewById(R.id.play_field); //поле для рисования
        play_field.setZOrderOnTop (true); // Установить фон холста прозрачным
        play_field.getHolder().setFormat(PixelFormat.TRANSLUCENT);

        play_field.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                width = play_field.getWidth();
                height = play_field.getHeight();
                drawThread = create_new_thread(width, height, number);
                if (number != 10 && number != 99) {
                    if (!isFinishing())
                        start.show();
                } else {
                    drawThread.start();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

            }
        });

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

    }

    private void change_base() {
        LinearLayout layout = findViewById(R.id.base);
        String name_base = getSharedPreferences("Main", MODE_PRIVATE).getString("base", "");
        switch (name_base) {
            case "default_base":
                layout.setBackgroundResource(R.drawable.base);
                break;
            case "cool_base":
                layout.setBackgroundResource(R.drawable.blue_base);
                break;
            case "gold_base":
                layout.setBackgroundResource(R.drawable.gold_base);
                break;
        }
    }

    private void add_to_table(int points) {
        MainActivity.mDataBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> players = (HashMap<String, Object>) dataSnapshot.getValue();
                HashMap<String, Object> update = new HashMap<>();
                int k = 0;
                for (Map.Entry<String, Object> entry : players. entrySet()){
                    Player current = dataSnapshot.child(entry.getKey()).getValue(Player.class);
                    if (current.getPoints() < points){
                        current.addNumber(1);
                        k++;
                        if (current.getNumber() != MainActivity.rate_table_kolvo){
                            update.put(entry.getKey(), current);
                        } else {
                            MainActivity.mDataBase.child(entry.getKey()).removeValue();
                        }
                    }
                }
                MainActivity.mDataBase.push().setValue(
                        new Player(getSharedPreferences("Main", MODE_PRIVATE).getString(MainActivity.nickname, ""),
                                MainActivity.mDataBase.getKey(), MainActivity.rate_table_kolvo-k, points));
                if (update.size() != 0){
                    MainActivity.mDataBase.updateChildren(update);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void update_abilities(int last_ability_time) {
        if (last_ability_time >= Params.time_manybullets && number > 2){
            many_bullets.setVisibility(View.VISIBLE);
        }
        if (last_ability_time >= Params.time_turret && number > 3){
            turret.setVisibility(View.VISIBLE);
        }
        if (last_ability_time >= Params.time_megabullet && number > 4){
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

    public void win_or_loose_dialog(int win_or_loose, int points){
        switch(win_or_loose){
            case 0:
                next.setBackgroundColor(Color.GRAY);
                next.setClickable(false);
                background_win_or_loose.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.dialog_loose, null));
                if (number == 99){
                    TextView one = loose_or_win.findViewById(R.id.points_1);
                    TextView two = loose_or_win.findViewById(R.id.points_2);
                    one.setText(getResources().getString(R.string.inf_loose));
                    two.setText(Integer.toString(points));
                    add_to_table(points);
                    update_record(points);
                }
                if (!isFinishing())
                    loose_or_win.show();
                break;
            case 1:
                SharedPreferences pref = getSharedPreferences("Main", MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                if (number == 10){
                    next.setBackgroundColor(Color.GRAY);
                    next.setClickable(false);
                    edit.putBoolean("level_inf", true);
                } else {
                    edit.putBoolean("level_" + (number + 1), true);
                }
                edit.apply();
                background_win_or_loose.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.dialog_win, null));
                if (!isFinishing())
                    loose_or_win.show();
                break;
        }
    }

    private void update_record(int points) {
        SharedPreferences pref = getSharedPreferences("Main", MODE_PRIVATE);
        if (points > pref.getInt("record", 0)){
            pref.edit().putInt("record", points).apply();
        }
    }

    private void start_options(){

        green = findViewById(R.id.green);
        red = findViewById(R.id.red);
        yellow = findViewById(R.id.yellow);
        blue = findViewById(R.id.blue);

        green.setBackground(green_pressed);
        red.setBackground(red_not_pressed);
        blue.setBackground(blue_not_pressed);
        yellow.setBackground(yellow_not_pressed);

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


        green.setOnClickListener(Colors_b);
        red.setOnClickListener(Colors_b);
        yellow.setOnClickListener(Colors_b);
        blue.setOnClickListener(Colors_b);

        ImageButton up_button = (ImageButton) findViewById(R.id.up_button);
        ImageButton down_button = (ImageButton) findViewById(R.id.down_button);
        ImageButton shot = (ImageButton) findViewById(R.id.shot2);

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

        textView = findViewById(R.id.boss);
        hp_samolet = findViewById(R.id.samolet_hp);
        hp_base = findViewById(R.id.base_hp);

        next.setBackgroundColor(Color.BLUE); // TODO: не забыть изменить
        next.setClickable(true);
        drawThread.change_bullet_color(1);
        if (number == 1)
            size.setVisibility(View.INVISIBLE);

        System.out.println(getSharedPreferences("Main", MODE_PRIVATE).getString("ship", ""));
        hp_samolet.setProgress(drawThread.get_Samolet().get_hp());
        hp_base.setProgress(drawThread.get_base().get_hp());
        many_bullets.setVisibility(View.INVISIBLE);
        megabullet.setVisibility(View.INVISIBLE);
        turret.setVisibility(View.INVISIBLE);
        green.setImageDrawable(green_pressed);
        red.setImageDrawable(red_not_pressed);
        blue.setImageDrawable(blue_not_pressed);
        yellow.setImageDrawable(yellow_not_pressed);
        size.setImageBitmap(Params.Bullets[0]);
        int k = size.getHeight()/10;
        size.setPadding(3*k,3*k, 3*k, 3*k);

        if (number != 99){
            points.setVisibility(View.INVISIBLE);
        } else {
            points.setText(Integer.toString(0));
        }
    }

    @Override
    protected void onStop() {
        //Looper.myLooper().quitSafely();
        loose_or_win.dismiss();
        start.dismiss();
        drawThread.interrupt();
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        loose_or_win.dismiss();
        start.dismiss();
        drawThread.interrupt();
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
