package com.example.airplane.Sprites.Bad;

import android.content.Context;

import com.example.airplane.ImageResource;
import com.example.airplane.Params;

import java.util.List;

public class Packman extends Enemy {
    private long  last_time;
    private double meteor_time = Params.meteor_time*Params.get_start_time();
    private List<Enemy> enemy_list;

    public Packman(double line_v, double line_h, Context context, List<Enemy> enemy_list) {
        super(ImageResource.PACKMAN_YELLOW, line_v, width + line_h, 5*height/32 + line_v,
                width*13/12 + line_h, context, Params.packman_damage, Params.packman_speed, Params.packman_hp, 3);
        last_time = System.currentTimeMillis();
        this.enemy_list = enemy_list;
        points = Params.p_packman;

        if (Params.change_color){
            color = (int)(Math.random()*4)+1;
            switch (color){
                case 1:
                    bitmap = ImageResource.PACKMAN_GREEN.getBitmap(context);
                    break;
                case 2:
                    bitmap = ImageResource.PACKMAN_RED.getBitmap(context);
                    break;
                case 3:
                    bitmap = ImageResource.PACKMAN_YELLOW.getBitmap(context);
                    break;
                case 4:
                    bitmap = ImageResource.PACKMAN_BLUE.getBitmap(context);
                    break;
            }
        }
    }

    public Packman(double line_v, double line_h, Context context, List<Enemy> enemy_list, int color) {
        super(ImageResource.PACKMAN_YELLOW, line_v, width + line_h, 5*height/32 + line_v,
                width*13/12 + line_h, context, Params.packman_damage, Params.packman_speed, Params.packman_hp, color);
        last_time = System.currentTimeMillis();
        this.enemy_list = enemy_list;
        points = Params.p_packman;

        if (Params.change_color){
            switch (color){
                case 1:
                    bitmap = ImageResource.PACKMAN_GREEN.getBitmap(context);
                    break;
                case 2:
                    bitmap = ImageResource.PACKMAN_RED.getBitmap(context);
                    break;
                case 3:
                    bitmap = ImageResource.PACKMAN_YELLOW.getBitmap(context);
                    break;
                case 4:
                    bitmap = ImageResource.PACKMAN_BLUE.getBitmap(context);
                    break;
            }
        }
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
            enemy_list.add(new Meteor((center-2 * height/32), -(width+width/12-center_2), context, color));
            last_time = System.currentTimeMillis();
        }
    }
}
