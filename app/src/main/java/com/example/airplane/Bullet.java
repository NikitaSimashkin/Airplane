package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;

public class Bullet extends Sprite{

    private int damage = Params.bullet_damage, speed = Params.bullet_speed, color;
    private ImageResource imageResource;

    public Bullet(int[] koord_samolet, Context context, int color) {
        super(Params.Bullets[0], (koord_samolet[0] + koord_samolet[2]) / 2 - (koord_samolet[2] - koord_samolet[0]) / 4,
                2 * (koord_samolet[1] + koord_samolet[3]) / 3,
                (koord_samolet[0] + koord_samolet[2]) / 2 + (koord_samolet[2] - koord_samolet[0]) / 4,
                koord_samolet[3], context);
        change_color(color);
    }

    public int getColor() {
        return color;
    }

    private void change_color(int color){
        this.color = color;
        bitmap = Params.Bullets[color - 1];

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
