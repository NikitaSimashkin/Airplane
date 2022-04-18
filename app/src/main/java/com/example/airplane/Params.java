package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;

public class Params {
    public static int samolet_speed = 90, samolet_hp = 1000;
    public static int base_hp = 2000;
    public static int[] bullet_stats = new int[]{500, 450, 500, 200, 250, 200, 80, 150, 80};
    public static int megabullet_damage = 700;
    public static int megabullet_enemy_damage = 999, megabullet_enemy_hp = 1000000000, megabullet_enemy_speed = 400;
    // дамаг - скорость - хп

    public static int meteor_damage = 200, meteor_speed = 1000, meteor_hp = 200;
    public static int big_meteor_damage = 10000, big_meteor_speed = 1500, big_meteor_hp = 2000;
    public static int alien_damage = 300, alien_speed = 800, alien_hp = 300;
    public static int alien_two_damage = 400, alien_two_speed = 700, alien_two_hp = 100, alien_two_hp_two = 300;
    public static int packman_damage = 0, packman_speed = 700, packman_hp = 300;
    public static int bird_damage = 200, bird_speed = 400, bird_hp = 80;
    public static int sun_damage = 600, sun_speed = 600, sun_hp = 600;
    public static int megasun_damage = 10000, megasun_speed = 1200, megasun_hp = 1200;
    public static int cat_damage = 800, cat_speed = 1000, cat_hp = 1, cat_speed_two = 500, cat_hp_two = 800;
    public static int yellow_damage = 10000, yellow_speed = 1200, yellow_hp = 1200,
        yellow_damage2 = 300, yellow_speed2 = 800, yellow_hp2 = 300;
    public static int heart_damage = -400, heart_speed = 800, heart_hp = 1;
    public static int boss_damage = -100, boss_speed = 100, boss_hp = 1000000000;
    public static int time_bullet_small = 250, time_bullet_normal = 500, time_bullet_big = 1000, time_meteor = 1000,
            time_alien = 1000, time_alien_two = 1000, time_packman = 1500, time_sun = 2000, time_bird = 1000,
            time_megasun = 3000, time_cat = 3000, time_yellow = 3000, time_heart = 1000;
    public static double turret_multiplier_bullet = 1.5, many_bullets_multiplier = 0.5;
    public static int number_bullet_enemy_turret = 15;
    public static double start_hp_multiplier = 1.5, start_speed_multiplier = 1, start_damage_multiplier = 1, start_time_multiplier = 1.5;
    public static double start_speedbullet_m = 1, start_damagebullet_m = 1, start_timebullet_m = 1;

    public static int time_turret = 45_000, time_megabullet = 60_000, time_manybullets = 30_000; // не замыть поменят в string.xml

    //public static Bitmap ALIEN, ALIEN_DEATH, ALIEN_TWO,
    //ALIEN_TWO_WITHOUT_CAP, PACKMAN, PACKMAN_DEATH, BIRD, BIRD_DEATH, SUN, SUN_DEATH, YELLOW,
    //YELLOW_DEATH, CAT_ONE, CAT_TWO, CAT_DEATH, HEART, BREAKING_HEART, MEGASUN;

    public static Bitmap[] Meteors = new Bitmap[5];
    public static Bitmap[] Bullets = new Bitmap[4];

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
//        SPACESHIP = ImageResource.SPACESHIP.getBitmap(context);
//
//        ALIEN = ImageResource.ALIEN.getBitmap(context);
//        ALIEN_DEATH = ImageResource.ALIEN_DEATH.getBitmap(context);
//        ALIEN_TWO = ImageResource.ALIEN_TWO.getBitmap(context);
//        ALIEN_TWO_WITHOUT_CAP = ImageResource.ALIEN_TWO_WITHOUT_CAP.getBitmap(context);
//
//        PACKMAN = ImageResource.PACKMAN.getBitmap(context);
//        PACKMAN_DEATH = ImageResource.PACKMAN_DEATH.getBitmap(context);
//
//        BIRD = ImageResource.BIRD.getBitmap(context);
//        BIRD_DEATH = ImageResource.BIRD_DEATH.getBitmap(context);
//
//        SUN = ImageResource.SUN.getBitmap(context);
//        SUN_DEATH = ImageResource.SUN_DEATH.getBitmap(context);
//        MEGASUN = ImageResource.MEGASUN.getBitmap(context);
//
//        YELLOW = ImageResource.YELLOW.getBitmap(context);
//        YELLOW_DEATH = ImageResource.YELLOW_DEATH.getBitmap(context);
//
//        CAT_ONE = ImageResource.CAT_ONE.getBitmap(context);
//        CAT_TWO = ImageResource.CAT_TWO.getBitmap(context);
//        CAT_DEATH = ImageResource.CAT_DEATH.getBitmap(context);
//
//        HEART = ImageResource.HEART.getBitmap(context);
//        BREAKING_HEART = ImageResource.BREAKING_HEART.getBitmap(context);
    }
}
