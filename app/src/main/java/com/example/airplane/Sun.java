package com.example.airplane;

import android.content.Context;

public class Sun extends Enemy{

    public Sun(int line_v, int line_h, Context context) {
        super(ImageResource.SUN, line_v, width + line_h, 6*height/32 + line_v,
                width*12/11 + line_h, context, Params.sun_damage, Params.sun_speed, Params.sun_hp, 2);
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = ImageResource.SUN_DEATH.getBitmap(context);
    }
}
