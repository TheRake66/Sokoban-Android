package com.example.sokoban.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.sokoban.R;

public class MapSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_selection);

        String map =
                "######\n" +
                "#..P.#\n" +
                "#B####\n" +
                "#.#   \n" +
                "#G#   \n" +
                "###   ";

        findViewById(R.id.button5).setOnClickListener(v -> {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("map", map);
            startActivity(intent);
        });
    }
}