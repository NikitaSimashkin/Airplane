package com.example.airplane.Sprites;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.airplane.DrawThread;
import com.example.airplane.Params;

import java.util.List;

public class Bullet_enemy extends Enemy{
    public Bullet_enemy(Bullet bullet, Context context) {
        super(Params.Bullets[bullet.getColor()-1], bullet.up, bullet.left, bullet.down, bullet.right, context,
                Params.bullet_stats[bullet.get_size()*3],
                Params.bullet_stats[bullet.get_size()*3 + 1], Params.bullet_stats[bullet.get_size()*3 + 2], bullet.getColor());
    }

    @Override
    public void collision(@NonNull Bullet two, List<Enemy> enemy_list) {
        super.collision(two, enemy_list);
        if (!alive)
            enemy_list.remove(this);
    }
}
