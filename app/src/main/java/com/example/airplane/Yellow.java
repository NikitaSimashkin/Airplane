package com.example.airplane;

import android.content.Context;

public class Yellow extends Enemy{
    public Yellow(int up, int left, int down, int right, Context context) {
        super(ImageResource.YELLOW, up, left, down, right, context, Params.yellow_damage,
                Params.yellow_speed, Params.yellow_hp, 3);
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = ImageResource.YELLOW_DEATH.getBitmap(context);
    }
}