package com.example.airplane.Activities;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
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
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.airplane.Fragments.TextFragment;
import com.example.airplane.DrawThread;
import com.example.airplane.MusicResorces;
import com.example.airplane.Params;
import com.example.airplane.Player;
import com.example.airplane.R;
import com.example.airplane.Fragments.SpaceshipControllerFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayActivity extends AppCompatActivity {

    private DrawThread drawThread;
    private Dialog loose_or_win, start, close_level;
    private TextView start_text;
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
    private TextView boss_text, points;

    private View background_win_or_loose;

    private SpaceshipControllerFragment button_fragment;
    private TextFragment text_fragment;
    private  FragmentManager fragmentManager;

    private boolean stop = false, pause = false;
    private static boolean drawThreadAlreadyExist;
    public static boolean TextFragmentAlreadyAdded;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (!start.isShowing())
            stop = true;
        super.onStop();
    }

    @Override
    protected void onPause() {
        MusicResorces.battle_s.pause();
        if (!start.isShowing()) {
            drawThread.set_pause(true);
            pause = true;
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (start != null && !start.isShowing()) {
            if (pause && stop) {
                start.dismiss();
                if (!loose_or_win.isShowing() && !start.isShowing()) {
                    if (close_level == null) {
                        create_close_dialog();
                    }
                    close_level.show();
                }
                pause = false;
                stop = false;
            }
        } else {
            MusicResorces.battle_s.start();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        loose_or_win.dismiss();
        start.dismiss();
        if (close_level != null)
            close_level.dismiss();

        MusicResorces.start_menu();

        drawThread.interrupt();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (!loose_or_win.isShowing() && !start.isShowing()) {
            if (close_level == null) {
                create_close_dialog();
            }
            if (!close_level.isShowing()) {
                close_level.show();
                drawThread.set_pause(true);
                MusicResorces.battle_s.pause();
            }
        }
    }

    private void create_close_dialog() {
        close_level = new Dialog(this);
        close_level.requestWindowFeature(Window.FEATURE_NO_TITLE);
        close_level.setCancelable(false);
        close_level.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        close_level.setContentView(R.layout.close_level);

        Button continue_b = close_level.findViewById(R.id.continue_button),
                break_b = close_level.findViewById(R.id.break_button);

        continue_b.setOnClickListener(v -> {
            MusicResorces.soundPool.play(MusicResorces.button_s1, 1, 1, 0, 0, 1);
            drawThread.set_pause(false);
            MusicResorces.battle_s.start();
            close_level.dismiss();
        });

        break_b.setOnClickListener(v -> {
            MusicResorces.soundPool.play(MusicResorces.button_s1, 1, 1, 0, 0, 1);
            drawThread.interrupt();
            close_level.dismiss();
            finish();
        });
    }

    private void init_tints_for_buttons() {
        green_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.green_pressed);
        green_not_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.green_not_pressed);
        red_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.red_pressed);
        red_not_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.red_not_pressed);
        yellow_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.yellow_pressed);
        yellow_not_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.yellow_not_pressed);
        blue_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.blue_pressed);
        blue_not_pressed = getTintedDrawable(context, R.drawable.circle_button, R.color.blue_not_pressed);
    }

    private void create_abilities() {

        // 0 - большой; 1 - нормальный; 2 - маленький

        size = findViewById(R.id.size_button);
        size.setOnClickListener(v -> {
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
        });

        turret = findViewById(R.id.turret);
        turret.setOnClickListener(v -> {
            drawThread.create_turret();
            drawThread.set_last_udpate_time(System.currentTimeMillis());
            turret.setVisibility(View.INVISIBLE);
            megabullet.setVisibility(View.INVISIBLE);
            many_bullets.setVisibility(View.INVISIBLE);
        });

        many_bullets = findViewById(R.id.many_bullets);
        many_bullets.setOnClickListener(v -> {
            drawThread.create_many_bullet();
            drawThread.set_last_udpate_time(System.currentTimeMillis());
            turret.setVisibility(View.INVISIBLE);
            megabullet.setVisibility(View.INVISIBLE);
            many_bullets.setVisibility(View.INVISIBLE);
        });

        megabullet = findViewById(R.id.megabullet);
        megabullet.setOnClickListener(v -> {
            drawThread.create_megabullet();
            drawThread.set_last_udpate_time(System.currentTimeMillis());
            turret.setVisibility(View.INVISIBLE);
            megabullet.setVisibility(View.INVISIBLE);
            many_bullets.setVisibility(View.INVISIBLE);
        });
    }

    private void create_surface_view() {
        play_field = findViewById(R.id.play_field); //поле для рисования
        play_field.setZOrderOnTop (true); // Установить фон холста прозрачным
        play_field.getHolder().setFormat(PixelFormat.TRANSLUCENT);

        play_field.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                if (!PlayActivity.drawThreadAlreadyExist) {
                    width = play_field.getWidth();
                    height = play_field.getHeight();
                    if (number != 10 && number != 99) {
                        if (!isFinishing()) {
                            start.show();
                        }
                    } else {
                        drawThread = create_new_thread(width, height, number);
                        drawThread.start();
                    }
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

            }
        });
    }

    private void create_loose_or_win_dialog() {
        loose_or_win = new Dialog(this);
        loose_or_win.requestWindowFeature(Window.FEATURE_NO_TITLE); // убираем заголовок
        loose_or_win.setCancelable(false); // нельзя закрыто окно кнопкой назад
        loose_or_win.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loose_or_win.setContentView(R.layout.looser_or_winner);

        background_win_or_loose = loose_or_win.findViewById(R.id.frameLayout);
        Button menu = loose_or_win.findViewById(R.id.continue_button);
        Button retry = loose_or_win.findViewById(R.id.retry);
        next = loose_or_win.findViewById(R.id.break_button);
        next.setBackgroundColor(Color.BLUE);

        retry.setOnClickListener(v -> {
            MusicResorces.soundPool.play(MusicResorces.button_s1, 1, 1, 0, 0, 1);
            loose_or_win.dismiss();
            update = true;
            create_helper_dialog();
            if (number != 10){
                start.show();
            } else {
                drawThread = create_new_thread(width, height, number);
                drawThread.start();
            }
        });

        menu.setOnClickListener(v -> {
            MusicResorces.soundPool.play(MusicResorces.button_s1, 1, 1, 0, 0, 1);
            loose_or_win.dismiss();
            finish();
        });

        next.setOnClickListener(v -> {
            MusicResorces.soundPool.play(MusicResorces.button_s1, 1, 1, 0, 0, 1);
            number++;
            loose_or_win.dismiss();
            update = true;
            create_helper_dialog();
            if (number != 10){
                start.show();
            } else {
                drawThread = create_new_thread(width, height, number);
                drawThread.start();
            }
        });
    }

    private void create_helper_dialog() {
        step = 1;
        start = new Dialog(this);
        start.requestWindowFeature(Window.FEATURE_NO_TITLE); // убираем заголовок
        start.setCancelable(false); // нельзя закрыто окно кнопкой назад
        Window start_window = start.getWindow();
        start_window.setBackgroundDrawableResource(android.R.color.transparent);
        start.setContentView(R.layout.start_dialog);
        start_window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        start_window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        start_text = start.findViewById(R.id.helper_text);
        ImageView helper = start.findViewById(R.id.helper);

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
        start_dialog_layout.setOnClickListener(v -> {
            if (step < start_phrases.length){
                start_text.setText(start_phrases[step]);
                if (number == 1)
                    start_text.setMaxLines(10);
            }
            else if (step == start_phrases.length){
                start.dismiss();
                //System.out.println(start.isShowing());

                drawThread = create_new_thread(width, height, number);
                drawThread.start();
            }
            step++;
        });

    }

    private void fragments() {
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (update){
            if (number == 10){
                if (!PlayActivity.TextFragmentAlreadyAdded) {
                    fragmentTransaction.add(R.id.container, text_fragment);
                    fragmentTransaction.hide(text_fragment);
                    PlayActivity.TextFragmentAlreadyAdded = true;
                }
            }
        } else {
            button_fragment = new SpaceshipControllerFragment();
            text_fragment = new TextFragment();
            if (number == 10){
                fragmentTransaction.add(R.id.container, text_fragment);
                fragmentTransaction.hide(text_fragment);
                PlayActivity.TextFragmentAlreadyAdded = true;
            }
            fragmentTransaction.add(R.id.container, button_fragment);
        }
        fragmentTransaction.commit();
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
                for (Map.Entry<String, Object> entry : players.entrySet()){
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
                if (k != 0) {
                    MainActivity.mDataBase.push().setValue(
                            new Player(getSharedPreferences("Main", MODE_PRIVATE).getString(MainActivity.nickname, ""),
                                    MainActivity.mDataBase.getKey(), MainActivity.rate_table_kolvo - k, points));
                }
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
        if (last_ability_time + 100 >= Params.time_manybullets && number > 2){
            many_bullets.setVisibility(View.VISIBLE);
        }
        if (last_ability_time + 100 >= Params.time_turret && number > 3){
            turret.setVisibility(View.VISIBLE);
        }
        if (last_ability_time + 100 >= Params.time_megabullet && number > 4){
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
        if (!PlayActivity.drawThreadAlreadyExist)
            return new DrawThread(play_field.getHolder(), context, width, height, handler, number);
        else {
            throw new NullPointerException();
        }
    }

    public void win_or_loose_dialog(int win_or_loose, int points){
        switch(win_or_loose){
            case 0:
                next.setBackgroundColor(Color.GRAY);
                next.setClickable(false);
                background_win_or_loose.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.dialog_loose, null));
                if (number == 99){
                    TextView one = loose_or_win.findViewById(R.id.text_1);
                    TextView two = loose_or_win.findViewById(R.id.text_2);
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
                    edit.putBoolean("level_inf", true).apply();
                } else {
                    edit.putBoolean("level_" + (number + 1), true).apply();
                }

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

        View.OnClickListener Colors_b = v -> {
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
        };


        green.setOnClickListener(Colors_b);
        red.setOnClickListener(Colors_b);
        yellow.setOnClickListener(Colors_b);
        blue.setOnClickListener(Colors_b);

        ImageButton up_button = findViewById(R.id.up_button);
        ImageButton down_button = findViewById(R.id.down_button);
        ImageButton shot = findViewById(R.id.shot2);

        down_button.setOnTouchListener((v, event) -> {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    drawThread.get_Samolet().set_updown(true, 1);
                    break;
                case MotionEvent.ACTION_UP:
                    drawThread.get_Samolet().set_updown(false, 1);
                    break;
            }
            return true;
        });

        up_button.setOnTouchListener((v, event) -> {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    drawThread.get_Samolet().set_updown(true, -1);
                    break;
                case MotionEvent.ACTION_UP:
                    drawThread.get_Samolet().set_updown(false, -1);
                    break;
            }
            return true;
        });


        shot.setOnTouchListener((v, event) -> {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    drawThread.setShot_button(true);
                    break;
                case MotionEvent.ACTION_UP:
                    drawThread.setShot_button(false);
                    break;
            }
            return true;
        });

        //boss_text = findViewById(R.id.boss);
        hp_samolet = findViewById(R.id.samolet_hp);
        hp_base = findViewById(R.id.base_hp);

        next.setBackgroundColor(Color.BLUE);
        next.setClickable(true);
        drawThread.change_bullet_color(1);
        if (number == 1)
            size.setVisibility(View.INVISIBLE);
        else
            size.setVisibility(View.VISIBLE);

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

    public static void setDrawThreadAlreadyExist(boolean drawThreadAlreadyExist) {
        PlayActivity.drawThreadAlreadyExist = drawThreadAlreadyExist;
    }

    private boolean update = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MusicResorces.menu_s.pause();
        MusicResorces.setMenu_start(false);
        MusicResorces.battle_s.seekTo(0);
        MusicResorces.play_loop(MusicResorces.battle_s);

        new Params(getApplicationContext(), number);
        Objects.requireNonNull(getSupportActionBar()).hide(); //убираем title
        setContentView(R.layout.playactivity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = getApplicationContext();
        number = getIntent().getExtras().getInt("number");

        init_tints_for_buttons();

        points = findViewById(R.id.points);
        Looper looper = Looper.myLooper();
        handler = new Handler(looper) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0: // проиграл или выиграл
                        drawThread.interrupt();
                        win_or_loose_dialog(msg.arg1, msg.arg2);
                        break;
                    case 1:
                        switch (msg.arg1) {
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
                       // FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().hide(button_fragment).show(text_fragment).commit();
                        break;
                    case 5:
                       // FragmentManager fragmentManager1 = getFragmentManager();
                        fragmentManager.beginTransaction().hide(text_fragment).show(button_fragment).commit();
                        //fragmentManager1.beginTransaction().replace(R.id.container, button_fragment).commit();
                        break;
                    case 6: // arg1 - номер фразы
                        boss_text = findViewById(R.id.boss);
                        boss_text.setText(start_phrases[msg.arg1]);
                        if (msg.arg1 == 2) {
                            boss_text.setTextColor(getResources().getColor(R.color.helper, null));
                        } else {
                            boss_text.setTextColor(getResources().getColor(R.color.boss, null));
                        }
                        break;
                    case 7:
                        points.setText(Integer.toString(msg.arg1));
                        break;
                    case 8:
                        change_base();
                        break;
                    case 9:
                        if (update){
                            create_loose_or_win_dialog();
                            fragments();
                        }
                        break;
                }
            }
        };

        PlayActivity.drawThreadAlreadyExist = false;
        PlayActivity.TextFragmentAlreadyAdded = false;
        create_loose_or_win_dialog();
        create_helper_dialog();
        fragments();
        create_abilities();
        create_surface_view();
    }

}
