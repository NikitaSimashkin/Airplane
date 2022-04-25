package com.example.airplane.Sprites.Bad;

import android.content.Context;

import com.example.airplane.ImageResource;
import com.example.airplane.Params;

public class Heart extends Enemy {
    public Heart(double line_v, double line_h, Context context) {
        super(ImageResource.HEART, line_v, width+line_h, line_v + 4*height/32,
                width*15/14 + line_h, context, Params.heart_damage,
                Params.heart_speed, Params.heart_hp, 5);
        hp = Params.heart_hp;
        speed = Params.heart_speed;
        damage = Params.heart_damage;
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = ImageResource.BREAKING_HEART.getBitmap(context);
    }
}