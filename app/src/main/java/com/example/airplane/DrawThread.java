package com.example.airplane;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;

import com.example.airplane.Sprites.Alien;
import com.example.airplane.Sprites.Alien_two;
import com.example.airplane.Sprites.Bird;
import com.example.airplane.Sprites.Bullet;
import com.example.airplane.Sprites.Cat;
import com.example.airplane.Sprites.Enemy;
import com.example.airplane.Sprites.Heart;
import com.example.airplane.Sprites.Many_bullets;
import com.example.airplane.Sprites.MegaBullet;
import com.example.airplane.Sprites.Megasun;
import com.example.airplane.Sprites.Meteor;
import com.example.airplane.Sprites.Packman;
import com.example.airplane.Sprites.Samolet;
import com.example.airplane.Sprites.Sprite;
import com.example.airplane.Sprites.Sun;
import com.example.airplane.Sprites.Yellow;

import java.util.ArrayList;
import java.util.List;

public class DrawThread extends Thread{
    private SurfaceHolder surfaceHolder;
    private Context context;
    private Handler handler;
    private int number; //номер уровня

    private Canvas canvas;

    private static double width, height;

    private Samolet samolet;
    private Base base;
    private int turret_number_bullet = 10; //кол-во пуль в туреле

    private Many_bullets many_bullets;

    private long time = System.currentTimeMillis();
    private long time_bullet_last = System.currentTimeMillis();
    private long last_frame = System.currentTimeMillis();

    protected List<Enemy> enemy_list = new ArrayList<>();
    protected List<Bullet> bullet_list = new ArrayList<>();

    private int enemys, bullet_color = 1, size = 1; //bullet_mode - цвет, size - размер пули

    public DrawThread (SurfaceHolder surfaceHolder, Context context, int width, int height, Handler handler, int number){
        super();
        this.surfaceHolder = surfaceHolder;
        this.context = context;
        this.handler = handler;
        this.number = number;
        DrawThread.width = width;
        DrawThread.height = height;
        Many_bullets.alive = false;
        handler.sendMessage(Message.obtain(handler, 3, 0, 0)); // меняем отображение размера пули

        samolet = new Samolet(context);
        base = new Base();
        start_options(number);
    }
    public static double get_height(){
        return height;
    }

    public static double get_width(){
        return width;
    }

    public Samolet get_Samolet(){
        return samolet;
    }

    public Base get_base(){
        return base;
    }

    public void update_samolet(){
        samolet.buttons();
    }

    public void update_bullets() {
        for (int i = 0; i < bullet_list.size(); i++)
            if (bullet_list.get(i).get_koord()[1] <= width)
                bullet_list.get(i).update_koord(); //обновляет координаты
            else {
                bullet_list.remove(i);
                i--;
            }

            if(samolet.turret_exist()){
                samolet.get_turret().create_bullet();
            }

            if (Many_bullets.alive){
                many_bullets.create_bullets();
            }
    }

    public void change_bullet_color(int color){
        bullet_color = color;
    }
    public void set_bullet_size(int size) {
        this.size = size;
    }
    public void change_bullet_time(double m) {
        time_bullet *= m;
    }

    public void create_bullets() {
        if (System.currentTimeMillis() - time_bullet_last >= time_bullet) {
            bullet_list.add(new Bullet(samolet.get_koord(), context, bullet_color, size));
            time_bullet_last = System.currentTimeMillis();

        }
    }

