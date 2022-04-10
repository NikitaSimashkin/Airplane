package com.example.airplane.Sprites.Bad;

import android.content.Context;

import com.example.airplane.ImageResource;

import java.util.List;

public class Enemy_turret extends Enemy{

    private final List<Enemy> enemyList;
    private long last;
    private int time_bullet = 1000, bullet_size = 2, number = 0, max_number = 1;

    public Enemy_turret(Context context, double h, List<Enemy> enemyList) {
        super(ImageResource.ENEMY_TURRET, h, width-9*height/32, h+8*height/32, width-1*height/32, context,
                0, 0, 100000000, 5);
        this.enemyList = enemyList;
        last = System.currentTimeMillis();
    }

    @Override
    public void update_koord() {
        if (System.currentTimeMillis() - last > time_bullet){
            enemyList.add(new Bullet_enemy(new double[]{up,left,down,right}, context, bullet_size, (int)((Math.random()*4)+1)));
            number++;
            if (number == max_number){
                enemyList.remove(this);
                Boss.set_turret_flag(false);
            }
            last = System.currentTimeMillis();
        }
    }
}
