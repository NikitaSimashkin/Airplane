package com.example.airplane;

import android.content.Context;

import com.example.airplane.Enemy;
import com.example.airplane.ImageResource;
import com.example.airplane.Params;

public class Heart extends Enemy {
    public Heart(int line_v, int line_h, Context context) {
        super(ImageResource.HEART, line_v, width+line_h, line_v + 4*height/32,
                width*15/14 + line_h, context, Params.heart_damage,
                Params.heart_speed, Params.heart_hp, 5);
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = ImageResource.BREAKING_HEART.getBitmap(context);
    }
}