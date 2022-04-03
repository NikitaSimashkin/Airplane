package com.example.airplane;

import android.content.Context;

public class Megasun extends Enemy{
    public Megasun(int up, int left, int down, int right, Context context) {
        super(ImageResource.MEGASUN, up, left, down, right, context,
                Params.megasun_damage, Params.megasun_speed, Params.megasun_hp, 2);
    }
}
