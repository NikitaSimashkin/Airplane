package com.example.airplane;

import android.content.Context;

public class Alien extends Enemy{
    public Alien(ImageResource imageResource, int up, int left, int down, int right, Context context) {
        super(ImageResource.ALIEN, up, left, down, right, context, 200, 800, 300);
    }
}
