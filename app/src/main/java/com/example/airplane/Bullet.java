package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;

public class Bullet extends Sprite{

    private int width, height;
    private int damage, speed;

    public Bullet(int up, int left, int down, int right, Context context, int damage, int speed, int height, int width) {
        super(ImageResource.BULLET, up, left, down, right, context);
        System.out.println(5);
        this.damage = damage;
        this.speed = speed;
        this.height = height;
        this.width = width;
    }

    public void update_koord(){
        this.set_koord(up, down, left+width/speed, right+width/speed);
        left = left+width/speed;
        right = right+width/speed;
    }
}
