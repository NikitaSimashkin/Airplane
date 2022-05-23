package com.example.airplane.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.airplane.ImageResource;
import com.example.airplane.MusicResorces;
import com.example.airplane.Player;
import com.example.airplane.R;
import com.example.airplane.Adapters.Table_adapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences pref;
    public static final String nickname = "Name", diff = "difficult", PLAYERS = "Players", SHIP = "ship",
    BULLET = "bullet", BASE = "base";
    public static final int rate_table_kolvo = 20;

    private Dialog options, table;
    private String player;
    private EditText name;
    private final Player[] list_info = new Player[MainActivity.rate_table_kolvo];
    private Table_adapter adapter;
    private ImageView imageStart;

    public static final DatabaseReference mDataBase = FirebaseDatabase.getInstance("https://spacewar-8bb7b-default-rtdb.firebaseio.com/").getReference(PLAYERS);

    private boolean close = true;

    @Override
    protected void onResume() {
        if (close){
            MusicResorces.menu_s.start();
        }
        close = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (close){
            MusicResorces.menu_s.pause();
        }
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide(); //убирает title_bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        MusicResorces.createMusicResources(getApplicationContext());

        MusicResorces.start_menu();

        // обработчик кнопки, который вызывает вторую активити с уровнями
        Button play_button = findViewById(R.id.play_button);
        play_button.setOnClickListener(v -> {
            MusicResorces.soundPool.play(MusicResorces.button_s1, 1, 1, 0, 0, 1);
            Intent i = new Intent(MainActivity.this, Levels_activity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            close = false;
            startActivity(i);
        });

        // окно магазина
        Button shop = findViewById(R.id.shop);
        shop.setOnClickListener(v -> {
            MusicResorces.soundPool.play(MusicResorces.button_s1, 1, 1, 0, 0, 1);
            Intent i = new Intent(MainActivity.this, ShopActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            close = false;
            startActivity(i);
        });

        // таблица лидеров
        Button rate_table = findViewById(R.id.rate_table);
        rate_table.setOnClickListener(v -> {
            MusicResorces.soundPool.play(MusicResorces.button_s1, 1, 1, 0, 0, 1);
            if (table == null)
                table = create_rate_table();
            get_info();
            table.show();
        });

        create_option_dialog();

        if (pref.getString("ship", "").equals("")){
            SharedPreferences.Editor edit = pref.edit();
            edit.putString(MainActivity.SHIP, "default_ship");
            edit.putString(MainActivity.BULLET, "default_bullet");
            edit.putString(MainActivity.BASE, "default_base");
            edit.apply();
        }
        
    }

    protected void create_option_dialog() {
        options = new Dialog(this);
        options.requestWindowFeature(Window.FEATURE_NO_TITLE); // убираем заголовок
        options.setCancelable(false); // нельзя закрыть окно кнопкой назад
        options.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        options.setContentView(R.layout.nickname);

        pref = getSharedPreferences("Main", MODE_PRIVATE);
        player = pref.getString(nickname, "");
        if (player.equals("")){
            options.show();
        }

        ImageButton close = options.findViewById(R.id.close);
        ImageButton accept = options.findViewById(R.id.accept);
        imageStart = options.findViewById(R.id.image_start);
        ImageButton opt = findViewById(R.id.options);

        name = options.findViewById(R.id.name);

        RadioButton easy = options.findViewById(R.id.easy);
        RadioButton normal = options.findViewById(R.id.normal);
        RadioButton hard = options.findViewById(R.id.hard);

        View.OnClickListener change_image = v -> {
            switch(v.getId()){
                case R.id.easy:
                    imageStart.setImageBitmap(ImageResource.ALIEN.getBitmap(getApplicationContext()));
                    break;
                case R.id.normal:
                    imageStart.setImageBitmap(ImageResource.ALIEN_TWO_WITHOUT_CAP.getBitmap(getApplicationContext()));
                    break;
                case R.id.hard:
                    imageStart.setImageBitmap(ImageResource.BOSS.getBitmap(getApplicationContext()));
            }
        };
        easy.setOnClickListener(change_image);
        normal.setOnClickListener(change_image);
        hard.setOnClickListener(change_image);

        opt.setOnClickListener(v -> {
            MusicResorces.soundPool.play(MusicResorces.button_s1, 1, 1, 0, 0, 1);
            options.show();
            name.setText(pref.getString(nickname, ""));
            int difficult = pref.getInt(diff, 0);
            switch (difficult){
                case 0:
                    easy.setChecked(true);
                    imageStart.setImageBitmap(ImageResource.ALIEN.getBitmap(getApplicationContext()));
                    break;
                case 1:
                    normal.setChecked(true);
                    imageStart.setImageBitmap(ImageResource.ALIEN_TWO_WITHOUT_CAP.getBitmap(getApplicationContext()));
                    break;
                case 2:
                    hard.setChecked(true);
                    imageStart.setImageBitmap(ImageResource.BOSS.getBitmap(getApplicationContext()));
                    break;
            }
        });

        close.setOnClickListener(v -> {
            MusicResorces.soundPool.play(MusicResorces.button_s1, 1, 1, 0, 0, 1);
            player = pref.getString(nickname, "");
            if (!player.equals(""))
                options.dismiss();
        });

        /*
        difficult - 0 easy; 1 - normal; 2 - hard
         */
        accept.setOnClickListener(v -> {
            MusicResorces.soundPool.play(MusicResorces.button_s1, 1, 1, 0, 0, 1);
            SharedPreferences.Editor edit = pref.edit();
            String nick = name.getText().toString();
            if (!nick.equals("") && (easy.isChecked() || normal.isChecked() || hard.isChecked())){
                edit.putString(nickname, nick);
                int difficult;
                if (easy.isChecked())
                    difficult = 0;
                else if (normal.isChecked())
                    difficult = 1;
                else
                    difficult = 2;
                edit.putInt(diff, difficult);
                edit.apply();
                options.dismiss();
            }
        });
    }

    protected Dialog create_rate_table(){

        Dialog t = new Dialog(this);
        t.requestWindowFeature(Window.FEATURE_NO_TITLE); // убираем заголовок
        t.setCancelable(false); // нельзя закрыть окно кнопкой назад
        t.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        t.setContentView(R.layout.rate_table);

        ImageButton close_table = t.findViewById(R.id.close_table);
        close_table.setOnClickListener(v -> {
            MusicResorces.soundPool.play(MusicResorces.button_s1, 1, 1, 0, 0, 1);
            if (!isFinishing())
                t.dismiss();
        });
        RecyclerView rv = t.findViewById(R.id.rating);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);

        {
            adapter = new Table_adapter(MainActivity.this);
            for (int i = 0; i < MainActivity.rate_table_kolvo; i++) {
                list_info[i] = (new Player("None", "0", i, 0));
            }
            adapter.change(list_info);
            adapter.notifyDataSetChanged();
            rv.setAdapter(adapter);
        }
        return t;
    }

    protected void get_info(){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Player player = ds.getValue(Player.class);
                    if (player != null){
                        list_info[player.getNumber()] = player;
                    }
                }
                adapter.change(list_info);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };

        mDataBase.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}