package com.example.airplane;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide(); //убирает title_bar
        setContentView(R.layout.activity_main);

       // ImageView main_background = (ImageView) findViewById(R.id.imageView); //загружает гифку
      //  Glide.with(this).load(R.drawable.krasivo2).into(main_background);
        /*
        В папке drawable лежат гифки, можно потом если что поменять
         */


    }
}