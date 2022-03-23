package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;

public class Enemy extends Sprite{

    private int damage, speed;
    private boolean alive = true;
    private long time_death = 0; //время смерти

    public Enemy(ImageResource imageResource, int up, int left, int down, int right, Context context, int damage, int speed) {
        super(imageResource, up, left, down, right, context);
        this.damage = damage;
        this.speed = speed;
    }

    public void update_koord(){
        if (alive) {
            this.set_koord(up, down, left - width / speed, right - width / speed);
            left = left - width / speed;
            right = right - width / speed;
        }
    }

    public long getTime_death() {
        return time_death;
    }
    public boolean get_alive() {
        return alive;
    }
    public int get_damage(){
        return damage;
    }

    public void setDeath() { //ставим смерть
        alive = false;
        bitmap = ImageResource.METEOR_DIED.getBitmap(context);
        time_death = System.currentTimeMillis();
    }

}
