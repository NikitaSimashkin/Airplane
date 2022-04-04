package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;

public class Bullet extends Sprite{

    private int damage = Params.bullet_damage, speed = Params.bullet_speed, color;

    public Bullet(int[] koord_samolet, Context context, int color) {
        super(ImageResource.BULLET, (koord_samolet[0] + koord_samolet[2]) / 2 - (koord_samolet[2] - koord_samolet[0]) / 4,
                2 * (koord_samolet[1] + koord_samolet[3]) / 3,
                (koord_samolet[0] + koord_samolet[2]) / 2 + (koord_samolet[2] - koord_samolet[0]) / 4,
                koord_samolet[3], context);
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void update_koord(){
            this.set_koord(up, down, left + width / speed, right + width / speed);
            left = left + width / speed;
            right = right + width / speed;
    }

    public int get_damage(){
        return damage;
    }
}
