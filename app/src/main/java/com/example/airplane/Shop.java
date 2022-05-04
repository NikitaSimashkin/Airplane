package com.example.airplane;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Objects;

public class Shop extends AppCompatActivity {

    private Shop_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide(); //убирает title_bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_shop);

        RecyclerView rv = findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);

        adapter = new Shop_adapter(this);
        rv.setAdapter(adapter);

        Looper looper = Looper.myLooper();
        Handler handler = new Handler(looper){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch(msg.what){
                    case 0:
                        int[] colors = adapter.get_colors();
                        for (int i = 0; i < msg.arg2; i++) {
                            if (colors[msg.arg1 + i] != -1) {
                                Shop_adapter.Item item = (Shop_adapter.Item) rv.findViewHolderForAdapterPosition(msg.arg1 + i);
                                if (item != null)
                                    item.set_color(colors[msg.arg1 + i]);
                                else
                                    adapter.notifyItemChanged(msg.arg1 + i);
                            }
                        }
                        break;
                }
            }
        };
        adapter.set_handler(handler);
    }
}