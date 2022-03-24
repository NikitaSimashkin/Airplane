package com.example.airplane;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.Objects;

public class TestActivity extends AppCompatActivity {
    static int i = 0;

    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.test_activity);

        Button b = findViewById(R.id.button);
        ImageView image = findViewById(R.id.imageView);
        Bitmap hp = ImageResource.HP.getBitmap(this);
        ImageView image2 = findViewById(R.id.imageView2);

        image.setScaleType(ImageView.ScaleType.FIT_START);
        image.setImageBitmap(ImageResource.HP.getBitmap(this));
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setProgress(100);


        Bitmap hp1 = drawableToBitmap(image.getDrawable());
        image2.setImageBitmap(hp1);
        int k = image.getWidth();

        image.setMaxHeight(hp1.getHeight());
        image.setMaxWidth(hp1.getWidth());

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0){
                    image.setImageBitmap(Bitmap.createBitmap(hp1, 0, 0, hp1.getWidth(), hp1.getHeight()));
                    i++;
                }
                else {
                    i+=10;
                    progressBar.setProgress(i);
                }
            }
        });
    }
}