package com.example.airplane;

import android.content.Context;

public class Megasun extends Enemy{
    public Megasun(int line_v, int line_h, Context context) {
        super(ImageResource.MEGASUN, line_v, width + line_h,
                30*height/32 + line_v, width*11/10 + line_h, context,
                Params.megasun_damage, Params.megasun_speed, Params.megasun_hp, 2);
    }
}
