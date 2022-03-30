package com.example.airplane;

import android.content.Context;

public class Alien extends Enemy{
    public Alien(int up, int left, int down, int right, Context context, int start, int end) {
        super(ImageResource.ALIEN, up, left, down, right, context, 150, 800, 200, start, end);
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = ImageResource.ALIEN_DEATH.getBitmap(context);
    }
}
