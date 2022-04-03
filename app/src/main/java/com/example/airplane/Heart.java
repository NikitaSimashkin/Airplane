package com.example.airplane;

import android.content.Context;

import com.example.airplane.Enemy;
import com.example.airplane.ImageResource;
import com.example.airplane.Params;

public class Heart extends Enemy {
    public Heart(int up, int left, int down, int right, Context context) {
        super(ImageResource.HEART, up, left, down, right, context, Params.heart_damage, Params.heart_speed, Params.heart_hp, 5);
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = ImageResource.BREAKING_HEART.getBitmap(context);
    }
}