package com.example.sokoban.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.sokoban.MainActivity;
import com.example.sokoban.R;

public class HomelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(R.id.button_play).setOnClickListener(v -> {
            startActivity(new Intent(HomelActivity.this, MapSelectionActivity.class));
        });
    }
}