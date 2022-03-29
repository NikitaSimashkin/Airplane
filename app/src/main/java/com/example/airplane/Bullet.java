package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;

public class Bullet extends Sprite{

    private int damage = 100, speed = 200;

    public Bullet(int up, int left, int down, int right, Context context) {
        super(ImageResource.BULLET, up, left, down, right, context);
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
