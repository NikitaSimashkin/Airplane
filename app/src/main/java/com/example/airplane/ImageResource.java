package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

public enum ImageResource {
    METEOR_RED(R.drawable.meteor_red),
    METEOR_GREEN(R.drawable.meteor_green),
    METEOR_BLUE(R.drawable.meteor_blue),
    METEOR_YELLOW(R.drawable.meteor_yellow),
    METEOR_DIED(R.drawable.meteor_death),
    METEOR_BIG(R.drawable.meteor_big),

    SPACESHIP(R.drawable.spaceship),
    SPACESHIP_COOL(R.drawable.spaceship_cool),

    BULLET_GREEN(R.drawable.bullet_green),
    BULLET_RED(R.drawable.bullet_red),
    BULLET_YELLOW(R.drawable.bullet_yellow),
    BULLET_BLUE(R.drawable.bullet_blue),

    ROMB_GREEN(R.drawable.romb_green),
    ROMB_RED(R.drawable.romb_red),
    ROMB_YELLOW(R.drawable.romb_yellow),
    ROMB_BLUE(R.drawable.romb_blue),

    RED_BASE(R.drawable.base),
    BLUE_BASE(R.drawable.blue_base),
    GOLD_BASE(R.drawable.gold_base),

    TURRET(R.drawable.turret),
    ENEMY_TURRET(R.drawable.enemy_turret),
    MEGABULLET(R.drawable.megabullet),
    ENEMY_MEGABULLET(R.drawable.enemy_megabullet),
    WHITE_PACKMAN(R.drawable.white_packman),
    ALIEN_GREEN(R.drawable.alien_green),
    ALIEN_RED(R.drawable.alien_red),
    ALIEN_YELLOW(R.drawable.alien_yellow),
    ALIEN_BLUE(R.drawable.alien_blue),

    ALIEN_DEATH(R.drawable.alien_death),

    ALIEN_TWO_GREEN(R.drawable.alien_two_green),
    ALIEN_TWO_WITHOUT_CAP_GREEN(R.drawable.angry_alien_without_cap_green),
    ALIEN_TWO_RED(R.drawable.alien_two_red),
    ALIEN_TWO_WITHOUT_CAP_RED(R.drawable.angry_alien_without_cap_red),
    ALIEN_TWO_YELLOW(R.drawable.alien_two_yellow),
    ALIEN_TWO_WITHOUT_CAP_YELLOW(R.drawable.angry_alien_without_cap_yellow),
    ALIEN_TWO_BLUE(R.drawable.alien_two_blue),
    ALIEN_TWO_WITHOUT_CAP_BLUE(R.drawable.angry_alien_without_cap_blue),

    PACKMAN_YELLOW(R.drawable.packman_yellow),
    PACKMAN_RED(R.drawable.packman_red),
    PACKMAN_BLUE(R.drawable.packman_blue),
    PACKMAN_GREEN(R.drawable.packman_green),
    PACKMAN_DEATH(R.drawable.death_packman),

    BIRD_BLUE(R.drawable.bird_blue),
    BIRD_RED(R.drawable.bird_red),
    BIRD_GREEN(R.drawable.bird_green),
    BIRD_YELLOW(R.drawable.bird_yellow),
    BIRD_DEATH(R.drawable.bird_death),

    SUN_RED(R.drawable.sun_red),
    SUN_BLUE(R.drawable.sun_blue),
    SUN_YELLOW(R.drawable.sun_yellow),
    SUN_GREEN(R.drawable.sun_green),
    SUN_DEATH(R.drawable.sun_death),

    YELLOW_TWO_YELLOW(R.drawable.yellow_two_yellow),
    YELLOW_TWO_TWO_YELLOW(R.drawable.yellow_two_two_yellow),
    YELLOW_TWO_RED(R.drawable.yellow_two_red),
    YELLOW_TWO_TWO_RED(R.drawable.yellow_two_two_red),
    YELLOW_TWO_GREEN(R.drawable.yellow_two_green),
    YELLOW_TWO_TWO_GREEN(R.drawable.yellow_two_two_green),
    YELLOW_TWO_BLUE(R.drawable.yellow_two_blue),
    YELLOW_TWO_TWO_BLUE(R.drawable.yellow_two_two_blue),

    CAT_ONE_BLUE(R.drawable.cat_one_blue),
    CAT_TWO_BLUE(R.drawable.cat_two_blue),
    CAT_ONE_RED(R.drawable.cat_one_red),
    CAT_TWO_RED(R.drawable.cat_two_red),
    CAT_ONE_GREEN(R.drawable.cat_one_green),
    CAT_TWO_GREEN(R.drawable.cat_two_green),
    CAT_ONE_YELLOW(R.drawable.cat_one_yellow),
    CAT_TWO_YELLOW(R.drawable.cat_two_yellow),
    CAT_DEATH(R.drawable.cat_death),

    HEART(R.drawable.heart),
    BREAKING_HEART(R.drawable.breaking_heart),

    BOSS(R.drawable.boss),
    MEGASUN_BLUE(R.drawable.megasun_blue),
    MEGASUN_RED(R.drawable.megasun_red),
    MEGASUN_GREEN(R.drawable.megasun_green),
    MEGASUN_YELLOW(R.drawable.megasun_yellow),

    ELAIN_1(R.drawable.helper101),
    ELAIN_2(R.drawable.helper102),
    ELAIN_3(R.drawable.helper103),
    ELAIN_4(R.drawable.helper104),
    ELAIN_5(R.drawable.helper105),
    ELAIN_6(R.drawable.helper106),
    ELAIN_7(R.drawable.helper107),
    ELAIN_DEATH(R.drawable.helper_death);

    private final int resourceId;
    private ImageResource(int resourceId){
        this.resourceId = resourceId;
    }
    public Bitmap getBitmap(@NonNull Context context){
        //return BitmapFactory.decodeResource(context.getResources(), resourceId);
        return drawableToBitmap(ResourcesCompat.getDrawable(context.getResources(),resourceId, null));
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;
       // try{
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                if(bitmapDrawable.getBitmap() != null) {
                    return bitmapDrawable.getBitmap();
                }
            }
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
       // }catch (OutOfMemoryError err){
       //     System.out.println(123);
       // }
       // return bitmap;
    }
}


