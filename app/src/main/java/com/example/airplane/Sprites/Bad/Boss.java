package com.example.airplane.Sprites.Bad;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.airplane.DrawThread;
import com.example.airplane.ImageResource;
import com.example.airplane.Params;
import com.example.airplane.Sprites.Good.Bullet;
import com.example.airplane.Sprites.Good.Elain;
import com.example.airplane.Sprites.Sprite;

import java.util.List;
import android.os.Handler;
import android.os.Message;

public class Boss extends Enemy{
    private DrawThread drawThread;
    private List<Enemy> enemyList;
    private List<Bullet> bulletList;

    private long time_one, time_center = 5000;
    private boolean back = true;
    List<Byte> enemys;
    private int count;

    private int step, number;
    private Handler handler;

    private boolean bullet_is_created = false, meteor_is_created = false, turret_is_created = false;
    private static boolean meteor_flag, turret_flag;

    public Boss(Context context, DrawThread drawThread, List<Enemy> enemyList, List<Bullet> bulletList, Handler handler) {
        super(ImageResource.BOSS, Sprite.height/32, Sprite.width, 31* Sprite.height/32, 14* Sprite.width/10,
                context, Params.boss_damage, Params.boss_speed, Params.boss_hp, 6);
        this.drawThread = drawThread;
        this.enemyList = enemyList;
        this.bulletList = bulletList;
        this.handler = handler;
        step = 0; number = 1;
        Boss.elain_in_center = false;
        Boss.time_center_one = 0;
        Boss.meteor_flag = false;
        Boss.turret_flag = false;
        flag_f = false;
        flag = true;
    }

    @Override
    public void update_koord() {
        switch (step) {
            case 8:
            case 0:
                center(time_center);
                break;
            case 7:
                if (turret_flag) {
                    break;
                }
            case 5:
                if (meteor_flag) {
                    break;
                }
            case 3:
            case 1:
                different_enemy(enemys);
                break;
            case 2:
            case 4:
            case 6:
                if (enemyList.size() == 1 || meteor_flag || turret_flag){
                    center(time_center);
                }
                break;
        }
    }

    @Override
    public void collision(@NonNull Bullet two, List<Enemy> enemy_list) {}

    public static void set_meteor_flag(boolean a){
        meteor_flag = a;
    }
    public static void set_turret_flag(boolean a){
        turret_flag = a;
    }

    public void center_switch(int number){
        switch(number){
            case 1:
                break;
            case 2:
                if (!bullet_is_created){
                    create_big_bullets();
                    bullet_is_created = true;
                }
                break;
            case 3:
                if (!meteor_is_created){
                    create_big_meteor(enemyList, context);
                    meteor_is_created = true;
                    meteor_flag = true;
                }
                break;
            case 4:
                if (!turret_is_created){
                    create_turrets(enemyList, context);
                    turret_is_created = true;
                    turret_flag = true;
                }
                break;
        }
    }

