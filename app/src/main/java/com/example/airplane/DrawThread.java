package com.example.airplane;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import java.util.ArrayList;

public class DrawThread extends Thread{
    private SurfaceHolder surfaceHolder;
    private Context context;

    private Canvas canvas;

    private int width, height;

    private Samolet samolet;

    private long time = System.currentTimeMillis()/1000000000;

    private ArrayList<Enemy> enemy_list = new ArrayList<>();

    private int enemys;

    public DrawThread (SurfaceHolder surfaceHolder, Context context, int width, int height){
        super();
        this.surfaceHolder = surfaceHolder;
        this.context = context;
        this.width = width;
        this.height = height;

        samolet = new Samolet(height/50, width/50, (int)(height/5.5), width/7, height, context);
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

        samolet.draw(canvas, null); //рисуем самолет
        for (int i = 0; i < enemy_list.size(); i++){ //отрисовываем врагов
            enemy_list.get(i).draw(canvas, null);
        }

        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void run() {

        while (true){ //сначала он проводит все вычисления, а потом уже все рисует в одном методе
            change_xy_samolet(); //обновляет координаты самолета
            update_enemy(); //отрисовывает всех врагов
            draw_all(); //рисует все
        }
    }

    private void update_enemy() {
        enemys = (int)(Math.random()*7);
        if (System.nanoTime()/1000000000 - time > 1){ //каждые 5 секунд спавним врага
            enemy_list.add(new Meteor(height/30 + enemys*(4 * height/30), width, 5*height/30 + enemys*(4 * height/30) , width*15/14, height, width, context));
            time = System.nanoTime()/1000000000;
        }
        for (int i = 0; i < enemy_list.size(); i++){
            enemy_list.get(i).update_koord(120); //обновляет координаты
         //   enemy_list.get(i).check(); //проверяет столкновение с самолетом или стеной
        }
    }
}

