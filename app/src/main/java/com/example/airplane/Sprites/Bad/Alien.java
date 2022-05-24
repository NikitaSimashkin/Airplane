package com.example.airplane.Sprites.Bad;

import android.content.Context;
import android.provider.ContactsContract;

import com.example.airplane.ImageResource;
import com.example.airplane.Params;
import com.example.airplane.Sprites.Sprite;

public class Alien extends Enemy {

    public Alien(double line_v, double line_h, Context context) {
        super(ImageResource.ALIEN_GREEN, line_v, width + line_h,
                6* height/32 + line_v, width*14/13 + line_h, context,
                Params.alien_damage, Params.alien_speed, Params.alien_hp, 1);
        points = Params.p_alien;

        if (Params.change_color){
            color = (int)(Math.random()*4)+1;
            switch (color){
                case 1:
                    bitmap = ImageResource.ALIEN_GREEN.getBitmap(context);
                    break;
                case 2:
                    bitmap = ImageResource.ALIEN_RED.getBitmap(context);
                    break;
                case 3:
                    bitmap = ImageResource.ALIEN_YELLOW.getBitmap(context);
                    break;
                case 4:
                    bitmap = ImageResource.ALIEN_BLUE.getBitmap(context);
                    break;
            }
        }
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = ImageResource.ALIEN_DEATH.getBitmap(context);
    }
}