    public boolean create_meteor(){
        if (System.currentTimeMillis() - time >= time_meteor){
            enemys = (int)(Math.random()*14);
            enemy_list.add(new Meteor((height/32 + enemys*(2 * height/32)),0, context, (int)(Math.random()*4 + 1)));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_alien(){
        if (System.currentTimeMillis() - time >= time_alien){
            enemys = (int)(Math.random()*9);
            enemy_list.add(new Alien(height/32 + enemys*(3 * height/32), 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_alien_two(){
        if (System.currentTimeMillis() - time >= time_alien_two){
            enemys = (int)(Math.random()*9);
            enemy_list.add(new Alien_two(height/32 + enemys*(3 * height/32), 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_packman(){
        if (System.currentTimeMillis() - time >= time_packman){
            enemys = (int)(Math.random()*14);
            enemy_list.add(new Packman(height/32 +enemys*(2 * height/32),0, context, enemy_list));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_bird(){
        if (System.currentTimeMillis() - time >= time_bird){
            enemys = (int)(Math.random()*15);
            enemy_list.add(new Bird(height/32 + enemys*(2*height /32), 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_sun(){
        if (System.currentTimeMillis() - time >= time_sun){
            enemys = (int)(Math.random()*9);
            enemy_list.add(new Sun(height/32 + enemys*(3*height /32), 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_megasun(){
        if (System.currentTimeMillis() - time >= time_megasun){
            enemy_list.add(new Megasun(height/32, 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_cat(){
        if (System.currentTimeMillis() - time >= time_cat){
            enemys = (int)(Math.random()*6);
            enemy_list.add(new Cat(height/32 + enemys*(4*height /32), 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_yellow(){
        if (System.currentTimeMillis() - time >= time_yellow){
            enemys = (int)(Math.random()*6);
            enemy_list.add(new Yellow(height/32 + enemys*(3*height /32), 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_heart(){
        if (System.currentTimeMillis() - time >= time_heart){
            enemys = (int)(Math.random()*14);
            enemy_list.add(new Heart(height/32 + enemys*(2 * height/32), 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_turret(){
        if (!samolet.turret_exist()){
            samolet.new Turret(bullet_color, bullet_list, size);
            return true;
        }
        return false;
    }

    public boolean create_many_bullet(){
        many_bullets = new Many_bullets(bullet_color, size, this, bullet_list, context);
        return true;
    }

    public boolean create_megabullet(){
        bullet_list.add(new MegaBullet(samolet.get_koord(), context));
        return true;
    }

    private int counter = 0;

    public boolean create_enemy(int enemy_number){
        switch (enemy_number){
            case 1:
                return create_meteor();
            case 2:
                return create_alien();
            case 3:
                return  create_alien_two();
            case 4:
                return create_packman();
            case 5:
                return create_bird();
            case 6:
                return create_sun();
            case 7:
                return create_cat();
            case 8:
                return create_yellow();
            case 9:
                return create_megasun();
            case 10:
                return create_heart();
            case 11:
            case 12:

        }
        return false;
    }


    public void update_enemy() {
        for (int i = 0; i < enemy_list.size(); i++){
            enemy_list.get(i).update_koord(); //обновляет координаты
            if (Enemy.check_two(samolet, enemy_list.get(i), new double[]{(width/100), (height/150), -(width/100),
                    -(height/150), (width/100), 0, -(width/100), 0}))  //проверяет столкновение с самолетом или стеной
            {
                samolet.change_hp(-enemy_list.get(i).get_damage());
                enemy_list.remove(i);
                i--;
                if (samolet.get_hp() > 0) // снимаем хп у самолета
                    handler.sendMessage(Message.obtain(handler, 1, samolet.get_hp(), 0));
                else
                    handler.sendMessage(Message.obtain(handler,0,100, 0));
                //TODO: второй аргумент - кол-во волн если бесконечный режим и кол-во очков если уровень

                // можем передать 3 аргумента: 1 - что меняем; 2 и 3 - как меняем
                // what: 1 - хп самолета, 2 - хп базы
                // agr1 - сколько хп нужно установить
            }
            else if (samolet.turret_exist() && Enemy.check_two(samolet.get_turret(), enemy_list.get(i), new double[]{(width/100), (height/150), -(width/100),
                    -(height/150), (width/100), 0, -(width/100), 0})){ //если турель есть, то проверяем столкновение с ней
                        samolet.get_turret().change_hp(-enemy_list.get(i).get_damage());
                        enemy_list.remove(i);
                        i--;
                }
            else if(enemy_list.get(i).get_koord()[1] <= 0){
                base.change_hp(-enemy_list.get(i).get_damage());
                enemy_list.remove(i);
                i--;
                if (base.get_hp() > 0)
                    handler.sendMessage(Message.obtain(handler, 2, base.get_hp(), 0));
                else
                    handler.sendMessage(Message.obtain(handler,0,100, 0));
            }

            else { // столкновение с пулей
                for (int j = 0; j < bullet_list.size(); j++){
                    if (enemy_list.get(i).get_alive() && Sprite.check_two(bullet_list.get(j), enemy_list.get(i), new double[]{(width/100), (height/150), -(width/100),
                            -(height/150), (width/100), 0, -(width/100), 0})){
                        enemy_list.get(i).collision(bullet_list.get(j), enemy_list);
                        bullet_list.get(j).collision(bullet_list);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
        enemy_list.clear();
        bullet_list.clear();
    }

    public void draw_all(){

        try {
            canvas = surfaceHolder.lockCanvas();

            Paint clearPaint = new Paint(); //очистка холста
            clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            canvas.drawRect(0, 0, (int)width, (int)height, clearPaint);

            for (int i = 0; i < enemy_list.size(); i++) { //отрисовываем врагов
                if (enemy_list.get(i).getTime_death() == 0 || System.currentTimeMillis() - enemy_list.get(i).getTime_death() <= 2000) {
                    enemy_list.get(i).draw(canvas, null);
                } else {
                    enemy_list.remove(i);
                    i--;
                }
            }
            for (int i = 0; i < bullet_list.size(); i++) { //отрисовываем пули
                bullet_list.get(i).draw(canvas, null);
            }

            if (samolet.turret_exist()) // рисуем турель
                {
                    samolet.get_turret().draw(canvas, null);
                }
            samolet.draw(canvas, null); //рисуем самолет

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
        catch (NullPointerException e){
            enemy_list.clear();
            bullet_list.clear();
        }
    }

    @Override
    public void run() {
        while (!isInterrupted()){ //сначала он проводит все вычисления, а потом уже все рисует в одном методе
            if (System.currentTimeMillis() - last_frame > 33)
                try {
                    last_frame = System.currentTimeMillis();
                    level(number);
                    update_samolet(); //обновляет координаты самолета
                    update_bullets(); //обновляет координаты пуль
                    update_enemy(); //отрисовывает всех врагов
                    draw_all();
                } catch (Exception e){
                    handler.sendMessage(Message.obtain(handler,0,100, 0));
                }
        }
    }

    private int count = 0; // переменные для уровней
    private int current_enemy = -1;

    public void level(int number){
        switch (number){
            case 1:
//                if (current_enemy == -1){
//                    current_enemy = (int)(Math.random()*count);
//                } else if (mobs.size() > 0 && create_enemy(mobs.get(current_enemy)+1)){
//                    mobs.remove(current_enemy);
//                    current_enemy = -1;
//                    count--;
//                }
                if (count < 5 && create_sun()){
                    count++;
                }
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
        }
    }

    List<Byte> mobs= new ArrayList<Byte>();
    /*
    1 - meteor
    2 - alien
    3 - alien_two
    4 - packman
    5 - bird
    6 - sun
    7 - cat
    8 - yellow
    9 - megasun
    10 - heart
    11 - boss
     */
    private int time_bullet, time_meteor, time_alien, time_alien_two, time_packman, time_bird, time_sun, time_megasun,
            time_cat, time_yellow, time_heart;

    public void start_options(int number) { //стартовые установки для разных уровней
        switch (number) {
            case 1:
                time_bullet = 2000;
                time_meteor = 2000;
                time_alien = 2000;
                time_alien_two = 2000;
                time_packman = 0;
                time_bird = 2000;
                time_sun = 2000;
                time_megasun = 2000;
                time_cat = 2000;
                time_yellow = 2000;
                time_heart = 2000;
                for(int i = 0; i < 10; i++){
                    mobs.add((byte) i);
                }
                break;
            case 2:
                time_meteor = 4000;
                time_alien = 3000;
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
        }
    }
}

