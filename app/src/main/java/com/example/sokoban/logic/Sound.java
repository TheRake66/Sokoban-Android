package com.example.sokoban.logic;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.gridlayout.widget.GridLayout;

import com.example.sokoban.R;
import com.example.sokoban.activity.GameActivity;

import java.util.ArrayList;
import java.util.List;


public class Sound {

    // Le context de l'application
    private Context context;

    // Mute ou pas
    private boolean mute = false;

    // Les sons
    public static final int SOUND_VICTORY = R.raw.victory;
    public static final int SOUND_BACKGROUND = R.raw.background;
    public static final int SOUND_MOVE = R.raw.move;
    public static final int SOUND_BOX_MOVE = R.raw.box_move;
    public static final int SOUND_BOX_PLACED = R.raw.box_placed;


    /**
     * Constructeur
     *
     * @param context le context de l'application
     */
    public Sound(Context context) {
        this.context = context;
        final MediaPlayer mp = MediaPlayer.create(this.context, SOUND_BACKGROUND);
        mp.setLooping(true);
        mp.start();
    }


    /**
     * Joue un son
     *
     * @param sound le son à jouer
     */
    public void playSound(int sound) {
        if (!mute) {
            final MediaPlayer mp = MediaPlayer.create(this.context, sound);
            mp.start();
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
    }

}
