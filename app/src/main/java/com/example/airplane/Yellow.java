package com.example.airplane;

import android.content.Context;

public class Yellow extends Enemy{
    public Yellow(int line_v, int line_h, Context context) {
        super(ImageResource.YELLOW, line_v, width+line_h, line_v + 15*height/32,
                width*15/10 + line_h, context, Params.yellow_damage,
                Params.yellow_speed, Params.yellow_hp, 3);
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = ImageResource.YELLOW_DEATH.getBitmap(context);
    }
}