package com.example.airplane.Sprites;

import android.content.Context;

import com.example.airplane.ImageResource;
import com.example.airplane.Params;

import java.util.List;

public class MegaBullet extends Bullet{
    public MegaBullet(double[] koord_samolet, Context context) {
        super(koord_samolet, context, 5, 0);
        bitmap = ImageResource.MEGABULLET.getBitmap(context);
        damage = Params.megabullet_damage;
        speed = Params.bullet_stats[1];
    }

    @Override
    public void collision(List<Bullet> bullet_list) {
    }
}
