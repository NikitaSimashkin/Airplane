package com.example.airplane.Sprites.Bad;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.airplane.ImageResource;
import com.example.airplane.MusicResorces;
import com.example.airplane.Params;
import com.example.airplane.Sprites.Good.Bullet;

import java.util.List;

public class Sun extends Enemy {

    public Sun(double line_v, double line_h, Context context) {
        super(ImageResource.SUN_RED, line_v, width + line_h, 6*height/32 + line_v,
                width*12/11 + line_h, context, Params.sun_damage, Params.sun_speed, Params.sun_hp, 2);
        points = Params.p_sun;

        if (Params.change_color){
            color = (int)(Math.random()*4)+1;
            switch (color){
                case 1:
                    bitmap = ImageResource.SUN_GREEN.getBitmap(context);
                    break;
                case 2:
                    bitmap = ImageResource.SUN_RED.getBitmap(context);
                    break;
                case 3:
                    bitmap = ImageResource.SUN_YELLOW.getBitmap(context);
                    break;
                case 4:
                    bitmap = ImageResource.SUN_BLUE.getBitmap(context);
                    break;
            }
        }
    }

    public Sun(double line_v, double line_h, Context context, int color) {
        super(ImageResource.SUN_RED, line_v, width + line_h, 6*height/32 + line_v,
                width*12/11 + line_h, context, Params.sun_damage, Params.sun_speed, Params.sun_hp, color);
        points = Params.p_sun;

        if (Params.change_color){
            switch (color){
                case 1:
                    bitmap = ImageResource.SUN_GREEN.getBitmap(context);
                    break;
                case 2:
                    bitmap = ImageResource.SUN_RED.getBitmap(context);
                    break;
                case 3:
                    bitmap = ImageResource.SUN_YELLOW.getBitmap(context);
                    break;
                case 4:
                    bitmap = ImageResource.SUN_BLUE.getBitmap(context);
                    break;
            }
        }
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = ImageResource.SUN_DEATH.getBitmap(context);
    }

    @Override
    public void collision(@NonNull Bullet two, List<Enemy> enemy_list) {
        if (color == two.getColor() || two.getColor() == 5){
            change_hp(-2*two.get_damage());
            MusicResorces.play(MusicResorces.enemy_damage);
        }
        else{
            enemy_list.add(new Bullet_enemy(two, context));
        }
    }
}
