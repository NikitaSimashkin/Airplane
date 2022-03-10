package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;

public class Enemy extends Sprite{
    private int up, left, down, right, height, width;
    private Context context;

    public Enemy(Bitmap bitmap, int up, int left, int down, int right, int height, int width, Context context) {
        super(bitmap, up, left, down, right, context);

        this.up = up;
        this.left = left;
        this.down = down;
        this.right = right;
        this.height = height;
        this.width = width;
        this.context = context;
    }

    public void update_koord(int k){
        this.set_koord(up, down, left-width/k, right-width/k);
        left = left-width/k;
        right = right-width/k;
    }
}
