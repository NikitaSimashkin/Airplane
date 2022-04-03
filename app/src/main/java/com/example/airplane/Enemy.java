package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;

public class Enemy extends Sprite{

    protected int damage, speed, hp;
    protected boolean alive = true;
    protected long time_death = 0; // время смерти
    protected int color;

    public Enemy(ImageResource imageResource, int up, int left, int down, int right, Context context, int damage, int speed, int hp, int color) {
        super(imageResource, up, left, down, right, context);
        this.damage = damage;
        this.speed = speed;
        this.hp = hp;
        this.color = color;
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

    public void change_hp(int a){
        hp += a;
        if (hp <= 0)
            setDeath();
    }

    public void setDeath() { //ставим смерть
        alive = false;
        time_death = System.currentTimeMillis();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


    public void collision(Bullet two, DrawThread drawThread){ //возвращает тру сли пулю надо удалять после столкновения
        if (color == two.getColor()){
            change_hp(-2*two.get_damage());
        }
        else if (color + two.getColor() == 5){
            change_hp((int)(-0.5*two.get_damage()));
        }
        else {
            change_hp(-two.get_damage());
        }
    };
}