    public void enemy_switch(int number){
        switch (number){
            case 1:
                enemys = DrawThread.create_level(7, 7, 7, 5, 4, 5, 3,1, 1, 1);
                //enemys = DrawThread.create_level(0, 0, 0, 0, 0, 0, 0,0, 0, 0);
                break;
            case 2:
                enemys = DrawThread.create_level(9, 9, 8, 6, 5, 6, 3,2, 1, 2);
                //enemys = DrawThread.create_level(0, 0, 0, 0, 0, 0, 0,0, 0, 0);
                break;
            case 3:
                enemys = DrawThread.create_level(11, 10, 10, 6, 7, 6, 5, 3, 2, 4);
                //enemys = DrawThread.create_level(0, 0, 0, 0, 0, 0, 0,0, 0, 0);
                break;
            case 4:
                enemys = DrawThread.create_level(15, 12, 10, 7, 8, 6, 6, 4, 2, 6);
                //enemys = DrawThread.create_level(0, 0, 0, 0, 0, 0, 0,0, 0, 0);
                break;
        }
        count = enemys.size();
    }
    private boolean flag, flag_f;
    private boolean[] flags_number = new boolean[6];
    public void center(long time_center){ //босс выходит в центер и уходит назад, number - номер выхода
        if (left > Sprite.width/2 && back) {
            left = left - (Sprite.width / speed);
            right = right - (Sprite.width / speed);
            time_one = System.currentTimeMillis();
            if (!flag_f) {
                handler.handleMessage(Message.obtain(handler, 4, 0, 0));
                handler.sendMessage(Message.obtain(handler,6,0,0));
                flag_f = true;
            }
        } else if (!flags_number[number] ){

            if (System.currentTimeMillis() - time_one > time_center)
                center_action();
            else
                if (number == 1)
                    handler.sendMessage(Message.obtain(handler,6,1,0));
                if (number == 2)
                    handler.sendMessage(Message.obtain(handler,6,4,0));
                if (number == 3)
                    handler.sendMessage(Message.obtain(handler,6,5 ,0));
                if (number == 4)
                    handler.sendMessage(Message.obtain(handler,6,6 ,0));
                if (number == 5)
                    handler.sendMessage(Message.obtain(handler,6,7 ,0));

        } else {
            if (flag) {
                center_switch(number);
                flag = false;
            }
            else {
                back = false;
                if (left < Sprite.width) {
                    left = left + (Sprite.width / speed);
                    right = right + (Sprite.width / speed);
                } else {
                    step++; // как только босс ушел - следующий шаг
                    back = true;
                    enemy_switch(number);
                    number++;
                    flag = true;
                    flag_f = false;
                    handler.handleMessage(Message.obtain(handler, 5, 0, 0));
                    if (number == 6)
                        handler.sendMessage(Message.obtain(handler,0,1, 0));
                }
            }
        }
    }
    private boolean elain_is_created = false, bullet_elain_is_created = false;
    private static boolean elain_in_center = false;

    public static void set_elain_in_center(){
        Boss.elain_in_center = true;
        time_center_one = System.currentTimeMillis();
    }

    public void addTime_one(long time_one) {
       // if (this.time_one != 0)
            this.time_one += time_one;
    }

    public static void addTime_center_one(long time_center_one) {
        if (Boss.time_center_one != 0)
            Boss.time_center_one += time_center_one;
    }

    private static long time_center_one;
    public void center_action(){
        switch(number){
            case 1:
                if (!elain_is_created){
                    bulletList.add(new Elain(context));
                    elain_is_created = true;
                }
                else if (!bullet_elain_is_created && elain_in_center){
                    if (System.currentTimeMillis() - time_center_one > 10000) {
                        Bullet_enemy bullet = new Bullet_enemy(new double[]{height / 2 - 3 * height / 32, width - 6 * height / 32, height / 2 + 3 * height / 32, width},
                                context, 1, 2) {
                            @Override
                            public void collision(@NonNull Bullet two, List<Enemy> enemy_list) {
                                if (two instanceof Elain){
                                    enemy_list.remove(this);
                                }
                            }

                            @Override
                            public void update_koord() {
                                speed = Params.bullet_stats[5];
                                super.update_koord();
                            }
                        };
                        enemyList.add(bullet);
                        bullet_elain_is_created = true;
                    } else {
                        if (System.currentTimeMillis() - time_center_one < 5000) {
                            handler.sendMessage(Message.obtain(handler, 6, 2, 0));
                        }
                        else {
                            handler.sendMessage(Message.obtain(handler, 6, 3, 0));
                        }
                    }
                } else if (!Elain.get_alive()){
                    flags_number[1] = true;
                    speed /= 2;
                }
                break;
            case 2:
                flags_number[2] = true;
                break;
            case 3:
                flags_number[3] = true;
                break;
            case 4:
                flags_number[4] = true;
                break;
            case 5:
                flags_number[5] = true;
                break;
        }
    }

    private int current_enemy = -1;

