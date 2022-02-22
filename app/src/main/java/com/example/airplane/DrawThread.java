package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

public class DrawThread extends Thread{
    private SurfaceHolder surfaceHolder;
    private Bitmap spaceship_picture;
    private Context context;

    public DrawThread (SurfaceHolder surfaceHolder, Context context){
        super();
        this.surfaceHolder = surfaceHolder;
        this.context = context;
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
