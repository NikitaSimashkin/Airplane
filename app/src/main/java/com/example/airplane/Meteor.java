package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Meteor extends Enemy {
    public Meteor(int up, int left, int down, int right, Context context, int color) {
        super(ImageResource.METEOR_DIED, up, left, down, right, context,
                Params.meteor_damage, Params.meteor_speed, Params.meteor_hp, color);
        change_bitmap(color);
        }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = ImageResource.METEOR_DIED.getBitmap(context);
    }

    public void change_bitmap(int color){
        switch(color){
            case 1:
                bitmap = ImageResource.METEOR_GREEN.getBitmap(context);
                break;
            case 2:
                bitmap = ImageResource.METEOR_RED.getBitmap(context);
                break;
            case 3:
                bitmap = ImageResource.METEOR_YELLOW.getBitmap(context);
                break;
            case 4:
                bitmap = ImageResource.METEOR_BLUE.getBitmap(context);
                break;
        }
    }

}