    public void different_enemy(List<Byte> enemys){
                if (current_enemy == -1){
                    current_enemy = (int)(Math.random()*count);
                }
                else if (enemys.size() > 0 && drawThread.create_enemy(enemys.get(current_enemy))){
                    enemys.remove(current_enemy);
                    current_enemy = -1;
                    count--;
                } else if (count == 0 && enemyList.size() == 1){
                    switch(number-1) {
                        case 1:
                            create_birds(enemyList, context);
                            step++;
                            break;
                        case 2:
                            create_packmans(enemyList, context);
                            step++;
                            break;
                        case 3:
                            create_bird_and_sun(enemyList, context);
                            step++;
                            break;
                        case 4:
                            step++;
                            break;
                    }
                }
    }

    public void create_big_bullets(){
        enemyList.add(new Bullet_enemy(context, 9));
        enemyList.add(new Bullet_enemy(context, 19));
        handler.handleMessage(Message.obtain(handler, 5, 0, 0));
    }

    public static boolean create_birds(List<Enemy> enemyList, Context context){
        int c = (int)(Math.random()*4) + 1;
        enemyList.add(new Bird(1* Sprite.height/32, 0, context, c));
        enemyList.add(new Bird(3*height/32, 0, context, c));
        enemyList.add(new Bird(5*height/32, 0, context, c));
        enemyList.add(new Bird(7*height/32, 0, context, c));
        enemyList.add(new Bird(9*height/32, 0, context, c));
        enemyList.add(new Bird(11*height/32, 0, context, c));
        enemyList.add(new Bird(13*height/32, 0, context, c));
        enemyList.add(new Bird(15*height/32, 0, context, c));
        enemyList.add(new Bird(17*height/32, 0, context, c));
        enemyList.add(new Bird(19*height/32, 0, context, c));
        enemyList.add(new Bird(21*height/32, 0, context, c));
        enemyList.add(new Bird(23*height/32, 0, context, c));
        enemyList.add(new Bird(25*height/32, 0, context, c));
        enemyList.add(new Bird(27*height/32, 0, context, c));
        enemyList.add(new Bird(29*height/32, 0, context, c));
        return true;
    }

    public static boolean create_packmans(List<Enemy> enemyList, Context context){
        int c = (int)(Math.random()*4) + 1;
        enemyList.add(new Packman(1*height/32, 0, context, enemyList, c));
        enemyList.add(new Packman(6*height/32, 0, context, enemyList, c));
        enemyList.add(new Packman(11*height/32, 0, context, enemyList, c));
        enemyList.add(new Packman(16*height/32, 0, context, enemyList, c));
        enemyList.add(new Packman(21*height/32, 0, context, enemyList, c));
        enemyList.add(new Packman(26*height/32, 0, context, enemyList, c));
        return true;
    }

    public static boolean create_big_meteor(List<Enemy> enemyList, Context context){
        enemyList.add(new Big_meteor(context, enemyList));
        return true;
    }

    public static boolean create_bird_and_sun(List<Enemy> enemyList, Context context){
        int c = (int)(Math.random()*4) + 1;
        enemyList.add(new Bird(1* Sprite.height/32, 0, context, c));
        enemyList.add(new Bird(7*height/32, 0, context, c));
        enemyList.add(new Bird(13*height/32, 0, context, c));
        enemyList.add(new Bird(19*height/32, 0, context, c));
        enemyList.add(new Bird(25*height/32, 0, context, c));
        enemyList.add(new Sun(1*height/32, 0, context, 5 - c));
        enemyList.add(new Sun(7*height/32, 0, context, 5 - c));
        enemyList.add(new Sun(13*height/32, 0, context, 5 - c));
        enemyList.add(new Sun(19*height/32, 0, context, 5 - c));
        enemyList.add(new Sun(25*height/32, 0, context, 5 - c));
        return true;
    }

    public static boolean create_turrets(List<Enemy> enemyList, Context context){
        enemyList.add(new Enemy_turret(context, 5*height/32, enemyList));
        enemyList.add(new Enemy_turret(context, 19*height/32, enemyList));
        return true;
    }

}
