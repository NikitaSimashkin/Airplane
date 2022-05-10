package com.example.airplane;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences pref;
    public static final String nickname = "Name", diff = "difficult", PLAYERS = "Players";
    public static final int rate_table_kolvo = 20;

    private Dialog options, table;
    private ImageButton close;
    private String player;
    private EditText name;
    private Player[] list_info = new Player[MainActivity.rate_table_kolvo];
    private Table_adapter adapter;
    private VideoView video;

    public static final DatabaseReference mDataBase = FirebaseDatabase.getInstance("https://spacewar-8bb7b-default-rtdb.firebaseio.com/").getReference(PLAYERS);

    @Override
    protected void onResume() {
        super.onResume();
      //  create_video();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //video.stopPlayback();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide(); //убирает title_bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        { //обработчик кнопки, который вызывает вторую активити playactivity
            Button play_button = (Button) findViewById(R.id.play_button);
            play_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, Levels_activity.class);
                    startActivity(i);
                }
            });
        }

       //video.start();

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

        close = options.findViewById(R.id.close);
        ImageButton accept = options.findViewById(R.id.accept);
        ImageButton opt = findViewById(R.id.options);

        name = options.findViewById(R.id.name);

        RadioButton easy = options.findViewById(R.id.easy);
        RadioButton normal = options.findViewById(R.id.normal);
        RadioButton hard = options.findViewById(R.id.hard);

        opt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options.show();
                name.setText(pref.getString(nickname, ""));
                int difficult = pref.getInt(diff, 0);
                switch (difficult){
                    case 0:
                        easy.setChecked(true);
                        break;
                    case 1:
                        normal.setChecked(true);
                        break;
                    case 2:
                        hard.setChecked(true);
                        break;
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player = pref.getString(nickname, "");
//                SharedPreferences.Editor edit = pref.edit();
//                edit.clear();
//                edit.apply();
                if (!player.equals(""))
                    options.dismiss();
            }
        });

        /*
        difficult - 0 easy; 1 - normal; 2 - hard
         */
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        if (pref.getString("ship", "").equals("")){
            SharedPreferences.Editor edit = pref.edit();
            edit.putString("ship", "default_ship");
            edit.putString("bullet", "default_bullet");
            edit.putString("base", "default_base");
            edit.apply();
        }

        Button shop = findViewById(R.id.shop);
        final int[] i = {0};
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Shop.class);
                startActivity(i);
//                mDataBase.push().setValue(new Player("None", mDataBase.getKey(), i[0], 0));
//                i[0]++;
//                pref.edit().putBoolean("level_inf", true).apply();
            }
        });

        pref.edit().putBoolean("level_inf", true).apply();
        Button rate_table = findViewById(R.id.rate_table);

        rate_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (table == null)
                    table = create_rate_table();
                get_info();
                table.show();
            }
        });

    }

    public Dialog create_rate_table(){

        Dialog t = new Dialog(this);
        t.requestWindowFeature(Window.FEATURE_NO_TITLE); // убираем заголовок
        t.setCancelable(false); // нельзя закрыть окно кнопкой назад
        t.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        t.setContentView(R.layout.rate_table);

        ImageButton close_table = t.findViewById(R.id.close_table);
        close_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFinishing())
                    t.dismiss();
            }
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

    private void get_info(){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Player player = ds.getValue(Player.class);
                    if (player != null){
                        list_info[player.getNumber()] = player;
                    }
                    i++;
                }
                adapter.change(list_info);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mDataBase.addListenerForSingleValueEvent(valueEventListener);

    }

    private void create_video(){
       // video = findViewById(R.id.videoView);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.back_video;
        video.setVideoURI(Uri.parse(path));
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
    }
}