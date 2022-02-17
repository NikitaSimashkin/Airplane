package com.example.airplane;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("Кирилл пашовкин не умеет программировать");
        Objects.requireNonNull(getSupportActionBar()).hide(); //убирает title_bar
        setContentView(R.layout.activity_main);
    }
}