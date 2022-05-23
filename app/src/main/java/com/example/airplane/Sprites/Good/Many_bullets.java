package com.example.airplane.Sprites.Good;

import android.content.Context;

import com.example.airplane.DrawThread;
import com.example.airplane.MusicResorces;
import com.example.airplane.Params;

import java.util.List;

public class Many_bullets{
    private int color;
    private int size, n, counter;
    public  static boolean alive;
    private long last_bullet;
    private double time_bullet;
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
                time_bullet = Params.time_bullet_big;
                n = 3;
                break;
            case 1:
                time_bullet = Params.time_bullet_normal;
                n = 7;
                break;
            case 2:
                time_bullet = Params.time_bullet_small;
                n = 19;
                break;
        }
        time_bullet *= (Params.many_bullets_multiplier*Params.start_timebullet_m);
    }

    public void create_bullets(){
        if (System.currentTimeMillis() - last_bullet > time_bullet){
            MusicResorces.play(MusicResorces.bullet_s[size]);
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
