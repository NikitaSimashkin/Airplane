package com.example.airplane;

import android.content.Context;

public class Bird extends Enemy{
    public Bird(int up, int left, int down, int right, Context context) {
        super(ImageResource.BIRD, up, left, down, right, context, Params.bird_damage, Params.bird_speed, Params.bird_hp, 4);
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = ImageResource.BIRD_DEATH.getBitmap(context);
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
