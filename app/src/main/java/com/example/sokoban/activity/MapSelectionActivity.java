package com.example.sokoban.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.example.sokoban.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MapSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_selection);

        findViewById(R.id.button_return).setOnClickListener(v -> {
            startActivity(new Intent(MapSelectionActivity.this, HomeActivity.class));
        });


        Button buttonMute = findViewById(R.id.button_mute);
        findViewById(R.id.button_mute).setOnClickListener(v -> {
                AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == 0) {
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    buttonMute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mute, 0, 0, 0);
                } else {
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    buttonMute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sound, 0, 0, 0);
                }
        });


        String map1 =
            "######\n" +
            "#..P.#\n" +
            "#B####\n" +
            "#.#   \n" +
            "#X#   \n" +
            "###   ";

        String map2 =
            "######\n" +
            "#P...#\n" +
            "#..BG#\n" +
            "#.GB.#\n" +
            "######";

        String map3 =
            "##### \n" +
            "#P..##\n" +
            "#GBS.#\n" +
            "#..#.#\n" +
            "#....#\n" +
            "######";

        String[]lesMapDure = {map1,map2,map3};

        GridLayout gridLayoutDure = findViewById(R.id.gridLayoutDure);
        createButtons(gridLayoutDure, lesMapDure);



        AssetManager am = this.getAssets();
        String[] lesMapsFichier = {};
        try {
            InputStream is = am.open("map.txt");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            int i;
            try {
                i = is.read();
                while (i != -1) {
                    byteArrayOutputStream.write(i);
                    Log.d(byteArrayOutputStream.toString(),"byteArrayOutputStream");
                    i = is.read();
                } is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        GridLayout gridLayoutFichier = findViewById(R.id.gridLayoutFichier);
        createButtons(gridLayoutFichier, lesMapsFichier);

        GridLayout gridLayoutAPI = findViewById(R.id.gridLayoutAPI);
        createButtons(gridLayoutAPI, lesMapDure);








    }

    public void createButtons(GridLayout gridLayout, String[] lesMap) {
        for (int i = 0; i < lesMap.length; i++) {
            Button button = new Button(new ContextThemeWrapper(this, R.style.ButtonValid), null, 0);
            button.setHeight(200);
            button.setWidth(200);
            button.setTextSize(25);
            button.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 0f), GridLayout.spec(GridLayout.UNDEFINED, 0f));
            params.setMargins(0, 0, 30, 20);
            button.setLayoutParams(params);
            button.setText(Integer.toString(i + 1));
            int finalI = i;
            button.setOnClickListener(v -> {
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("map", lesMap[finalI]);
                startActivity(intent);
            });
            gridLayout.addView(button);
        }
    }



}