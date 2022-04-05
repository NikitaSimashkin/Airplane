package com.example.airplane.Sprites;

import android.content.Context;

import com.example.airplane.ImageResource;
import com.example.airplane.Params;

public class Cat extends Enemy{
    private int i = 0;
    public Cat(double line_v, double line_h, Context context) {
        super(ImageResource.CAT_ONE, line_v, width + line_h, height/32 + line_v,
                width*12/10 + line_h, context,
                Params.cat_damage, Params.cat_speed, Params.cat_hp, 4);
    }

    @Override
    public void setDeath() {
        if (i == 0){
            speed = Params.cat_speed_two;
            hp = Params.cat_hp_two;
            bitmap = ImageResource.CAT_TWO.getBitmap(context);
            i++;
        }
        else{
            bitmap = ImageResource.CAT_DEATH.getBitmap(context);
            super.setDeath();
        }
    }
}
