package com.example.airplane.Sprites.Bad;

import android.content.Context;

import com.example.airplane.Params;

public class Meteor extends Enemy {
    public Meteor(double line_v, double line_h, Context context, int color) {
        super(Params.Meteors[color], line_v, width + line_h,
                line_v + 4*height/32, width*15/14 + line_h, context,
                Params.meteor_damage, Params.meteor_speed, Params.meteor_hp, color);
        change_bitmap(color);
        points = Params.p_meteor;
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
