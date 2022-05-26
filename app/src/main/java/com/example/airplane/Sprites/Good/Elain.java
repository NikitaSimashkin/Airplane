package com.example.airplane.Sprites.Good;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.ContactsContract;

import com.example.airplane.ImageResource;
import com.example.airplane.Params;
import com.example.airplane.Sprites.Bad.Boss;
import com.example.airplane.Sprites.Bad.Bullet_enemy;
import com.example.airplane.Sprites.Bad.Enemy;

import java.util.List;

public class Elain extends Bullet{

    private int step = 0;
    private Bitmap[] animation = new Bitmap[6];
    private long time, time_change = 500, time_death;
    private List<Bullet> bullet_list;
    private static boolean alive = true;

    public Elain(Context context) {
        super(height/2 - 4*height/32, 0, height/2 + 4*height/32, width/10, context);
        animation[0] = ImageResource.ELAIN_2.getBitmap(context);
        animation[1] = ImageResource.ELAIN_3.getBitmap(context);
        animation[2] = ImageResource.ELAIN_4.getBitmap(context);
        animation[3] = ImageResource.ELAIN_5.getBitmap(context);
        animation[4] = ImageResource.ELAIN_6.getBitmap(context);
        animation[5] = ImageResource.ELAIN_7.getBitmap(context);
        speed = Params.bullet_stats[1];
        Elain.alive = true;
    }

    @Override
    public void update_koord() {
        if (left < width/3) {
            super.update_koord();
            time = System.currentTimeMillis();
        }
        else if (step < 6 && System.currentTimeMillis() - time > time_change){
            bitmap = animation[step];
            step++;
            time = System.currentTimeMillis();
            if (step == 6)
                Boss.set_elain_in_center();
        }
        else if (time_death != 0 && System.currentTimeMillis() - time_death > 2000){
            bullet_list.remove(this);
            Elain.alive = false;
        }
    }

    public void addTime(long time){
        if (this.time != 0)
            this.time += time;
    }

    public void addTime_death(long time){
        if (this.time_death != 0)
            this.time_death += time;
    }

    @Override
    public void collision(Enemy enemy, List<Bullet> bullet_list) {
        bitmap = ImageResource.ELAIN_DEATH.getBitmap(context);
        time_death = System.currentTimeMillis();
        this.bullet_list = bullet_list;
    }

    public static boolean get_alive(){
        return Elain.alive;
    }
}
