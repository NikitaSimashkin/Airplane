package com.example.airplane.Sprites;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.airplane.DrawThread;
import com.example.airplane.ImageResource;

public class Sprite {

    protected Bitmap bitmap; //картинка
    protected double up, down, left, right; //координаты квадрата где сейчас картинка()
    protected static final double height = DrawThread.get_height(), width = DrawThread.get_width();
    protected long last_changed = System.currentTimeMillis();

    protected Context context;

    public void set_koord(double up, double down, double left, double right){ //меняем координаты
            this.up = up;
            this.down = down;
            this.left = left;
            this.right = right;
            last_changed = System.currentTimeMillis();
    }

    public void set_koord(double left, double right){ //меняем координаты
        this.left = left;
        this.right = right;
        last_changed = System.currentTimeMillis();
    }

    public Rect create_rect(){ //формируем область куда будем вставлять картинку
        return new Rect((int)left, (int)up, (int)right, (int)down);
    }

    public Rect create_rect_minus(double a, double b, double c, double d){ //создает измененный прямоугольник
        return new Rect((int)(left+a), (int)(up+b), (int)(right+c), (int)(down+d));
    }

    public double getUp() {
        return up;
    }

    public double getDown() {
        return down;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public Sprite(Bitmap bitmap, double up, double left, double down, double right, Context context){ //создаем спрайт и задаем нач. координаты
        this.bitmap = bitmap;
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.context = context;
    }

    public Sprite(ImageResource imageResource, double up, double left, double down, double right, Context context){ //создаем спрайт и задаем нач. координаты
        this.bitmap = imageResource.getBitmap(context);
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.context = context;
    }

    public double[] get_koord(){
        return new double[]{up, left, down, right};
    }

    public void draw (Canvas canvas, Paint paint){ //рисуем картинку на холсте
        canvas.drawBitmap(bitmap, null, create_rect(), paint);
    }

    public static boolean check (Sprite first, Sprite second){
        return first.create_rect().intersect(second.create_rect());
    }

    public static boolean check_two (Sprite first, Sprite second, double[] a){
        //return check(first, second);
        return first.create_rect_minus(a[0], a[1], a[2], a[3]).intersect(second.create_rect_minus(a[4], a[5], a[6], a[7]));
    }
}
