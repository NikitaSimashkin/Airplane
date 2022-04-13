package com.example.airplane.Sprites.Bad;

import android.content.Context;

import com.example.airplane.ImageResource;
import com.example.airplane.Params;
import com.example.airplane.Sprites.Sprite;

public class Cat extends Enemy{
    private int i = 0;
    public Cat(double line_v, double line_h, Context context) {
        super(ImageResource.CAT_ONE, line_v, Sprite.width + line_h, 10* Sprite.height/32 + line_v,
                Sprite.width*12/10 + line_h, context,
                Params.cat_damage, Params.cat_speed, Params.cat_hp, 4);
    }

    @Override
    public void setDeath() {
        if (i == 0){
            speed = (int)(Params.cat_speed_two * Params.start_speed_multiplier);
            hp = (int)(Params.cat_hp_two * Params.start_hp_multiplier);
            bitmap = ImageResource.CAT_TWO.getBitmap(context);
            i++;
        }
        else{
            bitmap = ImageResource.CAT_DEATH.getBitmap(context);
            super.setDeath();
        }
    }
}
