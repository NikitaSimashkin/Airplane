package com.example.airplane.Sprites.Bad;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.airplane.ImageResource;
import com.example.airplane.Params;
import com.example.airplane.Sprites.Good.Bullet;
import com.example.airplane.Sprites.Sprite;

import java.util.List;

public class Bullet_enemy extends Enemy{
    public Bullet_enemy(Bullet bullet, Context context) {
        super(Params.Bullets[bullet.getColor()-1], bullet.getUp(), bullet.getLeft(), bullet.getDown(), bullet.getRight(), context,
                Params.bullet_stats[bullet.get_size()*3],
                Params.bullet_stats[bullet.get_size()*3 + 1], Params.bullet_stats[bullet.get_size()*3 + 2], bullet.getColor());
    }

    public Bullet_enemy(Context context, int a) { // для босса
        super(ImageResource.ENEMY_MEGABULLET, a* Sprite.height/32, Sprite.width, (a+4)* Sprite.height/32, Sprite.width + 4* Sprite.height/32, context,
                Params.megabullet_enemy_damage,
                Params.megabullet_enemy_speed, Params.megabullet_enemy_hp, 1);
    }

    public Bullet_enemy(double[] koord_samolet, Context context, int size, int color) { // для турелей
        super(Params.Bullets[color-1], (koord_samolet[0] + koord_samolet[2]) / 2 - (koord_samolet[2] - koord_samolet[0]) / Math.pow(2, size+1),
                koord_samolet[1] - 2*(koord_samolet[2] - koord_samolet[0]) / Math.pow(2, size+1) + (koord_samolet[3]-koord_samolet[1])/5,
                (koord_samolet[0] + koord_samolet[2]) / 2 + (koord_samolet[2] - koord_samolet[0]) / Math.pow(2, size+1),
                koord_samolet[1]+(koord_samolet[3]-koord_samolet[1])/5, context, Params.bullet_stats[size*3],
                Params.bullet_stats[size*3 + 1]*5, Params.bullet_stats[size*3 + 2], color);
    }


    // так как у пули нет картинки смерти, то необходимо переопределить метод столкновения
    @Override
    public void collision(@NonNull Bullet two, List<Enemy> enemy_list) {
        if (hp < 10_000)
            super.collision(two, enemy_list);
        if (!alive)
            enemy_list.remove(this);
    }
}
