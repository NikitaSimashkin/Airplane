package com.example.airplane;

import android.content.Context;

public class Cat extends Enemy{
    private int i = 0;
    public Cat(int line_v, int line_h, Context context) {
        super(Params.CAT_ONE, line_v, width + line_h, 10*height/32 + line_v,
                width*12/10 + line_h, context,
                Params.cat_damage, Params.cat_speed, Params.cat_hp, 4);
    }

    @Override
    public void setDeath() {
        if (i == 0){
            speed = Params.cat_speed_two;
            hp = Params.cat_hp_two;
            bitmap = Params.CAT_TWO;
            i++;
        }
        else{
            bitmap = Params.CAT_DEATH;
            super.setDeath();
        }
    }
}
