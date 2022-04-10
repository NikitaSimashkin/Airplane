package com.example.airplane.Sprites.Bad;

import android.content.Context;

import com.example.airplane.ImageResource;
import com.example.airplane.Params;

import java.util.List;

public class Packman extends Enemy {
    private long meteor_time = 5000, last_time;
    private List<Enemy> enemy_list;

    public Packman(double line_v, double line_h, Context context, List<Enemy> enemy_list) {
        super(ImageResource.PACKMAN, line_v, width + line_h, 5*height/32 + line_v,
                width*13/12 + line_h, context, Params.packman_damage, Params.packman_speed, Params.packman_hp, 3);
        last_time = System.currentTimeMillis();
        this.enemy_list = enemy_list;
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = ImageResource.PACKMAN_DEATH.getBitmap(context);
    }

    @Override
    public void update_koord() {
        if (left > width/2)
            super.update_koord();
        if (System.currentTimeMillis() - last_time > meteor_time && alive) {
            double center = ((up + down)/2);
            double center_2 = ((left + right)/2);
            enemy_list.add(new Meteor((center-2 * height/32), -(width+width/12-center_2), context, (int)(Math.random() * 4 + 1)));
            last_time = System.currentTimeMillis();
        }
    }
}
