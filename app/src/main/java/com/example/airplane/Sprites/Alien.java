package com.example.airplane.Sprites;

import android.content.Context;

import com.example.airplane.ImageResource;
import com.example.airplane.Params;

public class Alien extends Enemy {
    public Alien(double line_v, double line_h, Context context) {
        super(ImageResource.ALIEN, line_v, width + line_h,
                6*height/32 + line_v, width*14/13 + line_h, context,
                Params.alien_damage, Params.alien_speed, Params.alien_hp, 1);
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = ImageResource.ALIEN_DEATH.getBitmap(context);
    }
}
