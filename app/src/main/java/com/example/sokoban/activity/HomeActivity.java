package com.example.sokoban.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.widget.TextView;

import com.example.sokoban.R;
import com.example.sokoban.lib.Function;
import com.example.sokoban.lib.MyDatabaseHelper;
import com.example.sokoban.logic.Sound;



public class HomeActivity extends AppCompatActivity {

    // Le media player
    public static Sound sound;

    // La BDD
    public static MyDatabaseHelper db;


    /**
     * Initialise l'accueil
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Encoche
        Function.addNotch(this, R.color.header_main);

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
                        Color.parseColor("#FDF95F"),
                        Color.parseColor("#FDF95F"),
                        Color.parseColor("#FDF95F"),
                        Color.parseColor("#F0D033"),
                        Color.parseColor("#F0D033"),
                        Color.parseColor("#F0D033"),
                        Color.parseColor("#DFA817"),
                        Color.parseColor("#DFA817")
                }, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader);

        // Bouton Play
        Function.openAct(this, MapSelectionActivity.class, R.id.button_play);

        // Bouton More Games
        findViewById(R.id.button_more).setOnClickListener(v -> {
            Uri uri = Uri.parse("https://github.com/TheRake66");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        // Initialisation des objects
        this.sound = new Sound(this);

        // BDD
        this.db = new MyDatabaseHelper(this);
    }

}