package com.example.airplane;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Samolet extends Sprite{
    private Bitmap spaceship_picture;
    private int up, left, down, right, height;

    private int up_or_down;
    private boolean updown;
    private Context context;

    public Samolet(int up, int left, int down, int right, int height, Context context) {
        super(BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceship), up, left, down, right);

        this.up = up;
        this.left = left;
        this.down = down;
        this.right = right;
        this.height = height;
        this.context = context;
    }


    public void change_xy_ship(int a, int b){ //метод меняет координаты самолета
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

    public void when_button_is_pressed(){ //умножаем на 1 если вниз, на -1 если вверх
        if (updown){
            change_xy_ship(height/80 * up_or_down, height/80 * up_or_down);
        }
    }
}
