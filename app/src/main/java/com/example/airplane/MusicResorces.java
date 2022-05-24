package com.example.airplane;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.example.airplane.Activities.MainActivity;

public class MusicResorces {

    public static MediaPlayer menu_s, battle_s;

    private static boolean menu_start = false;

    private static MusicResorces musicResorces;
    public static SoundPool soundPool;
    public static int high, low, middle, button_s1, chose, player_death, player_damage, base_damage, enemy_damage;
    public static final int[] bullet_s = new int[3];

    private MusicResorces(Context context){
        menu_s = MediaPlayer.create(context, R.raw.menu);
        battle_s = MediaPlayer.create(context, R.raw.battle);
       // battle_s.setVolume(0.8f, 0.8f);

        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(attributes).build();

        button_s1 = soundPool.load(context, R.raw.button1, 1);
        chose = soundPool.load(context, R.raw.chose, 1);

        high = soundPool.load(context, R.raw.high_attack, 1);
        low = soundPool.load(context, R.raw.low_attack, 1);
        middle = soundPool.load(context, R.raw.standart_attack, 1);

        player_death = soundPool.load(context, R.raw.player_death, 1);
        player_damage = soundPool.load(context, R.raw.enemy_damage, 1);
        base_damage = soundPool.load(context, R.raw.player_damage, 1);
        enemy_damage = soundPool.load(context, R.raw.player_damage2, 1);

        bullet_s[0] = low;
        bullet_s[1] = middle;
        bullet_s[2] = high;

    }

    public static void play_one(MediaPlayer mediaPlayer){
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
    }

    public static void createMusicResources(Context context) {
        if (musicResorces == null){
            musicResorces = new MusicResorces(context);
        }
    }

    public static void play_loop(MediaPlayer mediaPlayer){
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public static void start_menu(){
        if (!menu_start){
            menu_s.setLooping(true);
            menu_s.start();
            menu_start = true;
        }
    }

    public static void play(int id){
        MusicResorces.soundPool.play(id, 1, 1, 0, 0, 1);
    }

    public static void play_max(int id){
        MusicResorces.soundPool.play(id, 5, 5, 1, 0, 1);
    }

    public static void setMenu_start(boolean menu_start) {
        MusicResorces.menu_start = menu_start;
    }
}
