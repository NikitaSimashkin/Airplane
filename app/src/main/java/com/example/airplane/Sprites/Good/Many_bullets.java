package com.example.airplane.Sprites.Good;

import android.content.Context;

import com.example.airplane.DrawThread;

import java.util.List;

public class Many_bullets{
    private int color;
    private int size, n, counter;
    public  static boolean alive;
    private long last_bullet, time_bullet;
    private List<Bullet> bullet_list;
    private DrawThread drawThread;
    private Context context;

    public Many_bullets(int color, int size, DrawThread drawThread, List<Bullet> bullet_list, Context context){
        this.color = color;
        this.size = size;
        Many_bullets.alive = true;
        this.bullet_list = bullet_list;
        this.drawThread = drawThread;
        this.context = context;
        drawThread.change_bullet_time(1000);
        switch (size){
            case 0:
                n = 3;
                time_bullet = 1000;
                break;
            case 1:
                time_bullet = 500;
                n = 6;
                break;
            case 2:
                time_bullet = 300;
                n = 10;
                break;
        }
    }

    public void create_bullets(){
        if (System.currentTimeMillis() - last_bullet > time_bullet){
            bullet_list.add(new Bullet(drawThread.get_Samolet().get_koord(), context, color, size));
            counter++;
            last_bullet = System.currentTimeMillis();
            if (counter == n){
                drawThread.change_bullet_time(0.001);
                Many_bullets.alive = false;
            }
        }
    }
}
