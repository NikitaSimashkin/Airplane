package com.example.airplane.Sprites.Bad;

import android.content.Context;

import com.example.airplane.ImageResource;
import com.example.airplane.Params;
import com.example.airplane.Sprites.Sprite;

public class Alien_two extends Enemy {
    int i = 0; // он один раз "умирает", меняя картинку, а после второго раза по-настоящему умирает
    // изначально создается с 150 хп, потом перерождается уже с картинкой без каски и 200 хп

    public Alien_two(double line_v, double line_h, Context context) {
        super(ImageResource.ALIEN_TWO_GREEN, line_v, Sprite.width + line_h,
                6* Sprite.height/32 + line_v, Sprite.width*14/13 + line_h, context,
                Params.alien_two_damage, Params.alien_two_speed, Params.alien_two_hp, 1);
        points = Params.p_alien_two;

        if (Params.change_color){
            color = (int)(Math.random()*4)+1;
            switch (color){
                case 1:
                    bitmap = ImageResource.ALIEN_TWO_GREEN.getBitmap(context);
                    break;
                case 2:
                    bitmap = ImageResource.ALIEN_TWO_RED.getBitmap(context);
                    break;
                case 3:
                    bitmap = ImageResource.ALIEN_TWO_YELLOW.getBitmap(context);
                    break;
                case 4:
                    bitmap = ImageResource.ALIEN_TWO_BLUE.getBitmap(context);
                    break;
            }
        }
    }

    @Override
    public void setDeath() {
        if (i == 0) {
            change_bitmap();
            hp = (int)(Params.alien_two_hp_two * Params.get_start_hp());
            i++;
        }
        else{
            super.setDeath();
            bitmap = ImageResource.ALIEN_DEATH.getBitmap(context);
        }
    }

    public void change_bitmap(){
        if (Params.change_color){
            switch (color){
                case 1:
                    bitmap = ImageResource.ALIEN_TWO_WITHOUT_CAP_GREEN.getBitmap(context);
                    break;
                case 2:
                    bitmap = ImageResource.ALIEN_TWO_WITHOUT_CAP_RED.getBitmap(context);
                    break;
                case 3:
                    bitmap = ImageResource.ALIEN_TWO_WITHOUT_CAP_YELLOW.getBitmap(context);
                    break;
                case 4:
                    bitmap = ImageResource.ALIEN_TWO_WITHOUT_CAP_BLUE.getBitmap(context);
                    break;
            }
        } else {
            bitmap = ImageResource.ALIEN_TWO_WITHOUT_CAP_GREEN.getBitmap(context);
        }
    }
}
