package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Meteor extends Enemy{
    public Meteor(int up, int left, int down, int right, int height, int width, Context context) {
        super(BitmapFactory.decodeResource(context.getResources(), R.drawable.meteor), up, left, down, right, height, width, context);
    }
}
