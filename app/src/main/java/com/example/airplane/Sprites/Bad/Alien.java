package com.example.airplane.Sprites.Bad;

import android.content.Context;

import com.example.airplane.ImageResource;
import com.example.airplane.Params;
import com.example.airplane.Sprites.Sprite;

public class Alien extends Enemy {
    public Alien(double line_v, double line_h, Context context) {
        super(ImageResource.ALIEN, line_v, Sprite.width + line_h,
                6* Sprite.height/32 + line_v, Sprite.width*14/13 + line_h, context,
                Params.alien_damage, Params.alien_speed, Params.alien_hp, 1);
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = ImageResource.ALIEN_DEATH.getBitmap(context);
    }
}