package com.example.sokoban.logic;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.gridlayout.widget.GridLayout;

import com.example.sokoban.R;
import com.example.sokoban.activity.GameActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Sound {

    // Le context de l'application
    private Context context;

    // Le lecteur
    private SoundPool player;
    private HashMap<Integer, Integer> loaded = new HashMap<>();

    // Mute ou pas
    private boolean mute = false;


    // Les sons
    public static final int SOUND_VICTORY = R.raw.victory;
    public static final int SOUND_BACKGROUND = R.raw.background;
    public static final int SOUND_MOVE = R.raw.move;
    public static final int SOUND_BOX_MOVE = R.raw.box;
    public static final int SOUND_BOX_PLACED = R.raw.placed;

    /**
     * Constructeur
     *
     * @param context le context de l'application
     */
    public Sound(Context context) {
        this.context = context;
        this.player = new SoundPool(12, AudioManager.STREAM_MUSIC,1);
        this.loaded.put(SOUND_VICTORY, this.player.load(this.context, SOUND_VICTORY, 1));
        this.loaded.put(SOUND_BACKGROUND, this.player.load(this.context, SOUND_BACKGROUND, 1));
        this.loaded.put(SOUND_MOVE, this.player.load(this.context, SOUND_MOVE, 1));
        this.loaded.put(SOUND_BOX_MOVE, this.player.load(this.context, SOUND_BOX_MOVE, 1));
        this.loaded.put(SOUND_BOX_PLACED, this.player.load(this.context, SOUND_BOX_PLACED, 1));
    }


    /**
     * Joue un son
     *
     * @param sound le son à jouer
     */
    public void playSound(int sound) {
        if (!mute) {
            this.player.play(this.loaded.get(sound), 1, 1, 1, 0, 1);
        }
    }


    /**
     * Joue la musique de fond
     */
    public  void playBackgroundSound(){
        if (!mute) {
            this.player.play(this.loaded.get(SOUND_BACKGROUND), 1, 1, 1, -1, 1);
        }
    }


    /**
     * Si le son est mute ou pas
     *
     * @return true si le son est mute, false sinon
     */
    public boolean isMute() {
        return mute;
    }


    /**
     * Met le son en mute ou pas
     *
     * @param mute true si le son doit être mute, false sinon
     */
    public void setMute(boolean mute) {
        this.mute = mute;
        if (mute) {
            for (Integer stream : this.loaded.values()) {
                this.player.stop(stream);
            }
        } else {
            this.playBackgroundSound();
        }
    }

}
