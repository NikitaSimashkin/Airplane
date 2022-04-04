package com.example.airplane;

import android.content.Context;

import androidx.annotation.NonNull;

public class Megasun extends Enemy{
    public Megasun(int line_v, int line_h, Context context) {
        super(ImageResource.MEGASUN, line_v, width + line_h,
                30*height/32 + line_v, width*11/10 + line_h, context,
                Params.megasun_damage, Params.megasun_speed, Params.megasun_hp, 2);
    }

    @Override
    public void collision(@NonNull Bullet two, DrawThread drawThread) {
        super.collision(two, drawThread);
        if (!alive){
            drawThread.enemy_list.add(new Sun(height/32, -width+left, context));
            drawThread.enemy_list.add(new Sun(7*height/32, -width+left, context));
            drawThread.enemy_list.add(new Sun(13*height/32, -width+left, context));
            drawThread.enemy_list.add(new Sun(19*height/32, -width+left, context));
            drawThread.enemy_list.add(new Sun(25*height/32, -width+left, context));
            drawThread.enemy_list.remove(this);
        }
    }
}
