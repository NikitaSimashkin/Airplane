package com.example.airplane;

import android.content.Context;

public class Samolet extends Sprite{

    private int up_or_down, speed = height/Params.samolet_speed, hp = Params.samolet_hp;
    private boolean updown;

    public Samolet(Context context) {
        super(ImageResource.SPACESHIP, height/32, 0, (int)(height/5), width/7, context);
    }
    public void update_koord(int a, int b){ //метод меняет координаты самолета
        if (!(up + a < height/32 || down + b > 31*height/32)) { //это условие проверяет выход за границы
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
        if (hp > Params.samolet_hp)
            hp = Params.samolet_hp;
    }
}
