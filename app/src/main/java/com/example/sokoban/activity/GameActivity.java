package com.example.sokoban.activity;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MotionEventCompat;
import androidx.gridlayout.widget.GridLayout;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sokoban.R;
import com.example.sokoban.lib.OnSwipeTouchListener;

import java.util.Arrays;
import java.util.List;



public class GameActivity extends AppCompatActivity {

    // Le contenaire du jeu
    private ConstraintLayout gameContainer;

    // Le plateau de jeu
    private GridLayout board;

    // Compter le nombre de mouvements effectués
    private int moves;
    private TextView movesTextView;

    // La matrice de jeu
    private char[][] matrix;

    // Les types de cases
    private final char TYPE_EMPTY = ' ';
    private final char TYPE_WALL = '#';
    private final char TYPE_FLOOR = '.';
    private final char TYPE_BOX = 'B';
    private final char TYPE_TARGET = 'G';
    private final char TYPE_PLAYER = 'P';
    private final char TYPE_PLAYER_ON_TARGET = 'F';
    private final char TYPE_BOX_ON_TARGET = 'S';

    // Les directions
    private final int DIRECTION_UP = 0;
    private final int DIRECTION_RIGHT = 1;
    private final int DIRECTION_DOWN = 2;
    private final int DIRECTION_LEFT = 3;


    /**
     * Initialise le jeu, charge la carte et affiche le plateau de jeu
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // Preparation du compteur de mouvements
        this.moves = 0;
        this.movesTextView = findViewById(R.id.count);
        // Récupération du plateau de jeu
        this.board = findViewById(R.id.board);
        this.gameContainer = findViewById(R.id.container);
        this.gameContainer.setOnTouchListener(new OnSwipeTouchListener(GameActivity.this) {
            public void onSwipeTop() {
                int[] pos = GameActivity.this.getPlayerPosition();
                GameActivity.this.moveEntity(pos[0], pos[1], DIRECTION_UP);
            }
            public void onSwipeRight() {
                int[] pos = GameActivity.this.getPlayerPosition();
                GameActivity.this.moveEntity(pos[0], pos[1], DIRECTION_RIGHT);
            }
            public void onSwipeLeft() {
                int[] pos = GameActivity.this.getPlayerPosition();
                GameActivity.this.moveEntity(pos[0], pos[1], DIRECTION_LEFT);
            }
            public void onSwipeBottom() {
                int[] pos = GameActivity.this.getPlayerPosition();
                GameActivity.this.moveEntity(pos[0], pos[1], DIRECTION_DOWN);
            }

        });
        // Récupération de la carte dans les extras
        String map = getIntent().getStringExtra("map");
        // Chargement de la carte
        this.loadMap(map);
        // Affichage du plateau de jeu
        this.displayBoard();
    }


    /**
     * Charge une carte dans la matrice et creer les images
     *
     * @param map La carte à charger
     */
    private void loadMap(String map) {
        // Découpe la carte en lignes
        String[] lines = map.split("\n");
        // Initialise la matrice
        this.matrix = new char[lines.length][lines[0].length()];
        // Change le nombre de lignes et de colonnes
        this.board.setColumnCount(lines[0].length());
        this.board.setRowCount(lines.length);
        // On parcourt les lignes de la carte
        int x = 0, y = 0;
        for (String line : lines) {
            // On parcourt les caractères de la ligne
            for (char c : line.toCharArray()) {
                // On remplit la matrice
                this.setType(x, y, c);
                // On ajoute l'image correspondante
                this.addNewImage();
                x++;
            }
            x = 0;
            y++;
        }
    }


    /**
     * Dessine le plateau de jeu
     */
    private void displayBoard() {
        int index = 0;
        // On parcourt les lignes de la carte
        for (int y = 0; y < this.matrix.length; y++) {
            // On parcourt les caractères de la ligne
            for (int x = 0; x < this.matrix[y].length; x++) {
                // On ajoute l'image correspondante
                char t = this.getType(x, y);
                String name = this.typeToImageName(t);
                this.changeImage(index, name);
                index++;
            }
        }
    }


    /**
     * Deplace le joueur ou la caisse dans la matrice
     *
     * @param x La position en x
     * @param y La position en y
     * @param d La direction du déplacement
     */
    private boolean moveEntity(int x, int y, int d) {
        // Si l'entite peut se déplacer
        if (this.canMove(x, y, d)) {
            // Calcul la nouvelle position
            int[] newPos = this.changePosition(x, y, d);
            int newX = newPos[0];
            int newY = newPos[1];

            // Recupere le type de l'entite
            char t = this.getType(x, y);
            // Recupere le type de l'entite a la nouvelle position
            char newT = this.getType(newX, newY);


            // Si l'entite est un joueur sur le target ou une caisse sur le target on remet le target sinon on met le sol
            char floor = t == TYPE_PLAYER_ON_TARGET || t == TYPE_BOX_ON_TARGET ?
                    TYPE_TARGET :
                    TYPE_FLOOR;

            // Si entite est un joueur et que la case est un target alors on met le joueur sur le target
            if (t == TYPE_PLAYER && newT == TYPE_TARGET) {
                t = TYPE_PLAYER_ON_TARGET;
            // Si entite est un joueur sur le target et que la case est pas un target alors on met le joueur sur le floor
            } else if (t == TYPE_PLAYER_ON_TARGET && newT != TYPE_TARGET) {
                t = TYPE_PLAYER;
            }

            // Si entite est une caisse et que la case est un target alors on met la caisse sur le target
            if (t == TYPE_BOX && newT == TYPE_TARGET) {
                t = TYPE_BOX_ON_TARGET;
            // Si entite est une caisse sur le target et que la case est pas un target alors on met la caisse sur le floor
            } else if (t == TYPE_BOX_ON_TARGET && newT != TYPE_TARGET) {
                t = TYPE_BOX;
            }

            // On déplace l'entité
            this.setType(newX, newY, t);
            this.setType(x, y, floor);
            this.displayBoard();
            this.incrementMoves();

            // Verifi si le joueur a gagné
            if (this.checkWin()) {
                Toast.makeText(this, "Vous avez gagné !", Toast.LENGTH_SHORT).show();
                finish();
            }

            return true;
        } else {
            return false;
        }
    }


