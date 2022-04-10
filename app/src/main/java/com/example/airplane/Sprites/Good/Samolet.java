package com.example.airplane.Sprites.Good;

import android.content.Context;

import com.example.airplane.ImageResource;
import com.example.airplane.Params;
import com.example.airplane.Sprites.Sprite;

import java.util.List;

public class Samolet extends Sprite {

    private int up_or_down, speed = (int)height/ Params.samolet_speed, hp = Params.samolet_hp;
    private boolean updown;
    private Context context;
    private boolean turret_exist = false;
    private Turret turret;

    public Samolet(Context context) {
        super(ImageResource.SPACESHIP, height/32, 0, (height/5), width/7, context);
        this.context = context;
    }

    public boolean turret_exist(){
        return turret_exist;
    }

    public Turret get_turret(){
        return turret;
    }

    public void create_turret(){

    }

    public void update_koord(double a, double b){ //метод меняет координаты самолета
        if (!(up + a < height/32 || down + b > 31*height/32)) { //это условие проверяет выход за границы
            up = up + a;
            down = down + b;
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

    public static final int max_turret = 1;

    public class Turret extends Sprite{
        private List<Bullet> bullet_list;
        private int damage = Params.turret_damage, hp = Params.turret_hp, color, n, number_bullet, size;
        private long last_bullet, time_bullet;

        public Turret(int color, List<Bullet> bullet_list, int size) {
            super(ImageResource.TURRET, Samolet.this.up, Samolet.this.left,
                    Samolet.this.down ,width/8, Samolet.this.context);
            this.color = color;
            this.size = size;
            this.bullet_list = bullet_list;
            Samolet.this.turret_exist = true;
            Samolet.this.turret = Turret.this;
            switch (size){
                case 0:
                    n = 5;
                    time_bullet = 4000;
                    break;
                case 1:
                    time_bullet = 2000;
                    n = 10;
                    break;
                case 2:
                    time_bullet = 500;
                    n = 20;
                    break;
            }

            last_bullet = System.currentTimeMillis();
        }

        public void create_bullet(){
            if (System.currentTimeMillis() - last_bullet > time_bullet) {
                bullet_list.add(new Bullet(new double[]{up, left, down, right}, context, color, size));
                number_bullet++;
                if (number_bullet == n) {
                    Samolet.this.turret_exist = false;
                    Samolet.this.turret = null;
                }
                last_bullet = System.currentTimeMillis();
            }
        }

        public int get_hp(){
            return hp;
        }

        public boolean change_hp(int a){ // возвращает тру или фолт в зависимости от того жива турель или нет
            hp += a;
            if (hp > Params.turret_hp) {
                hp = Params.turret_hp;
                return true;
            }
            else if (hp > 0)
                return true;
            else {
                Samolet.this.turret_exist = false;
                Samolet.this.turret = null;
                return false;
            }
        }
    }
}

