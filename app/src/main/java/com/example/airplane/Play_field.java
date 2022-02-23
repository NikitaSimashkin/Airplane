package com.example.airplane;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

public class Play_field extends SurfaceView implements SurfaceHolder.Callback{
    private DrawThread drawThread;


    public Play_field(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);

        drawThread = new DrawThread(getHolder(), getContext(), getWidth(), getHeight());
        drawThread.start();
    }

    public void change_xy_ship(int top, int bottom){
        drawThread.set_xy_ship(top, bottom);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}