package com.example.airplane.Sprites.Bad;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.airplane.DrawThread;
import com.example.airplane.ImageResource;
import com.example.airplane.Params;
import com.example.airplane.Sprites.Good.Bullet;
import com.example.airplane.Sprites.Sprite;

import java.util.List;

public class Boss extends Enemy{
    private DrawThread drawThread;
    private List<Enemy> enemyList;

    private long time_one, time_center = 2000;
    private boolean back = true;
    List<Byte> enemys;
    private int count;
    private int step = 0, number = 1;

    private boolean bullet_is_created = false, meteor_is_created = false, turret_is_created = false;
    private static boolean meteor_flag = false, turret_flag = false;

    public Boss(Context context, DrawThread drawThread, List<Enemy> enemyList) {
        super(ImageResource.BOSS, Sprite.height/32, Sprite.width, 31* Sprite.height/32, 14* Sprite.width/10,
                context, Params.boss_damage, Params.boss_speed, Params.boss_hp, 6);
        this.drawThread = drawThread;
        this.enemyList = enemyList;
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
                    create_big_meteor();
                    meteor_is_created = true;
                    meteor_flag = true;
                }
                break;
            case 4:
                if (!turret_is_created){
                    create_turrets();
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
    private boolean flag = true;
    public void center(long time_center){ //босс выходит в центер и уходит назад, number - номер выхода
        if (left > Sprite.width/2 && back) {
            left = left - (Sprite.width / speed);
            right = right - (Sprite.width / speed);
            time_one = System.currentTimeMillis();
        } //else if (System.currentTimeMillis() - time_one < time_center){}
        else if (System.currentTimeMillis() - time_one > time_center){
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
                }
            }
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
                            create_birds();
                            step++;
                            break;
                        case 2:
                            create_packmans();
                            step++;
                            break;
                        case 3:
                            create_bird_and_sun();
                            step++;
                            break;
                        case 4:
                            step++;
                            break;
                    }
                }
    }

    public void create_birds(){
        enemyList.add(new Bird(1* Sprite.height/32, 0, context));
        enemyList.add(new Bird(3*height/32, 0, context));
        enemyList.add(new Bird(5*height/32, 0, context));
        enemyList.add(new Bird(7*height/32, 0, context));
        enemyList.add(new Bird(9*height/32, 0, context));
        enemyList.add(new Bird(11*height/32, 0, context));
        enemyList.add(new Bird(13*height/32, 0, context));
        enemyList.add(new Bird(15*height/32, 0, context));
        enemyList.add(new Bird(17*height/32, 0, context));
        enemyList.add(new Bird(19*height/32, 0, context));
        enemyList.add(new Bird(21*height/32, 0, context));
        enemyList.add(new Bird(23*height/32, 0, context));
        enemyList.add(new Bird(25*height/32, 0, context));
        enemyList.add(new Bird(27*height/32, 0, context));
        enemyList.add(new Bird(29*height/32, 0, context));
    }

    public void create_big_bullets(){
        enemyList.add(new Bullet_enemy(context, 9));
        enemyList.add(new Bullet_enemy(context, 19));
    }

    public void create_packmans(){
        enemyList.add(new Packman(1*height/32, 0, context, enemyList));
        enemyList.add(new Packman(6*height/32, 0, context, enemyList));
        enemyList.add(new Packman(11*height/32, 0, context, enemyList));
        enemyList.add(new Packman(16*height/32, 0, context, enemyList));
        enemyList.add(new Packman(21*height/32, 0, context, enemyList));
        enemyList.add(new Packman(26*height/32, 0, context, enemyList));

    }

    public void create_big_meteor(){
        enemyList.add(new Big_meteor(context, enemyList));
    }

    public void create_bird_and_sun(){
        enemyList.add(new Bird(1* Sprite.height/32, 0, context));
        enemyList.add(new Bird(7*height/32, 0, context));
        enemyList.add(new Bird(13*height/32, 0, context));
        enemyList.add(new Bird(19*height/32, 0, context));
        enemyList.add(new Bird(25*height/32, 0, context));
        enemyList.add(new Sun(1*height/32, 0, context));
        enemyList.add(new Sun(7*height/32, 0, context));
        enemyList.add(new Sun(13*height/32, 0, context));
        enemyList.add(new Sun(19*height/32, 0, context));
        enemyList.add(new Sun(25*height/32, 0, context));
    }

    public void create_turrets(){
        enemyList.add(new Enemy_turret(context, 5*height/32, enemyList));
        enemyList.add(new Enemy_turret(context, 19*height/32, enemyList));
    }

}
