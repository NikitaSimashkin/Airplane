package com.example.airplane.Sprites.Bad;

import android.content.Context;

import com.example.airplane.ImageResource;
import com.example.airplane.Params;

public class Yellow extends Enemy {

    private int i = 0;

    public Yellow(double line_v, double line_h, Context context) {
        super(ImageResource.YELLOW_TWO, line_v, width+line_h, line_v + 15*height/32,
                width*12/10 + line_h, context, Params.yellow_damage,
                Params.yellow_speed, Params.yellow_hp, 3);
    }

    @Override
    public void setDeath() {
        if (i == 0) {
                left += (right - left)/2;
                double temp = up;
                up += (down - temp)/4;
                down -= (down - temp)/4;
                bitmap = ImageResource.YELLOW_TWO_TWO.getBitmap(context);
                hp = Params.yellow_hp_two;
                damage = Params.yellow_damage_two;
                i++;
        }
        else {
            super.setDeath();
            bitmap = ImageResource.ALIEN_DEATH.getBitmap(context);
        }
    }

}