package com.example.airplane;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;

import com.example.airplane.Sprites.Bad.Alien;
import com.example.airplane.Sprites.Bad.Alien_two;
import com.example.airplane.Sprites.Bad.Bird;
import com.example.airplane.Sprites.Bad.Boss;
import com.example.airplane.Sprites.Good.Bullet;
import com.example.airplane.Sprites.Bad.Cat;
import com.example.airplane.Sprites.Bad.Enemy;
import com.example.airplane.Sprites.Bad.Heart;
import com.example.airplane.Sprites.Good.Many_bullets;
import com.example.airplane.Sprites.Good.MegaBullet;
import com.example.airplane.Sprites.Bad.Megasun;
import com.example.airplane.Sprites.Bad.Meteor;
import com.example.airplane.Sprites.Bad.Packman;
import com.example.airplane.Sprites.Good.Samolet;
import com.example.airplane.Sprites.Sprite;
import com.example.airplane.Sprites.Bad.Sun;
import com.example.airplane.Sprites.Bad.Yellow;

import java.util.ArrayList;
import java.util.List;

public class DrawThread extends Thread{
    private SurfaceHolder surfaceHolder;
    private Context context;
    private Handler handler;
    private int time_bullet = Params.time_bullet_normal, number; //номер уровня

    private Canvas canvas;

    private static double width, height;

    private Samolet samolet;
    private Base base;

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

    public static List<Byte> create_level(int meteor, int alien, int alien_two, int packman, int bird, int sun,
                                      int cat, int yellow, int megasun, int heart)
    {
        int number = meteor + alien + alien_two + packman + bird + sun + cat + yellow + megasun + heart;
        List<Byte> arr = new ArrayList<Byte>();
        while (meteor > 0){
            arr.add((byte)1);
            meteor--;
        }
        while (alien > 0){
            arr.add((byte)2);
            alien--;
        }
        while (alien_two > 0){
            arr.add((byte)3);
            alien_two--;
        }
        while (packman > 0){
            arr.add((byte)4);
            packman--;
        }
        while (bird > 0){
            arr.add((byte)5);
            bird--;
        }
        while (sun > 0){
            arr.add((byte)6);
            sun--;
        }
        while (cat > 0){
            arr.add((byte)7);
            cat--;
        }
        while (yellow > 0){
            arr.add((byte)8);
            yellow--;
        }
        while (megasun > 0){
            arr.add((byte)9);
            megasun--;
        }
        while (heart > 0){
            arr.add((byte)10);
            heart--;
        }
        return arr;
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
        switch (size){
            case 0:
                time_bullet = Params.time_bullet_big;
                break;
            case 1:
                time_bullet = Params.time_bullet_normal;
                break;
            case 2:
                time_bullet = Params.time_bullet_small;
                break;
        }
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
        if (System.currentTimeMillis() - time >= Params.time_meteor){
            enemys = (int)(Math.random()*14);
            enemy_list.add(new Meteor((height/32 + enemys*(2 * height/32)),0, context, (int)(Math.random()*4 + 1)));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_alien(){
        if (System.currentTimeMillis() - time >= Params.time_alien){
            enemys = (int)(Math.random()*9);
            enemy_list.add(new Alien(height/32 + enemys*(3 * height/32), 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_alien_two(){
        if (System.currentTimeMillis() - time >= Params.time_alien_two){
            enemys = (int)(Math.random()*9);
            enemy_list.add(new Alien_two(height/32 + enemys*(3 * height/32), 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_packman(){
        if (System.currentTimeMillis() - time >= Params.time_packman){
            enemys = (int)(Math.random()*14);
            enemy_list.add(new Packman(height/32 +enemys*(2 * height/32),0, context, enemy_list));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_bird(){
        if (System.currentTimeMillis() - time >= Params.time_bird){
            enemys = (int)(Math.random()*15);
            enemy_list.add(new Bird(height/32 + enemys*(2*height /32), 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_sun(){
        if (System.currentTimeMillis() - time >= Params.time_sun){
            enemys = (int)(Math.random()*9);
            enemy_list.add(new Sun(height/32 + enemys*(3*height /32), 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_megasun(){
        if (System.currentTimeMillis() - time >= Params.time_megasun){
            enemy_list.add(new Megasun(height/32, 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_cat(){
        if (System.currentTimeMillis() - time >= Params.time_cat){
            enemys = (int)(Math.random()*6);
            enemy_list.add(new Cat(height/32 + enemys*(4*height /32), 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_yellow(){
        if (System.currentTimeMillis() - time >= Params.time_yellow){
            enemys = (int)(Math.random()*6);
            enemy_list.add(new Yellow(height/32 + enemys*(2*height /32), 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_heart(){
        if (System.currentTimeMillis() - time >= Params.time_heart){
            enemys = (int)(Math.random()*14);
            enemy_list.add(new Heart(height/32 + enemys*(2 * height/32), 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_boss(){
        enemy_list.add(new Boss(context, this, enemy_list));
        return true;
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

        }
        return false;
    }


    public void update_enemy() {
        for (int i = 0; i < enemy_list.size(); i++){
            Enemy enemy = enemy_list.get(i);
            enemy.update_koord(); //обновляет координаты
            if (Enemy.check_two(samolet, enemy, new double[]{(width/100), (height/150), -(width/100),
                    -(height/150), (width/100), 0, -(width/100), 0}))  //проверяет столкновение с самолетом или стеной
            {
                samolet.change_hp(-enemy.get_damage());
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
            else if (samolet.turret_exist() && Enemy.check_two(samolet.get_turret(), enemy, new double[]{(width/100), (height/150), -(width/100),
                    -(height/150), (width/100), 0, -(width/100), 0}) && !(enemy instanceof Heart)){ //если турель есть, то проверяем столкновение с ней
                        samolet.get_turret().set_death();
                }
            else if(enemy.get_koord()[1] <= 0){
                base.change_hp(-enemy.get_damage());
                enemy_list.remove(i);
                i--;
                if (base.get_hp() > 0)
                    handler.sendMessage(Message.obtain(handler, 2, base.get_hp(), 0));
                else
                    handler.sendMessage(Message.obtain(handler,0,100, 0));
            }

            else { // столкновение с пулей
                for (int j = 0; j < bullet_list.size(); j++){
                    Bullet bullet = bullet_list.get(j);
                    if (enemy.get_alive() && Sprite.check_two(bullet, enemy, new double[]{(width/100), (height/150), -(width/100),
                            -(height/150), (width/100), 0, -(width/100), 0})){
                        enemy.collision(bullet, enemy_list);
                        bullet.collision(bullet_list);
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
            if (System.currentTimeMillis() - last_frame > 0)
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
//                if (count < 5 && create_yellow()){
//                    count++;
//                }
                if (count < 1) {
                    create_boss();
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

    public void start_options(int number) { //стартовые установки для разных уровней
        switch (number) {
            case 1:
                for(int i = 0; i < 10; i++){
                    mobs.add((byte) i);
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
}

