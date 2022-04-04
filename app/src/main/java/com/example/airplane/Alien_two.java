package com.example.airplane;

import android.content.Context;
import android.media.Image;

public class Alien_two extends Enemy{
    int i = 0; // он один раз "умирает", меняя картинку, а после второго раза по-настоящему умирает
    // изначально создается с 150 хп, потом перерождается уже с картинкой без каски и 200 хп

    public Alien_two(int line_v, int line_h, Context context) {
        super(Params.ALIEN_TWO, line_v, width + line_h,
                6*height/32 + line_v, width*14/13 + line_h, context,
                Params.alien_two_damage, Params.alien_two_speed, Params.alien_two_hp, 1);
    }

    @Override
    public void setDeath() {
        if (i == 0) {
            change_bitmap();
            hp = Params.alien_two_hp_two;
            i++;
        }
        else{
            super.setDeath();
            bitmap = Params.ALIEN_DEATH;
        }
    }

    public void change_bitmap(){
        bitmap = Params.ALIEN_TWO_WITHOUT_CAP;
    }
}