    /**
     * Incremente le nombre de coups
     */
    private void incrementMoves() {
        this.moves++;
        this.movesTextView.setText("Nombre de coups : " + this.moves);
    }


    /**
     * Verifie si une entite peut se déplacer
     * @param x La position en x
     * @param y La position en y
     * @param d La direction du déplacement
     * @return true si l'entite peut se déplacer
     */
    private boolean canMove(int x, int y, int d) {
        // Calcul la nouvelle position
        int[] newPos = this.changePosition(x, y, d);
        int newX = newPos[0];
        int newY = newPos[1];

        // Si le joueur est en dehors du plateau
        if (newX < 0 || newX >= this.matrix[0].length ||
                newY < 0 || newY >= this.matrix.length) {
            return false;
        }

        // Futur type de la case
        char t = this.getType(newX, newY);

        // Si le joueur est sur un mur
        if (t == TYPE_WALL) {
            return false;

        // Si l'entite est sur une caisse
        } else if (t == TYPE_BOX || t == TYPE_BOX_ON_TARGET) {
            return this.moveEntity(newX, newY, d);

        // Si l'entite peut se déplacer
        } else {
            return true;
        }
    }


    /**
     * Change une position par une direction
     *
     * @param x La position en x
     * @param y La position en y
     * @param d La direction du déplacement
     * @return La nouvelle position en x et y
     */
    private int[] changePosition(int x, int y, int d) {
        int[] newPosition = {x, y};
        switch (d) {
            case DIRECTION_UP:
                newPosition[1]--;
                break;
            case DIRECTION_DOWN:
                newPosition[1]++;
                break;
            case DIRECTION_LEFT:
                newPosition[0]--;
                break;
            case DIRECTION_RIGHT:
                newPosition[0]++;
                break;
        }
        return newPosition;
    }


    /**
     * Convertit un caractère en un nom d'image
     *
     * @param t Le type de case
     * @return Le nom de l'image
     */
    private String typeToImageName(int t) {
        String name = "";
        switch (t) {
            case TYPE_WALL:
                name = "box_steel";
                break;
            case TYPE_FLOOR:
                name = "floor";
                break;
            case TYPE_PLAYER:
            case TYPE_PLAYER_ON_TARGET:
                name = "player";
                break;
            case TYPE_BOX:
                name = "box";
                break;
            case TYPE_BOX_ON_TARGET:
                name = "box_green";
                break;
            case TYPE_TARGET:
                name = "gem";
                break;
            case TYPE_EMPTY:
                name = null;
                break;
        }
        return name;
    }


    /**
     * Ajoute une image vide au plateau
     */
    private void addNewImage() {
        ImageView imageView = new ImageView(this);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.width = 128;
        layoutParams.height = 128;
        imageView.setLayoutParams(layoutParams);
        this.board.addView(imageView);
    }


    /**
     * Modifi une image à la position spécifiée
     *
     * @param index L'index de l'image
     * @param name  Le nom de l'image
     */
    private void changeImage(int index, String name) {
        ImageView imageView = (ImageView)this.board.getChildAt(index);
        // Pour les images vide, on ne charge pas d'image
        if (name != null) {
            imageView.setBackgroundResource(this.getResourcesId("floor"));
            imageView.setImageResource(this.getResourcesId(name));
        }
    }


    /**
     * Retourne l'identifiant de la ressource spécifiée
     *
     * @param image Le nom de la ressource
     * @return L'identifiant de la ressource
     */
    private int getResourcesId(String image) {
        return super
                .getResources()
                .getIdentifier(image, "drawable", this.getPackageName());
    }


    /**
     * Récupère la position du joueur
     *
     * @return La position du joueur (x, y)
     */
    private int[] getPlayerPosition() {
        int[] p = new int[2];
        for (int y = 0; y < this.matrix.length; y++) {
            for (int x = 0; x < this.matrix[y].length; x++) {
                int t = this.getType(x, y);
                if (t == TYPE_PLAYER || t == TYPE_PLAYER_ON_TARGET) {
                    p[0] = x;
                    p[1] = y;
                }
            }
        }
        return p;
    }


    /**
     * Recupere le type de la case à la position spécifiée
     *
     * @param x La position en x
     * @param y La position en y
     * @return Le type de la case
     */
    private char getType(int x, int y) {
        return this.matrix[y][x];
    }


    /**
     * Change le type de la case à la position spécifiée
     *
     * @param x La position en x
     * @param y La position en y
     * @param type Le type de la case
     */
    private void setType(int x, int y, char type) {
        this.matrix[y][x] = type;
    }


    /**
     * Verifie si toutes les caisses sont sur les cibles
     *
     * @return true si toutes les caisses sont sur les cibles
     */
    private boolean checkWin() {
        for (int y = 0; y < this.matrix.length; y++) {
            for (int x = 0; x < this.matrix[y].length; x++) {
                int t = this.getType(x, y);
                // Il reste encore des caisse sans target
                if (t == TYPE_BOX) {
                    return false;
                }
            }
        }
        return true;
    }

}