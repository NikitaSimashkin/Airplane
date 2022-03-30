package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;

public class Enemy extends Sprite{

    protected int damage, speed, hp;
    protected boolean alive = true;
    protected int start, end; // занятые линии; нужно, чтобы в некоторых ситуациях определить свободные линии
    protected long time_death = 0; // время смерти

    public Enemy(ImageResource imageResource, int up, int left, int down, int right, Context context, int damage, int speed, int hp, int start, int end) {
        super(imageResource, up, left, down, right, context);
        this.damage = damage;
        this.speed = speed;
        this.hp = hp;
        this.start = start;
        this.end = end;
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
    public int get_start_end(){return start*100+end;} // - колво сотен это начало, оставлееся это конец

    public void change_hp(int a){
        hp += a;
        if (hp <= 0)
            setDeath();
    }

    public void setDeath() { //ставим смерть
        alive = false;
        time_death = System.currentTimeMillis();
    }

}
