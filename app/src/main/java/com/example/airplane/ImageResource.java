package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

public enum ImageResource {
    METEOR_RED(R.drawable.meteor_red),
    METEOR_GREEN(R.drawable.meteor_green),
    METEOR_BLUE(R.drawable.meteor_blue),
    METEOR_YELLOW(R.drawable.meteor_yellow),
    METEOR_DIED(R.drawable.meteor_death),

    SPACESHIP(R.drawable.spaceship),
    BULLET(R.drawable.bullet_one),

    ALIEN(R.drawable.alien),
    ALIEN_DEATH(R.drawable.alien_death),

    ALIEN_TWO(R.drawable.alien_two),
    ALIEN_TWO_WITHOUT_CAP(R.drawable.angry_alien_without_cap);

    private final int resourceId;
    private ImageResource(int resourceId){
        this.resourceId = resourceId;
    }
    public Bitmap getBitmap(Context context){
        //return BitmapFactory.decodeResource(context.getResources(), resourceId);
        return drawableToBitmap(ResourcesCompat.getDrawable(context.getResources(),resourceId, null));
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

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
    }
}


