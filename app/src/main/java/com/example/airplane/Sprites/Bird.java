package com.example.airplane.Sprites;

import android.content.Context;

import com.example.airplane.DrawThread;
import com.example.airplane.ImageResource;
import com.example.airplane.Params;

import java.util.List;

public class Bird extends Enemy {
    public Bird(double line_v, double line_h, Context context) {
        super(ImageResource.BIRD, line_v, width + line_h, 2*height/32 + line_v,
                width*14/13 + line_h, context, Params.bird_damage, Params.bird_speed, Params.bird_hp, 4);
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = ImageResource.BIRD_DEATH.getBitmap(context);
    }

    @Override
    public void collision(Bullet two, List<Enemy> enemy_list) {
        if (color != two.getColor()){
            if (System.currentTimeMillis() % 2 == 0){
                if (up - 4 * height/32 > 0){
                    up -= 4 * height/32;
                    down -= 4 * height/32;
                }
                else {
                    up += 4 * height/32;
                    down += 4 * height/32;
                }
            } else {
                if (down + 4 * height/32 < height){
                    up += 4 * height/32;
                    down += 4 * height/32;
                }
                else {
                    up -= 4 * height/32;
                    down -= 4 * height/32;
                }
            }
        } else{
            change_hp(-2 * two.get_damage());
        }
    }
}
