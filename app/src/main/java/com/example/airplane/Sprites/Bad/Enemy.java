package com.example.airplane.Sprites.Bad;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Message;

import androidx.annotation.NonNull;

import com.example.airplane.DrawThread;
import com.example.airplane.ImageResource;
import com.example.airplane.MusicResorces;
import com.example.airplane.Params;
import com.example.airplane.Sprites.Good.Bullet;
import com.example.airplane.Sprites.Sprite;

import java.util.List;
import java.util.logging.Handler;

public class Enemy extends Sprite {

    protected int damage, speed, hp;
    protected boolean alive = true;
    protected long time_death = 0; // время смерти
    protected int color, points;

    public Enemy(Bitmap bitmap, double up, double left, double down, double right, Context context, int damage, int speed, int hp, int color) {
        super(bitmap, up, left, down, right, context);
        this.damage = (int)(Params.get_start_damage()*damage);
        this.speed = (int)(Params.get_start_speed()*speed) ;
        this.hp = (int)(Params.get_start_hp()*hp);
        this.color = color;
    }

    public Enemy(ImageResource imageResource, double up, double left, double down, double right, Context context, int damage, int speed, int hp, int color) {
        super(imageResource, up, left, down, right, context);
        this.damage = (int)(Params.get_start_damage()*damage);
        this.speed = (int)(Params.get_start_speed()*speed) ;
        this.hp = (int)(Params.get_start_hp()*hp);
        this.color = color;
    }

    public void update_koord(){
        if (alive) {
            left = left - (width / speed);
            right = right - (width / speed);
        }
    }

    public long getTime_death() {
        return time_death;
    }
    public void addTime_death(long time_death) {
        this.time_death += time_death;
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
        DrawThread.add_points(points);
        DrawThread.points_handler();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


    public void collision(@NonNull Bullet two, List<Enemy> enemy_list){
        if (color == two.getColor()){
            change_hp(-2*two.get_damage());
        }
        else if (color + two.getColor() == 5){
            change_hp((int)(-0.5*two.get_damage()));
        }
        else {
            change_hp(-two.get_damage());
        }
        MusicResorces.play(MusicResorces.enemy_damage);
    }

}
