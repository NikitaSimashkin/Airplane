package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Meteor extends Enemy {
    public Meteor(int line_v, int line_h, Context context, int color) {
        super(Params.Meteors[color], line_v, width + line_h,
                line_v + 4*height/32, width*15/14 + line_h, context,
                Params.meteor_damage, Params.meteor_speed, Params.meteor_hp, color);
        change_bitmap(color);
        }


    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = Params.Meteors[4];
    }

    public void change_bitmap(int color){
        bitmap = Params.Meteors[color-1];
    }

}
