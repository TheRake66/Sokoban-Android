package com.example.sokoban.activity;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import android.widget.TextView;

import com.example.sokoban.R;
import com.example.sokoban.logic.Board;
import com.example.sokoban.logic.Control;
import com.example.sokoban.logic.Entity;
import com.example.sokoban.logic.Map;


public class GameActivity extends AppCompatActivity {

    // Les objets communs
    public static Board board;
    public static Entity entity;
    public static Control control;
    public static Map map;


    /**
     * Initialise le jeu, charge la carte et affiche le plateau de jeu
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Recuperation du numero de niveau
        int numLevel = getIntent().getIntExtra("level", 1);
        ((TextView)findViewById(R.id.level)).setText("Level " + numLevel);

        // Ajout des écouteurs de clics
        findViewById(R.id.pause).setOnClickListener(v -> this.toogleMsgbox());
        findViewById(R.id.continu).setOnClickListener(v -> this.toogleMsgbox());
        findViewById(R.id.menu).setOnClickListener(v -> this.finish());
        findViewById(R.id.reset).setOnClickListener(v -> GameActivity.board.resetBoard());
        findViewById(R.id.continu).setOnClickListener(v -> GameActivity.board.loadState());

        // Preparation du jeu
        String map = getIntent().getStringExtra("map");
        char[][] matrix = Map.stringToMatrix(map);
        GridLayout boardLayout = findViewById(R.id.board);
        ConstraintLayout screenLayout = findViewById(R.id.container);
        TextView counter = findViewById(R.id.count);

        // Initialisation des object
        GameActivity.board = new Board(boardLayout, matrix);
        GameActivity.entity = new Entity();
        GameActivity.control = new Control(screenLayout, counter);
        GameActivity.map = new Map();
        getIntent().getIntExtra("level", 1);
    }


    /**
     * Affiche ou cache le menu pause
     */
    private void toogleMsgbox() {
        ConstraintLayout msgbox = findViewById(R.id.msgbox);
        msgbox.setVisibility(
                msgbox.getVisibility() == findViewById(R.id.msgbox).INVISIBLE ?
                        findViewById(R.id.msgbox).VISIBLE :
                        findViewById(R.id.msgbox).INVISIBLE);
    }


}