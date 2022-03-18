package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public enum ImageResource {
    METEOR(R.drawable.meteor),
    //METEOR_DIED(R.drawable.meteor_death),
    SPACESHIP(R.drawable.spaceship),
    BULLET(R.drawable.bullet_1);

    private final int resourceId;
    private ImageResource(int resourceId){
        this.resourceId = resourceId;
    }
    public Bitmap getBitmap(Context context){
        return BitmapFactory.decodeResource(context.getResources(), resourceId);
    }
}


