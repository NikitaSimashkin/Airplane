package com.example.airplane;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class DrawThread extends Thread{
    private SurfaceHolder surfaceHolder;
    private Context context;

    private Bitmap spaceship_picture;
    private Bitmap buffer;
    private Canvas canvas, buffer_canvas = null;

    private Rect rect;

    private int width, height, left, right, top, bottom;

    private int top_ship, bottom_ship;

    public DrawThread (SurfaceHolder surfaceHolder, Context context, int width, int height){
        super();
        this.surfaceHolder = surfaceHolder;
        this.context = context;
        spaceship_picture = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceship);
        this.width = width;
        this.height = height;

        top_ship = this.height/50;
        bottom_ship = this.width/9;
        left = this.height/50;

        buffer = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
        buffer_canvas = new Canvas();


    }

    public void change_xy_ship(int a, int b){
        top_ship += a;
        bottom_ship += b;
    }

    public void update(){

        canvas = surfaceHolder.lockCanvas();

        Paint clearPaint = new Paint(); //очистка холста
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        canvas.drawRect(0, 0, width, height, clearPaint);
        rect = new Rect (left, top_ship, height/4, bottom_ship);
        canvas.drawBitmap(spaceship_picture, null, rect, null);
        canvas.setBitmap(buffer);

        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void run() {

        while (true){
            update();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

