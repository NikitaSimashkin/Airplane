package com.example.airplane;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.SurfaceHolder;

public class DrawThread extends Thread{
    private SurfaceHolder surfaceHolder;
    private Context context;

    private Canvas canvas;

    private Rect rect;

    private int width, height, left, right, top, bottom;

    private int top_ship, bottom_ship;

    private boolean updown = false;
    private int up_or_down = 0;

    private Samolet samolet;

    public DrawThread (SurfaceHolder surfaceHolder, Context context, int width, int height){
        super();
        this.surfaceHolder = surfaceHolder;
        this.context = context;
        this.width = width;
        this.height = height;

        samolet = new Samolet(height/50, width/50, height/5, width/9, height, context);
    }

    public Samolet get_Samolet(){
        return samolet;
    }

    public void change_xy_samolet(){
        samolet.when_button_is_pressed();
    }

    public void draw_all(){

        canvas = surfaceHolder.lockCanvas();

        Paint clearPaint = new Paint(); //очистка холста
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawRect(0, 0, width, height, clearPaint);

        samolet.draw(canvas, null);

        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void run() {

        while (true){ //сначала он проводит все вычисления, а потом уже все рисует в одном методе
            change_xy_samolet();
            draw_all();
        }
    }
}

