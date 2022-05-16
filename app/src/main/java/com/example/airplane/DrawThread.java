package com.example.airplane;

import android.content.Context;
import android.content.SharedPreferences;
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
    private static Handler handler;
    private int time_bullet = Params.time_bullet_normal, number; //номер уровня
    private double hp_multiplier, damage_multiplier, speed_multiplier;

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

    private SharedPreferences pref;

    private int enemys, bullet_color = 1, size = 1; // bullet_color - цвет, size - размер пули

    private static int points;

    public DrawThread (SurfaceHolder surfaceHolder, Context context, int width, int height, Handler handler, int number){
        super();
        this.surfaceHolder = surfaceHolder;
        this.context = context;
        this.handler = handler;
        this.number = number;
        DrawThread.width = width;
        DrawThread.height = height;
        Many_bullets.alive = false;
        handler.sendMessage(Message.obtain(handler, 2, 0, 0));
        handler.sendMessage(Message.obtain(handler, 8, 0, 0));

        pref = context.getSharedPreferences("Main", Context.MODE_PRIVATE);
        String temp = pref.getString("ship", "");

        if (temp.equals("default_ship"))
            samolet = new Samolet(context, ImageResource.SPACESHIP, false);
        else if (temp.equals("cool_ship"))
            samolet = new Samolet(context, ImageResource.SPACESHIP_COOL, false);
        else if (temp.equals("white_packman"))
            samolet = new Samolet(context, ImageResource.WHITE_PACKMAN, true);

        base = new Base();
        start_options(number);
        points = 0;
    }

    public static void add_points(int p){
        points += p;
    }

    public static void points_handler(){
        handler.sendMessage(Message.obtain(handler, 7, points, 0));
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
        if (System.currentTimeMillis() - time >= Params.time_meteor*Params.get_start_time()){
            enemys = (int)(Math.random()*14);
            enemy_list.add(new Meteor((height/32 + enemys*(2 * height/32)),0, context, (int)(Math.random()*4 + 1)));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_alien(){
        if (System.currentTimeMillis() - time >= Params.time_alien*Params.get_start_time()){
            enemys = (int)(Math.random()*9);
            enemy_list.add(new Alien(height/32 + enemys*(3 * height/32), 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_alien_two(){
        if (System.currentTimeMillis() - time >= Params.time_alien_two*Params.get_start_time()){
            enemys = (int)(Math.random()*9);
            enemy_list.add(new Alien_two(height/32 + enemys*(3 * height/32), 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_packman(){
        if (System.currentTimeMillis() - time >= Params.time_packman*Params.get_start_time()){
            enemys = (int)(Math.random()*14);
            enemy_list.add(new Packman(height/32 +enemys*(2 * height/32),0, context, enemy_list));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_bird(){
        if (System.currentTimeMillis() - time >= Params.time_bird*Params.get_start_time()){
            enemys = (int)(Math.random()*15);
            enemy_list.add(new Bird(height/32 + enemys*(2*height /32), 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_sun(){
        if (System.currentTimeMillis() - time >= Params.time_sun*Params.get_start_time()){
            enemys = (int)(Math.random()*9);
            enemy_list.add(new Sun(height/32 + enemys*(3*height /32), 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_megasun(){
        if (System.currentTimeMillis() - time >= Params.time_megasun*Params.get_start_time()){
            enemy_list.add(new Megasun(height/32, 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_cat(){
        if (System.currentTimeMillis() - time >= Params.time_cat*Params.get_start_time()){
            enemys = (int)(Math.random()*6);
            enemy_list.add(new Cat(height/32 + enemys*(4*height /32), 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_yellow(){
        if (System.currentTimeMillis() - time >= Params.time_yellow*Params.get_start_time()){
            enemys = (int)(Math.random()*6);
            enemy_list.add(new Yellow(height/32 + enemys*(2*height /32), 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_big_meteor(){
        if (System.currentTimeMillis() - time >= Params.time_big_meteor*Params.get_start_time()){
            Boss.create_big_meteor(enemy_list, context);
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_birds(){
        if (System.currentTimeMillis() - time >= Params.time_birds*Params.get_start_time()){
            Boss.create_birds(enemy_list, context);
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_packmans(){
        if (System.currentTimeMillis() - time >= Params.time_packmans*Params.get_start_time()){
            Boss.create_packmans(enemy_list, context);
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_bird_and_sun(){
        if (System.currentTimeMillis() - time >= Params.time_birdsuns*Params.get_start_time()){
            Boss.create_bird_and_sun(enemy_list, context);
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_turrets(){
        if (System.currentTimeMillis() - time >= Params.time_turrets*Params.get_start_time()){
            Boss.create_turrets(enemy_list, context);
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_heart(){
        if (System.currentTimeMillis() - time >= Params.time_heart*Params.get_start_time()){
            enemys = (int)(Math.random()*14);
            enemy_list.add(new Heart(height/32 + enemys*(2 * height/32), 0, context));
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean create_boss(){
        enemy_list.add(new Boss(context, this, enemy_list, bullet_list, handler));
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
                return create_boss();
            case 12:
                return create_birds();
            case 13:
                return create_big_meteor();
            case 14:
                return create_packmans();
            case 15:
                return create_turrets();
            case 16:
                return create_bird_and_sun();
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
                    handler.sendMessage(Message.obtain(handler, 1, 0, samolet.get_hp()));
                else
                    handler.sendMessage(Message.obtain(handler, 0, 0, points));

                //TODO: arg2 - кол-во волн если бесконечный режим и кол-во очков если уровень
                 /*
                 what:
                    0 - диалоговое окно
                        arg1:
                            0 - проиграл
                            1 - выиграл
                        arg2: -
                    1 - меняем хп
                        arg1:
                            0 - самолета
                            1 - базы
                        arg2:
                            кол-во хп, которое надо установить
                    2 - устанавливаемм стартовые настройки
                  */
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
                    handler.sendMessage(Message.obtain(handler, 1, 1, base.get_hp()));
                else
                    handler.sendMessage(Message.obtain(handler,0,0, points));
            }

            else { // столкновение с пулей
                for (int j = 0; j < bullet_list.size(); j++){
                    Bullet bullet = bullet_list.get(j);
                    if (enemy.get_alive() && Sprite.check_two(bullet, enemy, new double[]{(width/100), (height/150), -(width/100),
                            -(height/150), (width/100), 0, -(width/100), 0})){
                        enemy.collision(bullet, enemy_list);
                        bullet.collision(enemy, bullet_list);
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
                Enemy enemy = enemy_list.get(i);
                if (enemy.getTime_death() == 0 || System.currentTimeMillis() - enemy.getTime_death() <= 2000) {
                    enemy.draw(canvas, null);
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
            if (System.currentTimeMillis() - last_frame > 11) {
              //  try {
                    last_frame = System.currentTimeMillis();
                    level();
                    update_samolet(); //обновляет координаты самолета
                    update_bullets(); //обновляет координаты пуль
                    update_enemy(); //отрисовывает всех врагов
                    draw_all();
              //  } catch (Exception e){
              ////      handler.sendMessage(Message.obtain(handler,0,100, 0));
                }
        }
    }

    private int count = 0; // переменные для уровней
    private int current_enemy = -1;
    private long last_update_time, last_ability_time;

    public void set_last_udpate_time(long a){
        last_update_time = a;
        last_ability_time = a;
    }

    public void level(){
                if (current_enemy == -1){
                    current_enemy = (int)(Math.random()*count);
                } else if (mobs.size() > 0 && create_enemy(mobs.get(current_enemy))){
                    mobs.remove(current_enemy);
                    current_enemy = -1;
                    count--;
                } else if (count == 0 && enemy_list.size() == 0){
                    if (!(number == 99))
                        handler.sendMessage(Message.obtain(handler,0,1, 0));
                    else {
                        start_options(99);
                        Params.change_difficult();
                    }
                }

                if (System.currentTimeMillis() - last_update_time > 15_000){
                    last_update_time = System.currentTimeMillis();
                    handler.sendMessage(Message.obtain(handler, 3, (int) (System.currentTimeMillis()-last_ability_time), 0));
                }
    }

    List<Byte> mobs= new ArrayList<>();
    /*
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
                return create_boss();
            case 12:
                return create_birds();
            case 13:
                return create_big_meteor();
            case 14:
                return create_packmans();
            case 15:
                return create_turrets();
            case 16:
                return create_bird_and_sun();
     */

    private boolean first = true;
    public void start_options(int number) { //стартовые установки для разных уровней
        current_enemy = -1;
        switch (number) {
            case 1: // 50
                //mobs = create_level(3,0,0,0,0,0,0,0,0,0);
                mobs = create_level(50,0,0,0,0,0,0,0,0,2);
                break;
            case 2: // 70
                //mobs = create_level(3,0,0,0,0,0,0,0,0,0);
                mobs = create_level(40,30,0,0,0,0,0,0,0,3);
                break;
            case 3: // 100
                mobs = create_level(50,30,20,0,0,0,0,0,0,4);
                break;
            case 4: // 130
                mobs = create_level(60,35,20,15,0,0,0,0,0,5);
                break;
            case 5: // 150
                mobs = create_level(55,35,25,15,20,0,0,0,0,6);
                break;
            case 6: // 180
                mobs = create_level(65,35,25,20,25,10,0,0,0,7);
                break;
            case 7: // 200
                mobs = create_level(65,30,20,25,40,15,5,0,0,8);
                break;
            case 8: // 220
                mobs = create_level(70,35,30,25,30,18,7,5,0,9);
                break;
            case 9: // 250
                mobs = create_level(80,45,30,20,30,20,10,10,5,10);
                break;
            case 10:
                mobs.add((byte)(11));
                break;
            case 99:
                mobs = create_level(80,45,30,20,30,20,10,10,5,10);
                mobs.add((byte)(12));
                mobs.add((byte)(13));
                mobs.add((byte)(14));
                mobs.add((byte)(15));
                mobs.add((byte)(16));
                break;
        }
        count = mobs.size();

        if(first) {
            last_update_time = last_ability_time = System.currentTimeMillis();
            first = false;
        }
    }
}

