package com.example.sokoban.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.widget.TextView;

import com.example.sokoban.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Degrader de couleur
        TextView textView = (TextView) findViewById(R.id.copy);
        String text = textView.getText().toString();
        TextPaint paint = textView.getPaint();
        float width = paint.measureText(text);
        Shader textShader = new LinearGradient(0, 0, 0, textView.getTextSize(),
                new int[]{
                        Color.parseColor("#FDF95F"),
                        Color.parseColor("#FDF95F"),
                        Color.parseColor("#FDF95F"),
                        Color.parseColor("#FDF95F"),
                        Color.parseColor("#F0D033"),
                        Color.parseColor("#F0D033"),
                        Color.parseColor("#F0D033"),
                        Color.parseColor("#DFA817")
                }, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader);

        // Bouton jouer
        findViewById(R.id.button_play).setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, MapSelectionActivity.class));
        });
    }
}