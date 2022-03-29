package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Meteor extends Enemy {
    public Meteor(int up, int left, int down, int right, Context context) {
        super(ImageResource.METEOR, up, left, down, right, context, 100, 1000, 100);
        }
}
