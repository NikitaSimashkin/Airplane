package com.example.airplane;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class DrawThread extends Thread{
    private SurfaceHolder surfaceHolder;
    private Context context;
    private Handler handler;
    private int number; //номер уровня

    private Canvas canvas;

    private static int width, height;

    private Samolet samolet;
    private Base base;

    private long time = System.currentTimeMillis();
    private long time_bullet_last = System.currentTimeMillis();
    private long lastFrame = System.currentTimeMillis();

    protected List<Enemy> enemy_list = new ArrayList<>();
    protected List<Bullet> bullet_list = new ArrayList<>();

    private int enemys, points, temp;

    public DrawThread (SurfaceHolder surfaceHolder, Context context, int width, int height, Handler handler, int number){
        super();
        this.surfaceHolder = surfaceHolder;
        this.context = context;
        this.handler = handler;
        this.number = number;
        DrawThread.width = width;
        DrawThread.height = height;

        samolet = new Samolet(height/50, 0, (int)(height/5), width/7, context);
        base = new Base();
        start_options(number);
    }
    public static int get_height(){
        return height;
    }

    public static int get_width(){
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
    }

    public void create_bullets() {
        if (System.currentTimeMillis() - time_bullet_last >= 2000) {
            time_bullet_last = System.currentTimeMillis() ;
            int[] koord_samolet = samolet.get_koord();
            bullet_list.add(new Bullet(
                    (koord_samolet[0] + koord_samolet[2]) / 2 - (koord_samolet[2] - koord_samolet[0]) / 4,
                    2 * (koord_samolet[1] + koord_samolet[3]) / 3,
                    (koord_samolet[0] + koord_samolet[2]) / 2 + (koord_samolet[2] - koord_samolet[0]) / 4,
                    koord_samolet[3],
                    context, 1));

        }
    }

    public boolean create_meteor(){
        if (System.currentTimeMillis() - time >= time_meteor){
            enemys = (int)(Math.random()*7);
            enemy_list.add(new Meteor(height/32 + enemys*(4 * height/32), width,
                    5*height/32 + enemys*(4 * height/32) , width*15/14, context, (int)(Math.random()*4 + 1)));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_alien(){
        if (System.currentTimeMillis() - time >= time_alien){
            enemys = (int)(Math.random()*9);
            enemy_list.add(new Alien(height/32 + enemys*(3 * height/32), width,
                    7*height/32 + enemys*(3 * height/32) , width*14/13, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_alien_two(){
        if (System.currentTimeMillis() - time >= time_alien_two){
            enemys = (int)(Math.random()*9);
            enemy_list.add(new Alien_two(height/32 + enemys*(3 * height/32), width,
                    7*height/32 + enemys*(3 * height/32) , width*14/13, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_packman(){
        if (System.currentTimeMillis() - time >= time_packman){
            enemys = (int)(Math.random()*14);
            enemy_list.add(new Packman(height/32 + enemys*(2 * height/32), width,
                    6*height/32 + enemys*(2 * height/32) , width*13/12, context, this));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_bird(){
        if (System.currentTimeMillis() - time >= time_bird){
            enemys = (int)(Math.random()*15);
            enemy_list.add(new Bird(height/32 + enemys*(2*height /32), width,
                    3*height/32 + enemys*(2*height /32) , width*14/13, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_sun(){
        if (System.currentTimeMillis() - time >= time_sun){
            enemys = (int)(Math.random()*9);
            enemy_list.add(new Sun(height/32 + enemys*(3*height /32), width,
                    7*height/32 + enemys*(3*height /32) , width*12/11, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_megasun(){
        if (System.currentTimeMillis() - time >= time_megasun){
            enemy_list.add(new Megasun(height/32, width,
                    31*height/32, width*11/10, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_cat(){
        if (System.currentTimeMillis() - time >= time_cat){
            enemys = (int)(Math.random()*6);
            enemy_list.add(new Cat(height/32 + enemys*(4*height /32), width,
                    11*height/32 + enemys*(4*height /32) , width*12/10, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_yellow(){
        if (System.currentTimeMillis() - time >= time_yellow){
            enemys = (int)(Math.random()*6);
            enemy_list.add(new Yellow(height/32 + enemys*(3*height /32), width,
                    16*height/32 + enemys*(3*height /32) , width*15/10, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_heart(){
        if (System.currentTimeMillis() - time >= time_heart){
            enemys = (int)(Math.random()*6);
            enemy_list.add(new Heart(height/32 + enemys*(4 * height/32), width,
                    5*height/32 + enemys*(4 * height/32) , width*15/14, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public void create_enemy(int enemy_number){
        switch (enemy_number){
            case 1:
                create_meteor();
                break;
            case 2:
                create_alien();
                break;
            case 3:
                create_alien_two();
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
            case 11:
                break;
            case 12:
                break;
        }
    }


    public void update_enemy() {
        for (int i = 0; i < enemy_list.size(); i++){
            enemy_list.get(i).update_koord(); //обновляет координаты
            if (Enemy.check_two(samolet, enemy_list.get(i), new int[]{width/100, height/150, -width/100, -height/150,
                    width/100, 0, -width/100, 0}))  //проверяет столкновение с самолетом или стеной
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
                    if (enemy_list.get(i).get_alive() && Sprite.check_two(bullet_list.get(j), enemy_list.get(i), new int[]{width/100, height/150, -width/100, -height/150,
                            width/100, 0, -width/100, 0})){
                        enemy_list.get(i).collision(bullet_list.get(j), this);
                        bullet_list.remove(j);
                        break;
                    }
                }
            }
        }
    }

    public void draw_all(){

        try {
            canvas = surfaceHolder.lockCanvas();

            Paint clearPaint = new Paint(); //очистка холста
            clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            canvas.drawRect(0, 0, width, height, clearPaint);

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

            samolet.draw(canvas, null); //рисуем самолет

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
        catch (NullPointerException e){
            enemy_list.removeAll(enemy_list);
            bullet_list.removeAll(bullet_list);
        }
    }

    private long last_frame = System.currentTimeMillis();

    @Override
    public void run() {
        while (!isInterrupted()){ //сначала он проводит все вычисления, а потом уже все рисует в одном методе
            if (System.currentTimeMillis() - last_frame > 16)
                last_frame = System.currentTimeMillis();
                level(number);
                update_samolet(); //обновляет координаты самолета
                update_bullets(); //обновляет координаты самолета
                update_enemy(); //отрисовывает всех врагов
                draw_all();
        }
    }

    private int count_meteor = 0; // переменные для уровней

    public void level(int number){
        switch (number){
            case 1:
                if (count_meteor < 50 && create_packman()){
                    count_meteor++;
                }
                if (count_meteor == 50 && enemy_list.isEmpty()){
                    System.out.println("YouWin");
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
    5 -
    6 -
    7 -
    8 -
    9 -
    10 - final boss
    11 - heart
     */
    private int time_bullet, time_meteor, time_alien, time_alien_two, time_packman, time_bird, time_sun, time_megasun,
            time_cat, time_yellow, time_heart;

    public void start_options(int number) { //стартовые установки для разных уровней
        switch (number) {
            case 1:
                time_meteor = 2000;
                time_alien = 2000;
                time_alien_two = 2000;
                time_packman = 2000;
                time_bird = 2000;
                time_sun = 2000;
                time_megasun = 2000;
                time_cat = 2000;
                time_yellow = 2000;
                time_heart = 2000;
                for(int i = 0; i < 50; i++){
                    mobs.add((byte) 1);
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

