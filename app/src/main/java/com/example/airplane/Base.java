package com.example.airplane;

import android.content.Context;

// TODO: не забыть реализовать этот класс
public class Base{
    private int hp = 1000;

    public Base() {
    }

    public void change_hp(int a){
        hp+=a;
    }

    public int get_hp(){
        return hp;
    }
}
