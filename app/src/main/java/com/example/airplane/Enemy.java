package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;

public class Enemy extends Sprite{
    protected int up, left, down, right, height, width, damage;
    private Context context;

    /**
     * проверка жив или нет, 1 - yes, 1000000 - no
     */
    private int alive = 1;
    private long time_death = 0; //время смерти

    public Enemy(ImageResource imageResource, int up, int left, int down, int right, int height, int width, Context context, int damage) {
        super(imageResource, up, left, down, right, context);

        this.up = up;
        this.left = left;
        this.down = down;
        this.right = right;
        this.height = height;
        this.width = width;
        this.damage = damage;
        this.context = context;
    }

    public void update_koord(int k){
        this.set_koord(up, down, left-width/k, right-width/k);
        left = left-width/k;
        right = right-width/k;
    }

    public int getAlive() {
        return alive;
    }
    public long getTime_death() {
        return time_death;
    }

    public void setDeath(Bitmap bitmap) { //ставим смерть
        alive = 100000000;
        this.bitmap = bitmap;
        time_death = System.nanoTime()/1000000000;
    }

}
