package com.example.airplane.Sprites.Good;

import android.content.Context;
import android.graphics.Rect;

import com.example.airplane.ImageResource;
import com.example.airplane.Params;
import com.example.airplane.Sprites.Sprite;

import java.util.List;

public class Bullet extends Sprite {

    protected int damage, speed;

    protected int color, size;

    public Bullet(double[] koord_samolet, Context context, int color, int size) {
        super(Params.Bullets[0], (koord_samolet[0] + koord_samolet[2]) / 2 - (koord_samolet[2] - koord_samolet[0]) / Math.pow(2, size+1),
                koord_samolet[3] - 2*(koord_samolet[2] - koord_samolet[0]) / Math.pow(2, size+1) + (koord_samolet[3]-koord_samolet[1])/5,
                (koord_samolet[0] + koord_samolet[2]) / 2 + (koord_samolet[2] - koord_samolet[0]) / Math.pow(2, size+1),
                koord_samolet[3]+(koord_samolet[3]-koord_samolet[1])/5, context);
        if (color != 5)
            change(color, size);
    }

    public int getColor() {
        return color;
    }

    private void change(int color, int size){
        this.color = color;
        this.size = size;
        damage = (int)(Params.bullet_stats[size*3] * Params.start_damagebullet_m);
        speed = (int)(Params.bullet_stats[size*3+1] * Params.start_speedbullet_m);

        bitmap = Params.Bullets[color - 1];
    }

    @Override
    public Rect create_rect_minus(double a, double b, double c, double d) {
        if (size != 2)
            return super.create_rect_minus(a, b, c, d);
        else
            return new Rect((int)left, (int)up, (int)(right), (int)down);
    }

    public void update_koord(){
            left =left + width / speed;
            right = right + width / speed;
    }

    public int get_damage(){
        return damage;
    }
    public int get_size(){
        return size;
    }

    public void collision(List<Bullet> bullet_list){ // при столкновени по умолчанию удаляем
        bullet_list.remove(this);
    }
}
