package com.example.airplane;

import android.content.Context;

public class Samolet extends Sprite{

    private int up_or_down, speed = height/120, hp = 1000;
    private boolean updown;

    public Samolet(int up, int left, int down, int right, Context context) {
        super(ImageResource.SPACESHIP, up, left, down, right, context);
    }
    public void update_koord(int a, int b){ //метод меняет координаты самолета
        if (!(up + a < height/50 || down + b > height)) { //это условие проверяет выход за границы
            this.set_koord(up + a, down + b, left, right);
            up += a;
            down += b;
        }
    }

    public void set_updown (boolean updown, int up_or_down){ //этот метод и метод ниже меняют координаты самолета в соответствии с кнопками
        this.updown = updown;
        this.up_or_down = up_or_down;
    }

    public void buttons(){ //умножаем на 1 если вниз, на -1 если вверх
        if (updown){
            update_koord(speed * up_or_down, speed * up_or_down);
        }
    }

    public int get_hp(){
        return hp;
    }

    public void change_hp(int a){
        hp += a;
    }
}
