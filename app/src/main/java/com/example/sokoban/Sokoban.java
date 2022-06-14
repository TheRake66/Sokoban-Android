package com.example.sokoban;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.sokoban.activity.HomeActivity;



public class Sokoban extends Application implements LifecycleObserver {

    /**
     * Gere l'evennement quand met en veille l'application
     */
    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }


    /**
     * Mise en veille
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void onAppBackgrounded() {
        HomeActivity.sound.setMute(true);
    }


    /**
     * Sortie de veille
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void onAppForegrounded() {
        HomeActivity.sound.setMute(false);
    }

}