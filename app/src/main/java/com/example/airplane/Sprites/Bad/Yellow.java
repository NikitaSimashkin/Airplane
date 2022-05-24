package com.example.airplane.Sprites.Bad;

import android.content.Context;

import com.example.airplane.ImageResource;
import com.example.airplane.Params;

public class Yellow extends Enemy {

    private int i = 0;

    public Yellow(double line_v, double line_h, Context context) {
        super(ImageResource.YELLOW_TWO_YELLOW, line_v, width+line_h, line_v + 15*height/32,
                width*12/10 + line_h, context, Params.yellow_damage,
                Params.yellow_speed, Params.yellow_hp, 3);
        points = Params.p_yellow;

        if (Params.change_color){
            color = (int)(Math.random()*4)+1;
            switch (color){
                case 1:
                    bitmap = ImageResource.YELLOW_TWO_GREEN.getBitmap(context);
                    break;
                case 2:
                    bitmap = ImageResource.YELLOW_TWO_RED.getBitmap(context);
                    break;
                case 3:
                    bitmap = ImageResource.YELLOW_TWO_YELLOW.getBitmap(context);
                    break;
                case 4:
                    bitmap = ImageResource.YELLOW_TWO_BLUE.getBitmap(context);
                    break;
            }
        }
    }

    @Override
    public void setDeath() {
        if (i == 0) {
                left += (right - left)/2;
                double temp = up;
                up += (down - temp)/4;
                down -= (down - temp)/4;
                if (Params.change_color){
                    switch (color){
                        case 1:
                            bitmap = ImageResource.YELLOW_TWO_TWO_GREEN.getBitmap(context);
                            break;
                        case 2:
                            bitmap = ImageResource.YELLOW_TWO_TWO_RED.getBitmap(context);
                            break;
                        case 3:
                            bitmap = ImageResource.YELLOW_TWO_TWO_YELLOW.getBitmap(context);
                            break;
                        case 4:
                            bitmap = ImageResource.YELLOW_TWO_TWO_BLUE.getBitmap(context);
                            break;
                    }
                } else{
                    bitmap = ImageResource.YELLOW_TWO_TWO_YELLOW.getBitmap(context);
                }
                damage = (int)(Params.get_start_damage()*Params.yellow_damage2);
                speed = (int)(Params.get_start_speed()*Params.yellow_speed2) ;
                hp = (int)(Params.get_start_hp()*Params.yellow_hp2);
                i++;
        }
        else {
            super.setDeath();
            bitmap = ImageResource.ALIEN_DEATH.getBitmap(context);
        }
    }

}