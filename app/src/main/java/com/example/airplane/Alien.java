package com.example.airplane;

import android.content.Context;

public class Alien extends Enemy{
    public Alien(int line_v, int line_h, Context context) {
        super(Params.ALIEN, line_v, width + line_h,
                6*height/32 + line_v, width*14/13 + line_h, context,
                Params.alien_damage, Params.alien_speed, Params.alien_hp, 1);
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = Params.ALIEN_DEATH;
    }
}
