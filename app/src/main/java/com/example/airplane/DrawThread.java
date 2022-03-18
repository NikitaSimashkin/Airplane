package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import java.util.ArrayList;

public class DrawThread extends Thread{
    private SurfaceHolder surfaceHolder;
    private Context context;

    private Canvas canvas;

    private int width, height;

    private Samolet samolet;

    private Bitmap bullet_1;

    private long time = System.currentTimeMillis()/1000;

    private ArrayList<Enemy> enemy_list = new ArrayList<>();
    private ArrayList<Bullet> bullet_list = new ArrayList<>();

    private int enemys;

    private long time_bullet = System.currentTimeMillis()/1000;

    public DrawThread (SurfaceHolder surfaceHolder, Context context, int width, int height){
        super();
        this.surfaceHolder = surfaceHolder;
        this.context = context;
        this.width = width;
        this.height = height;

        bullet_1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet_1);

        samolet = new Samolet(height/50, width/50, (int)(height/5), width/7, height, context);
    }

    public Samolet get_Samolet(){
        return samolet;
    }

    public void change_xy_samolet(){
        samolet.when_button_is_pressed();
    }

    public void draw_all(){

        try {
            canvas = surfaceHolder.lockCanvas();

            Paint clearPaint = new Paint(); //очистка холста
            clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            canvas.drawRect(0, 0, width, height, clearPaint);

            for (int i = 0; i < enemy_list.size(); i++) { //отрисовываем врагов
                if (enemy_list.get(i).getTime_death() == 0 || System.currentTimeMillis()/1000 - enemy_list.get(i).getTime_death() <= 2) {
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
        }
    }

    @Override
    public void run() {

        while (!isInterrupted()){ //сначала он проводит все вычисления, а потом уже все рисует в одном методе
            change_xy_samolet(); //обновляет координаты самолета
            update_bullets();
            update_enemy(); //отрисовывает всех врагов
            draw_all(); //рисует все
        }
    }

    public void update_bullets() {
        for (int i = 0; i < bullet_list.size(); i++)
            bullet_list.get(i).update_koord(); //обновляет координаты
    }

    public void create_bullets() {
        if (System.currentTimeMillis()/1000 - time_bullet > 1){
            int[] koord_samolet = samolet.get_koord();
            bullet_list.add(new Bullet(
                    (koord_samolet[0] + koord_samolet[2])/2 - (koord_samolet[2] - koord_samolet[0])/6,
                    (koord_samolet[1] + koord_samolet[3])/2,
                    (koord_samolet[0] + koord_samolet[2])/2 + (koord_samolet[2] - koord_samolet[0])/6,
                    koord_samolet[3],
                    context, 5, 120, height, width));

            time_bullet = System.currentTimeMillis()/1000;
        }
    }

    private void update_enemy() {
        enemys = (int)(Math.random()*7);
        if (System.currentTimeMillis()/1000 - time > 1){ //каждые 5 секунд спавним врага
            enemy_list.add(new Meteor(height/30 + enemys*(4 * height/30), width,
                    5*height/30 + enemys*(4 * height/30) , width*15/14, height, width, context, 5));
            time = System.currentTimeMillis()/1000;
        }
        for (int i = 0; i < enemy_list.size(); i++){
            enemy_list.get(i).update_koord(300 * enemy_list.get(i).getAlive()); //обновляет координаты
            if (Enemy.check_two(samolet, enemy_list.get(i), new int[]{width/100, height/150, -width/100, -height/150,
                    width/100, 0, -width/100, 0}))  //проверяет столкновение с самолетом или стеной
            {
                enemy_list.remove(i);
                //samolet.change_hp();
            }
            else if(enemy_list.get(i).get_koord()[1] <= 0){
                enemy_list.remove(i);
                //base.change_hp();
            }
            else { // столкновение с пулей
                for (int j = 0; j < bullet_list.size(); j++){
                    if (enemy_list.get(i).getAlive() < 10 && Sprite.check_two(bullet_list.get(j), enemy_list.get(i), new int[]{width/100, height/150, -width/100, -height/150,
                            width/100, 0, -width/100, 0})){
                        enemy_list.get(i).setDeath(BitmapFactory.decodeResource(context.getResources(), R.drawable.meteor_death));
                        bullet_list.remove(j);
                        break;
                    }
                }
            }
        }
    }
}

