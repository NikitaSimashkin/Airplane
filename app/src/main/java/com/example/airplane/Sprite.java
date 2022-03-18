package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Sprite {
    /**
     * bitmap должен меняться только изнутри класса
     * setDeath(!)
     */
    protected Bitmap bitmap;
    protected final ImageResource imageResource; // картинка
    protected int up, down, left, right; //координаты квадрата где сейчас картинка()
    protected int height, width;
    private Context context;

    public void set_koord(int up, int down, int left, int right){ //меняем координаты
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public Rect create_rect(){ //формируем область куда будем вставлять картинку
        return new Rect(left, up, right, down);
    }

    public Rect create_rect_minus(int a, int b, int c, int d){
        return new Rect(left+a, up+b, right+c, down+d);
    }


    public Sprite(ImageResource imageResource, int up, int left, int down, int right, Context context){ //создаем спрайт и задаем нач. координаты
        this.imageResource = imageResource;
        this.bitmap = imageResource.getBitmap(context);
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.context = context;
    }

    public int[] get_koord(){
        return new int[]{up, left, down, right};
    }

    public Canvas draw (Canvas canvas, Paint paint){ //рисуем картинку на холсте
        canvas.drawBitmap(bitmap, null, create_rect(), paint);
        return canvas;
    }

    public static boolean check (Sprite first, Sprite second){
        return first.create_rect().intersect(second.create_rect());
    }

    public static boolean check_two (Sprite first, Sprite second, int[] a){
        return first.create_rect_minus(a[0], a[1], a[2], a[3]).intersect(second.create_rect_minus(a[4], a[5], a[6], a[7]));
    }
}
