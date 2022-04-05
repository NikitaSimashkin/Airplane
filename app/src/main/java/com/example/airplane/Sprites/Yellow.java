package com.example.airplane.Sprites;

import android.content.Context;
import android.graphics.Rect;

import com.example.airplane.ImageResource;
import com.example.airplane.Params;
import com.example.airplane.Sprites.Enemy;

public class Yellow extends Enemy {
    public Yellow(double line_v, double line_h, Context context) {
        super(ImageResource.YELLOW, line_v, width+line_h, line_v + 15*height/32,
                width*15/10 + line_h, context, Params.yellow_damage,
                Params.yellow_speed, Params.yellow_hp, 3);
    }

    @Override
    public Rect create_rect_minus(double a, double b, double c, double d) {
        return super.create_rect_minus((a + 3*(right-left)/5), b, c, d);
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = ImageResource.YELLOW_DEATH.getBitmap(context);
    }

}