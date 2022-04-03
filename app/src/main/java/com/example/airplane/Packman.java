package com.example.airplane;

import android.content.Context;

public class Packman extends Enemy{
    public Packman(int up, int left, int down, int right, Context context) {
        super(ImageResource.PACKMAN, up, left, down, right, context, Params.packman_damage, Params.packman_speed,
                Params.packman_hp, 3);
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = ImageResource.PACKMAN_DEATH.getBitmap(context);
    }
}
