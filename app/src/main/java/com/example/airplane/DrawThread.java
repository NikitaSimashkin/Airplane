package com.example.airplane;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.widget.Button;
import android.widget.ImageButton;

public class DrawThread extends Thread{
    private SurfaceHolder surfaceHolder;
    private Bitmap spaceship_picture;
    private Context context;
    private ImageButton up_button;
    private ImageButton down_button;

    public DrawThread (SurfaceHolder surfaceHolder, Context context, ImageButton up, ImageButton down){
        super();
        this.surfaceHolder = surfaceHolder;
        this.context = context;

        up_button = up;
        down_button = down;
    }

    @Override
    public void run() {
        Canvas canvas = surfaceHolder.lockCanvas();  //рисуем картинку корабля
        Paint spaceship = new Paint();

        spaceship_picture = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceship);
        Rect test = new Rect(10, 10, 200, 200);
        canvas.drawBitmap(spaceship_picture, null, test, spaceship);
        surfaceHolder.unlockCanvasAndPost(canvas);


        }
}

