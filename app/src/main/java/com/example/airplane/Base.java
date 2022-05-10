package com.example.airplane;

// TODO: не забыть реализовать этот класс
public class Base{
    private int hp = Params.base_hp;

    public Base() {
    }

    public void change_hp(int a){
        hp+=a;
        if (hp > Params.base_hp)
            hp = Params.base_hp;
    }

    public int get_hp(){
        return hp;
    }
}
