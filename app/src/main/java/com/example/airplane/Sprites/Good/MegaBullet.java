package com.example.airplane.Sprites.Good;

import android.content.Context;

import com.example.airplane.ImageResource;
import com.example.airplane.Params;
import com.example.airplane.Sprites.Bad.Big_meteor;
import com.example.airplane.Sprites.Bad.Bullet_enemy;
import com.example.airplane.Sprites.Bad.Enemy;
import com.example.airplane.Sprites.Bad.Enemy_turret;

import java.util.List;

public class MegaBullet extends Bullet{
    public MegaBullet(double[] koord_samolet, Context context) {
        super(koord_samolet, context, 5, 0);
        bitmap = ImageResource.MEGABULLET.getBitmap(context);
        damage = (int)(Params.megabullet_damage*Params.start_damagebullet_m);
        speed = (int)(Params.bullet_stats[1]*Params.start_speedbullet_m);
    }

    @Override
    public void collision(Enemy enemy, List<Bullet> bullet_list) {
        if ((enemy instanceof Big_meteor) || (enemy instanceof Enemy_turret))
            bullet_list.remove(this);
    }
}
