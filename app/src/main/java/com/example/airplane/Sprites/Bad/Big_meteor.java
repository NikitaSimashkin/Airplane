package com.example.airplane.Sprites.Bad;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.airplane.ImageResource;
import com.example.airplane.Params;
import com.example.airplane.Sprites.Good.Bullet;
import com.example.airplane.Sprites.Good.MegaBullet;
import com.example.airplane.Sprites.Sprite;

import java.util.List;

public class Big_meteor extends Enemy{

    private long time_created, time_packman = 5000;
    private List<Enemy> enemyList;
    private boolean is_created = false;

    public Big_meteor(Context context, List<Enemy> enemyList) {
        super(ImageResource.METEOR_BIG, Sprite.height/32, Sprite.width, 31* Sprite.height/32, Sprite.width + 31* Sprite.height/32, context,
                Params.big_meteor_damage, Params.big_meteor_speed, Params.big_meteor_hp, 5);
        this.enemyList = enemyList;
        time_created = System.currentTimeMillis();
    }

    @Override
    public void update_koord() {
        super.update_koord();
        if (System.currentTimeMillis() - time_created > time_packman && !is_created){
            enemyList.add(new Packman(6* Sprite.height/32, 0, context, enemyList));
            enemyList.add(new Packman(21* Sprite.height/32, 0, context, enemyList));
            is_created = true;
        }
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = Params.Meteors[4];
        Boss.set_meteor_flag(false);
    }

    @Override
    public void collision(@NonNull Bullet two, List<Enemy> enemy_list) {
        if (!(two instanceof MegaBullet))
            super.collision(two, enemy_list);
    }
}
