package com.example.airplane.Sprites;

import android.content.Context;

import com.example.airplane.ImageResource;
import com.example.airplane.Params;

import java.util.List;

public class Bullet extends Sprite {

    private int damage = Params.bullet_damage, speed = Params.bullet_speed;

    protected int color;
    private ImageResource imageResource;

    public Bullet(double[] koord_samolet, Context context, int color) {
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
            left =left + width / speed;
            right = right + width / speed;
    }

    public int get_damage(){
        return damage;
    }

    public void collision(List<Bullet> bullet_list){ // при столкновени по умолчанию удаляем
        bullet_list.remove(this);
    }
}
