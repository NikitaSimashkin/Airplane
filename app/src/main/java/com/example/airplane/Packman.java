package com.example.airplane;

import android.content.Context;

public class Packman extends Enemy{
    private long meteor_time = 5000, last_time;
    private DrawThread drawThread;

    public Packman(int line_v, int line_h, Context context, DrawThread drawThread) {
        super(Params.PACKMAN, line_v, width + line_h, 5*height/32 + line_v,
                width*13/12 + line_h, context, Params.packman_damage, Params.packman_speed, Params.packman_hp, 3);
        last_time = System.currentTimeMillis();
        this.drawThread = drawThread;
    }

    @Override
    public void setDeath() {
        super.setDeath();
        bitmap = Params.PACKMAN_DEATH;
    }

    @Override
    public void update_koord() {
        if (left > width/2)
            super.update_koord();
        if (System.currentTimeMillis() - last_time > meteor_time && alive) {
            int center = (up + down)/2;
            int center_2 = (left + right)/2;
            drawThread.enemy_list.add(new Meteor((center-2 * height/32), -(center_2), context, (int) (Math.random() * 4 + 1)));
            last_time = System.currentTimeMillis();
        }
    }
}
