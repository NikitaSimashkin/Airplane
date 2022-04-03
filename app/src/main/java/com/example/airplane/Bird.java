package com.example.airplane;

import android.content.Context;

public class Bird extends Enemy{
    public Bird(int up, int left, int down, int right, Context context) {
        super(ImageResource.BIRD, up, left, down, right, context, Params.bird_damage, Params.bird_speed, Params.bird_hp, 4);
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = ImageResource.BIRD_DEATH.getBitmap(context);
    }
}
