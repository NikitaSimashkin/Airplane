package com.example.airplane;

import android.content.Context;

public class Bird extends Enemy{
    public Bird(int line_v, int line_h, Context context) {
        super(Params.BIRD, line_v, width + line_h, 2*height/32 + line_v,
                width*14/13 + line_h, context, Params.bird_damage, Params.bird_speed, Params.bird_hp, 4);
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = Params.BIRD_DEATH;
    }

    @Override
    public void collision(Bullet two, DrawThread drawThread) {
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

    public void change_line(){

    }
}
