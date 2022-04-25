package com.example.airplane;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences pref;
    public static final String nickname = "Name", diff = "difficult";
    private Dialog options;
    private ImageButton close;
    private String player;
    private EditText name;

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

    }
}