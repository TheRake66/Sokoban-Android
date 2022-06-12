package com.example.sokoban.lib;

import android.app.Activity;
import android.content.Context;
import android.widget.Button;

import com.example.sokoban.R;
import com.example.sokoban.activity.HomeActivity;

public class Function {




    public void back(Activity act, int id) {
        act.findViewById(R.id.button_return).setOnClickListener(v -> act.finish());
    }


    public void toogleMute(Activity act, int id) {
        Button buttonMute = act.findViewById(id);
        act.findViewById(R.id.button_mute).setOnClickListener(v -> {
            if (HomeActivity.sound.isMute()) {
                HomeActivity.sound.setMute(false);
                buttonMute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sound, 0, 0, 0);
            } else {
                HomeActivity.sound.setMute(true);
                buttonMute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mute, 0, 0, 0);
            }
        });
    }


}
