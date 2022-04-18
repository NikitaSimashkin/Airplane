package com.example.airplane.Sprites.Good;

import android.content.Context;

import com.example.airplane.ImageResource;
import com.example.airplane.Params;
import com.example.airplane.Sprites.Bad.Big_meteor;
import com.example.airplane.Sprites.Bad.Enemy;

import java.util.List;

public class MegaBullet extends Bullet{
    public MegaBullet(double[] koord_samolet, Context context) {
        super(koord_samolet, context, 5, 0);
        bitmap = ImageResource.MEGABULLET.getBitmap(context);
        damage = (int)(Params.megabullet_damage*Params.start_damagebullet_m);
        speed = (int)(Params.bullet_stats[1]*Params.start_damagebullet_m);
        color = 5;
    }

    @Override
    public void collision(Enemy enemy, List<Bullet> bullet_list) {
        if ((enemy instanceof Big_meteor))
            bullet_list.remove(this);
    }
}
