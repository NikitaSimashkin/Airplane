package com.example.airplane;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
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
    private Canvas canvas;

    private Rect rect, last_rect;

    private int last_left, last_top, last_right, last_bottom, current_left, current_top, current_right, current_bottom;

    private int top_ship = 10, bottom_ship = 200;

    public DrawThread (SurfaceHolder surfaceHolder, Context context){
        super();
        this.surfaceHolder = surfaceHolder;
        this.context = context;
        spaceship_picture = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceship);
    }

    public void set_xy_ship(int a, int b){
        top_ship += a;
        bottom_ship += b;
    }

    public void update(){
        canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); //очистка холста

        //самолет
        rect = new Rect (10, top_ship, 200, bottom_ship);
        canvas.drawBitmap(spaceship_picture, null, rect, null);
        surfaceHolder.unlockCanvasAndPost(canvas);
        last_rect = rect;
    }

    @Override
    public void run() {

        while (true){
            update();
            try {
                Thread.sleep(34);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
