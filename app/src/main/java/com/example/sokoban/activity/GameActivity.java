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
import com.example.sokoban.lib.Function;
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

        // Encoche
        Function.addNotch(this, R.color.header);

        // Recuperation du numero de niveau
        String levelName = getIntent().getStringExtra("level");
        Function.setText(this, R.id.level, levelName);

        // Ajout des écouteurs de clics
        Function.onClick(this, R.id.pause, v -> this.toogleMsgbox());
        Function.onClick(this, R.id.continu, v -> this.toogleMsgbox());
        Function.closeAct(this, R.id.menu);
        Function.onClick(this, R.id.reset, v -> GameActivity.board.resetBoard());
        Function.onClick(this, R.id.undo, v -> GameActivity.board.loadState());
        Function.toogleMute(this, R.id.button_mute_game);

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

}