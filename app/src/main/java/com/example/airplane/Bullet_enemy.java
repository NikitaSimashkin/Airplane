package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class Bullet_enemy extends Enemy{
    public Bullet_enemy(Bullet bullet, Context context) {
        super(Params.Bullets[bullet.getColor()-1], bullet.up, bullet.left, bullet.down, bullet.right, context,
                Params.bullet_damage, Params.bullet_speed, Params.bullet_hp, bullet.getColor());
    }

    @Override
    public void collision(@NonNull Bullet two, DrawThread drawThread) {
        super.collision(two, drawThread);
        if (!alive)
            drawThread.enemy_list.remove(this);
    }
}
