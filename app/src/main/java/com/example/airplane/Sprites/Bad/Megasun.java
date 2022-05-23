package com.example.airplane.Sprites.Bad;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.airplane.DrawThread;
import com.example.airplane.ImageResource;
import com.example.airplane.MusicResorces;
import com.example.airplane.Params;
import com.example.airplane.Sprites.Good.Bullet;

import java.util.List;

public class Megasun extends Enemy {
    public Megasun(double line_v, double line_h, Context context) {
        super(ImageResource.MEGASUN, line_v, width + line_h,
                30*height/32 + line_v, width*11/10 + line_h, context,
                Params.megasun_damage, Params.megasun_speed, Params.megasun_hp, 2);
        points = Params.p_megasun;
    }

    @Override
    public void collision(@NonNull Bullet two, List<Enemy> enemy_list) {
        MusicResorces.play(MusicResorces.enemy_damage);
        super.collision(two, enemy_list);
        if (!alive){
            enemy_list.add(new Sun(height/32, (-width+left), context));
            enemy_list.add(new Sun((7*height/32), (-width+left), context));
            enemy_list.add(new Sun((13*height/32), (-width+left), context));
            enemy_list.add(new Sun((19*height/32), (-width+left), context));
            enemy_list.add(new Sun((25*height/32), (-width+left), context));
            enemy_list.remove(this);
            DrawThread.add_points(points);
            DrawThread.points_handler();
        }
    }
}
