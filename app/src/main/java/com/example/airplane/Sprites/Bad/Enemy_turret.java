package com.example.airplane.Sprites.Bad;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.airplane.DrawThread;
import com.example.airplane.ImageResource;
import com.example.airplane.Params;
import com.example.airplane.Sprites.Good.Bullet;
import com.example.airplane.Sprites.Good.MegaBullet;

import java.util.List;

public class Enemy_turret extends Enemy{

    private final List<Enemy> enemyList;
    private long last;
    private double time_bullet = Params.time_bullet_small*Params.turret_multiplier_bullet_enemy;
    private int number = 0, max_number = Params.number_bullet_enemy_turret;

    public Enemy_turret(Context context, double h, List<Enemy> enemyList) {
        super(ImageResource.ENEMY_TURRET, h, width-9*height/32, h+8*height/32, width-1*height/32, context,
                0, 0, 100000000, 5);
        this.enemyList = enemyList;
        last = System.currentTimeMillis();
    }

    @Override
    public void update_koord() {
        if (System.currentTimeMillis() - last > time_bullet){
            enemyList.add(new Bullet_enemy(new double[]{up,left,down,right}, context, 2, (int)((Math.random()*4)+1)));
            number++;
            if (number == max_number){
                enemyList.remove(this);
                Boss.set_turret_flag(false);
                DrawThread.add_points(points);
                DrawThread.points_handler();
            }
            last = System.currentTimeMillis();
        }
    }

    @Override
    public void collision(@NonNull Bullet two, List<Enemy> enemy_list) {}
}
