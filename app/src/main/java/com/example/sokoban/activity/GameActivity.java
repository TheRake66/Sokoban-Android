package com.example.sokoban.activity;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.sokoban.R;
import com.example.sokoban.logic.Board;
import com.example.sokoban.logic.Control;
import com.example.sokoban.logic.Entity;
import com.example.sokoban.logic.Map;
import com.example.sokoban.logic.Sound;


public class GameActivity extends AppCompatActivity {

    // Les objets communs
    public static Board board;
    public static Entity entity;
    public static Control control;
    public static Map map;


    /**
     * Initialise le jeu, charge la carte et affiche le plateau de jeu
     *
     * @param savedInstanceState
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Recuperation du numero de niveau
        String levelName = getIntent().getStringExtra("level");
        ((TextView)findViewById(R.id.level)).setText(levelName);

        // Ajout des écouteurs de clics
        findViewById(R.id.pause).setOnClickListener(v -> this.toogleMsgbox());
        findViewById(R.id.continu).setOnClickListener(v -> this.toogleMsgbox());
        findViewById(R.id.menu).setOnClickListener(v -> this.finish());
        findViewById(R.id.reset).setOnClickListener(v -> GameActivity.board.resetBoard());
        findViewById(R.id.undo).setOnClickListener(v -> GameActivity.board.loadState());
        Button mute = findViewById(R.id.button_mute_game);
        mute.setOnClickListener(v -> this.toogleMute(mute));

        // Preparation du jeu
        String map = getIntent().getStringExtra("map");
        char[][] matrix = Map.stringToMatrix(map);
        GridLayout boardLayout = findViewById(R.id.board);
        ConstraintLayout screenLayout = findViewById(R.id.container);
        TextView counter = findViewById(R.id.count);

        // Initialisation des object
        GameActivity.map = new Map();
        GameActivity.control = new Control(screenLayout, counter);
        GameActivity.entity = new Entity();
        GameActivity.board = new Board(boardLayout, matrix);
    }


    /**
     * Affiche ou cache le menu pause
     */
    private void toogleMsgbox() {
        Log.d("toogleMsgbox", "toogleMsgbox");
        ConstraintLayout msgbox = findViewById(R.id.msgbox);
        msgbox.setVisibility(
                msgbox.getVisibility() == findViewById(R.id.msgbox).INVISIBLE ?
                        findViewById(R.id.msgbox).VISIBLE :
                        findViewById(R.id.msgbox).INVISIBLE);
    }


    /**
     * Mute ou unmute le son
     *
     * @param mute Le bouton mute
     */
    private void toogleMute(Button mute) {
        if (HomeActivity.sound.isMute()) {
            HomeActivity.sound.setMute(false);
            mute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sound, 0, 0, 0);
        } else {
            HomeActivity.sound.setMute(true);
            mute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mute, 0, 0, 0);
        }
    }

}