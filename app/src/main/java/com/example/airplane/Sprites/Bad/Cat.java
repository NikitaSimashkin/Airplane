package com.example.airplane.Sprites.Bad;

import android.content.Context;

import com.example.airplane.ImageResource;
import com.example.airplane.Params;
import com.example.airplane.Sprites.Sprite;

public class Cat extends Enemy{
    private int i = 0;
    public Cat(double line_v, double line_h, Context context) {
        super(ImageResource.CAT_ONE_BLUE, line_v, Sprite.width + line_h, 10* Sprite.height/32 + line_v,
                Sprite.width*12/10 + line_h, context,
                Params.cat_damage, Params.cat_speed, Params.cat_hp, 4);
        points = Params.p_cat;

        if (Params.change_color){
            color = (int)(Math.random()*4)+1;
            switch (color){
                case 1:
                    bitmap = ImageResource.CAT_ONE_GREEN.getBitmap(context);
                    break;
                case 2:
                    bitmap = ImageResource.CAT_ONE_RED.getBitmap(context);
                    break;
                case 3:
                    bitmap = ImageResource.CAT_ONE_YELLOW.getBitmap(context);
                    break;
                case 4:
                    bitmap = ImageResource.CAT_ONE_BLUE.getBitmap(context);
                    break;
            }
        }
    }

    @Override
    public void setDeath() {
        if (i == 0){
            speed = (int)(Params.cat_speed_two * Params.get_start_speed());
            hp = (int)(Params.cat_hp_two * Params.get_start_hp());
            if (Params.change_color){
                switch (color){
                    case 1:
                        bitmap = ImageResource.CAT_TWO_GREEN.getBitmap(context);
                        break;
                    case 2:
                        bitmap = ImageResource.CAT_TWO_RED.getBitmap(context);
                        break;
                    case 3:
                        bitmap = ImageResource.CAT_TWO_YELLOW.getBitmap(context);
                        break;
                    case 4:
                        bitmap = ImageResource.CAT_TWO_BLUE.getBitmap(context);
                        break;
                }
            } else {
                bitmap = ImageResource.CAT_TWO_BLUE.getBitmap(context);
            }
            i++;
        }
        else{
            bitmap = ImageResource.CAT_DEATH.getBitmap(context);
            super.setDeath();
        }
    }
}
