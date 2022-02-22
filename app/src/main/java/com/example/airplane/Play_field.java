package com.example.airplane;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

public class Play_field extends SurfaceView implements SurfaceHolder.Callback{


    public Play_field(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);

        ImageButton up_button = (ImageButton) findViewById(R.id.up_button);
        ImageButton down_button = (ImageButton) findViewById(R.id.down_button);

        DrawThread drawThread = new DrawThread(getHolder(), getContext(), up_button, down_button);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}