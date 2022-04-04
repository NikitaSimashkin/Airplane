package com.example.airplane;

import android.content.Context;
import android.graphics.Rect;

public class Yellow extends Enemy{
    public Yellow(int line_v, int line_h, Context context) {
        super(ImageResource.YELLOW, line_v, width+line_h, line_v + 15*height/32,
                width*15/10 + line_h, context, Params.yellow_damage,
                Params.yellow_speed, Params.yellow_hp, 3);
    }

    @Override
    public Rect create_rect_minus(int a, int b, int c, int d) {
        return super.create_rect_minus(a + 3*(right-left)/5, b, c, d);
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = ImageResource.YELLOW_DEATH.getBitmap(context);
    }

}