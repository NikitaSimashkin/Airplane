package com.example.airplane.Sprites.Bad;

import android.content.Context;

import com.example.airplane.ImageResource;
import com.example.airplane.MusicResorces;
import com.example.airplane.Params;
import com.example.airplane.Sprites.Good.Bullet;
import com.example.airplane.Sprites.Sprite;

import java.util.List;

public class Bird extends Enemy {
    public Bird(double line_v, double line_h, Context context) {
        super(ImageResource.BIRD_BLUE, line_v, Sprite.width + line_h, 2* Sprite.height/32 + line_v,
                Sprite.width*14/13 + line_h, context, Params.bird_damage, Params.bird_speed, Params.bird_hp, 4);
        points = Params.p_bird;

        if (Params.change_color){
            color = (int)(Math.random()*4)+1;
            switch (color){
                case 1:
                    bitmap = ImageResource.BIRD_GREEN.getBitmap(context);
                    break;
                case 2:
                    bitmap = ImageResource.BIRD_RED.getBitmap(context);
                    break;
                case 3:
                    bitmap = ImageResource.BIRD_YELLOW.getBitmap(context);
                    break;
                case 4:
                    bitmap = ImageResource.BIRD_BLUE.getBitmap(context);
                    break;
            }
        }
    }

    public Bird(double line_v, double line_h, Context context, int color) {
        super(ImageResource.BIRD_BLUE, line_v, Sprite.width + line_h, 2* Sprite.height/32 + line_v,
                Sprite.width*14/13 + line_h, context, Params.bird_damage, Params.bird_speed, Params.bird_hp, color);
        points = Params.p_bird;

        if (Params.change_color){
            switch (color){
                case 1:
                    bitmap = ImageResource.BIRD_GREEN.getBitmap(context);
                    break;
                case 2:
                    bitmap = ImageResource.BIRD_RED.getBitmap(context);
                    break;
                case 3:
                    bitmap = ImageResource.BIRD_YELLOW.getBitmap(context);
                    break;
                case 4:
                    bitmap = ImageResource.BIRD_BLUE.getBitmap(context);
                    break;
            }
        }
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = ImageResource.BIRD_DEATH.getBitmap(context);
    }

    @Override
    public void collision(Bullet two, List<Enemy> enemy_list) {
        if (color != two.getColor() && two.getColor() != 5){
            if (System.currentTimeMillis() % 2 == 0){
                if (up - 4 * Sprite.height/32 > 0){
                    up -= 4 * Sprite.height/32;
                    down -= 4 * Sprite.height/32;
                }
                else {
                    up += 4 * Sprite.height/32;
                    down += 4 * Sprite.height/32;
                }
            } else {
                if (down + 4 * Sprite.height/32 < Sprite.height){
                    up += 4 * Sprite.height/32;
                    down += 4 * Sprite.height/32;
                }
                else {
                    up -= 4 * Sprite.height/32;
                    down -= 4 * Sprite.height/32;
                }
            }
        } else{
            MusicResorces.play(MusicResorces.enemy_damage);
            change_hp(-2 * two.get_damage());
        }
    }
}
