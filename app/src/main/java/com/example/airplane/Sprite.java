package com.example.airplane;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Sprite {
    private Bitmap bitmap; // картинка
    private int up, down, left, right; //координаты квадрата где сейчас картинка()

    public void set_koord(int up, int down, int left, int right){ //задаем координаты
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public Rect create_rect(){ //формируем область куда будем вставлять картинку
        return new Rect(left, up, right, down);
    }


    public Sprite(Bitmap bitmap, int up, int left, int down, int right){
        this.bitmap = bitmap;
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public Bitmap get_picture (){
        return bitmap;
    }

    public Canvas draw (Canvas canvas, Paint paint){
        canvas.drawBitmap(bitmap, null, create_rect(), paint);
        return canvas;
    }

}
