package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;

public class Params {
    public static int samolet_speed = 60, samolet_hp = 500;
    public static int base_hp = 1000;
    public static int turret_damage = 200, turret_hp = 201;
    public static int[] bullet_stats = new int[]{300, 600, 300, 100, 200, 100, 50, 100, 50};
    public static int megabullet_damage = 10000;
    // дамаг - скорость - хп

    public static int meteor_damage = 100, meteor_speed = 1000, meteor_hp = 100;
    public static int alien_damage = 150, alien_speed = 10000, alien_hp = 200;
    public static int alien_two_damage = 200, alien_two_speed = 810, alien_two_hp = 150, alien_two_hp_two = 200;
    public static int packman_damage = 50, packman_speed = 800, packman_hp = 100;
    public static int bird_damage = 100, bird_speed = 700, bird_hp = 100;
    public static int sun_damage = 100, sun_speed = 1500, sun_hp = 100;
    public static int megasun_damage = 100, megasun_speed = 700, megasun_hp = 100;
    public static int cat_damage = 100, cat_speed = 700, cat_hp = 100, cat_speed_two = 400, cat_hp_two = 100;
   // public static int yellow_damage = 100, yellow_speed = 700, yellow_hp = 100;
    public static int yellow_damage = 100, yellow_hp = 200, yellow_hp_two = 100, yellow_speed = 700, yellow_damage_two = 100;
    public static int heart_damage = -100, heart_speed = 1500, heart_hp = 100;

    //public static Bitmap ALIEN, ALIEN_DEATH, ALIEN_TWO,
    //ALIEN_TWO_WITHOUT_CAP, PACKMAN, PACKMAN_DEATH, BIRD, BIRD_DEATH, SUN, SUN_DEATH, YELLOW,
    //YELLOW_DEATH, CAT_ONE, CAT_TWO, CAT_DEATH, HEART, BREAKING_HEART, MEGASUN;

    public static Bitmap[] Meteors = new Bitmap[5];
    public static Bitmap[] Bullets = new Bitmap[4];

    public Params(Context context){
        Meteors[1] = ImageResource.METEOR_RED.getBitmap(context);
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
