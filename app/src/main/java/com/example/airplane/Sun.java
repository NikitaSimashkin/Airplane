package com.example.airplane;

import android.content.Context;

public class Sun extends Enemy{

    public Sun(int up, int left, int down, int right, Context context) {
        super(ImageResource.SUN, up, left, down, right, context, Params.sun_damage, Params.sun_speed, Params.sun_hp, 2);
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = ImageResource.SUN_DEATH.getBitmap(context);
    }
}
