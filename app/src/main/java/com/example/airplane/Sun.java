package com.example.airplane;

import android.content.Context;

import androidx.annotation.NonNull;

public class Sun extends Enemy{

    public Sun(int line_v, int line_h, Context context) {
        super(Params.SUN, line_v, width + line_h, 6*height/32 + line_v,
                width*12/11 + line_h, context, Params.sun_damage, Params.sun_speed, Params.sun_hp, 2);
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = Params.SUN_DEATH;
    }

    @Override
    public void collision(@NonNull Bullet two, DrawThread drawThread) {
        if (color == two.getColor()){
            change_hp(-2*two.get_damage());
        }
        else{
            drawThread.enemy_list.add(new Bullet_enemy(two, context));
        }
    }
}
