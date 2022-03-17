package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Meteor extends Enemy {
    public Meteor(int up, int left, int down, int right, int height, int width, Context context, int damage) {
        super(ImageResource.METEOR, up, left, down, right, height, width, context, damage);
        }
}
