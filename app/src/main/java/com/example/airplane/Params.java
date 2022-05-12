package com.example.airplane;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

public class Params {
    public final static int samolet_speed = 90, samolet_hp = 1000;
    public final static int base_hp = 2000;
    public final static int[] bullet_stats = new int[]{500, 450, 500, 200, 250, 200, 80, 150, 80};
    public final static int megabullet_damage = 700;
    public final static int megabullet_enemy_damage = 999, megabullet_enemy_hp = 1000000000, megabullet_enemy_speed = 400;
    // дамаг - скорость - хп

    public final static int meteor_damage = 200, meteor_speed = 1000, meteor_hp = 200;
    public final static int big_meteor_damage = 10000, big_meteor_speed = 1500, big_meteor_hp = 4000;
    public final static int alien_damage = 300, alien_speed = 800, alien_hp = 300;
    public final static int alien_two_damage = 400, alien_two_speed = 700, alien_two_hp = 100, alien_two_hp_two = 300;
    public final static int packman_damage = 0, packman_speed = 700, packman_hp = 300;
    public final static int bird_damage = 200, bird_speed = 400, bird_hp = 80;
    public final static int sun_damage = 600, sun_speed = 600, sun_hp = 600;
    public final static int megasun_damage = 10000, megasun_speed = 1200, megasun_hp = 1200;
    public final static int cat_damage = 800, cat_speed = 1000, cat_hp = 1, cat_speed_two = 500, cat_hp_two = 800;
    public final static int yellow_damage = 10000, yellow_speed = 1200, yellow_hp = 1200,
        yellow_damage2 = 300, yellow_speed2 = 800, yellow_hp2 = 300;

    public final static int heart_damage = -400, heart_speed = 800, heart_hp = 1;

    public final static int boss_damage = 1, boss_speed = 500, boss_hp = 1000000000;

    public final static int time_bullet_small = 250, time_bullet_normal = 500, time_bullet_big = 1000,
            time_meteor = 1000, time_alien = 1000, time_alien_two = 1000,
            time_packman = 1500, time_sun = 2000, time_bird = 1000,
            time_megasun = 3000, time_cat = 3000, time_yellow = 3000,
            time_heart = 1000;

    public final static int time_big_meteor = 5000, time_birds = 7000, time_packmans = 8000, time_birdsuns = 10000,
            time_turrets = 10000;

    public final static double turret_multiplier_bullet = 2, many_bullets_multiplier = 0.5, turret_multiplier_bullet_enemy = 4;

    public final static int number_bullet_enemy_turret = 15;
    public final static double start_hp_multiplier_default = 1.5, start_speed_multiplier_default = 0.8,
            start_damage_multiplier_default = 1, start_time_multiplier_default = 1.5;
    public final static double start_speedbullet_m = 1, start_damagebullet_m = 1, start_timebullet_m = 1;

    public final static int time_turret = 45_000, time_megabullet = 60_000, time_manybullets = 30_000; // не забыть поменят в string.xml

    private static double start_hp_multiplier, start_damage_multiplier, start_speed_multiplier, start_time_multiplier;
    public final static Bitmap[] Meteors = new Bitmap[5];
    public final static Bitmap[] Bullets = new Bitmap[4];

    public static final int p_meteor = 10, p_alien = 14, p_alien_two = 16, p_bird = 16, p_packman = 18, p_sun = 21, p_megasun = 25,
                            p_cat = 30, p_yellow = 30, p_big_meteor = 40, p_turret = 40;

    public Params(Context context){
        Meteors[1] = ImageResource.METEOR_RED.getBitmap(context);
        //Meteors[1] = ImageResource.G.getBitmap(context);
        Meteors[0] = ImageResource.METEOR_GREEN.getBitmap(context);
        Meteors[3] = ImageResource.METEOR_BLUE.getBitmap(context);
        Meteors[2] = ImageResource.METEOR_YELLOW.getBitmap(context);
        Meteors[4] = ImageResource.METEOR_DIED.getBitmap(context);

        Bullets[0] = ImageResource.BULLET_GREEN.getBitmap(context);
        Bullets[1] = ImageResource.BULLET_RED.getBitmap(context);
        Bullets[2] = ImageResource.BULLET_YELLOW.getBitmap(context);
        Bullets[3] = ImageResource.BULLET_BLUE.getBitmap(context);

        switch (context.getSharedPreferences("Main", MODE_PRIVATE).getInt(MainActivity.diff, 1)){ // 0 - легко - 1 - нормально 2 - сложно
            case 0:
                start_hp_multiplier = 0.9 * start_hp_multiplier_default;
                start_speed_multiplier = 1.1 * start_speed_multiplier_default;
                start_damage_multiplier = 0.9 * start_damage_multiplier_default;
                start_time_multiplier = 1.1 * start_time_multiplier_default;
                break;
            case 1:
                start_hp_multiplier = 1 * start_hp_multiplier_default;
                start_speed_multiplier = 1 * start_speed_multiplier_default;
                start_damage_multiplier = 1 * start_damage_multiplier_default;
                start_time_multiplier = 1 * start_time_multiplier_default;
                break;
            case 2:
                start_hp_multiplier = 1.1 * start_hp_multiplier_default;
                start_speed_multiplier = 0.9 * start_speed_multiplier_default;
                start_damage_multiplier = 1.1 * start_damage_multiplier_default;
                start_time_multiplier = 0.9 * start_time_multiplier_default;
                break;
        }
    }

    public static double get_start_hp(){
        return start_hp_multiplier;
    }

    public static double get_start_speed(){
        return start_speed_multiplier;
    }

    public static double get_start_damage(){
        return start_damage_multiplier;
    }

    public static double get_start_time(){
        return start_time_multiplier;
    }

    public static void change_difficult(){
        start_hp_multiplier *= 1.05;
        start_speed_multiplier *= 1.05;
        start_damage_multiplier *= 1.05;
    }
}
