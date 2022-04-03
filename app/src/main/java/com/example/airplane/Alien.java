package com.example.airplane;

import android.content.Context;

public class Alien extends Enemy{
    public Alien(int up, int left, int down, int right, Context context) {
        super(ImageResource.ALIEN, up, left, down, right, context,
                Params.alien_damage, Params.alien_speed, Params.alien_hp, 1);
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = ImageResource.ALIEN_DEATH.getBitmap(context);
    }
}
