package com.example.sokoban.logic;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.gridlayout.widget.GridLayout;

import com.example.sokoban.activity.GameActivity;
import com.example.sokoban.activity.HomeActivity;

import java.util.ArrayList;
import java.util.List;


public class Board {

    // La matrice de jeu
    private char[][] matrix;

    // Carte originale
    private char[][] originalMatrix;

    // Sauvegarde de la carte
    private List<char[][]> statesMatrix = new ArrayList<>();

    // Le plateau de jeu
    private GridLayout board;
    private Context context;

    // La taille du plateau
    private int width;
    private int height;
    private int nbBoxes;


    /**
     * Constructeur
     *
     * @param board Le plateau de jeu
     * @param matrix La matrice de jeu
     */
    public Board(GridLayout board, char[][] matrix) {
        this.board = board;
        this.context = board.getContext();
        this.matrix = matrix;
        this.height = matrix.length;
        this.width = matrix[0].length;
        this.nbBoxes = width * height;
        this.originalMatrix = new char[this.height][this.width];
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                this.originalMatrix[y][x] = this.getType(x, y);
            }
        }
        this.loadMap();
        this.displayBoard();
    }


    /**
     * Creer les images
     */
    private void loadMap() {
        // Change le nombre de lignes et de colonnes
        this.board.setColumnCount(width);
        this.board.setRowCount(height);
        // On creer le nombre d'images correspondant au nombre de cases
        for (int i = 0; i < this.nbBoxes; i++) {
            this.addNewImage();
        }
    }


    /**
     * Dessine le plateau de jeu
     */
    public void displayBoard() {
        int index = 0;
        // On parcourt les lignes de la carte
        for (int y = 0; y < this.height; y++) {
            // On parcourt les caractères de la ligne
            for (int x = 0; x < this.width; x++) {
                // On ajoute l'image correspondante
                char t = this.getType(x, y);
                String name = Entity.typeToImageName(t);
                this.changeImage(index, name);
                index++;
            }
        }
    }


    /**
     * Remet le plateau de jeu à son état original
     */
    public void resetBoard() {
        for (int y = 0; y < this.matrix.length; y++) {
            for (int x = 0; x < this.matrix[y].length; x++) {
                this.setType(x, y, this.originalMatrix[y][x]);
            }
        }
        this.displayBoard();
        this.statesMatrix.clear();
        GameActivity.control.resetMoves();
    }


    /**
     * Récupère la position du joueur
     *
     * @return La position du joueur (x, y)
     */
    public int[] getPlayerPosition() {
        int[] p = new int[2];
        for (int y = 0; y < this.matrix.length; y++) {
            for (int x = 0; x < this.matrix[y].length; x++) {
                int t = this.getType(x, y);
                if (t == Entity.TYPE_PLAYER || t == Entity.TYPE_PLAYER_ON_TARGET) {
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
    public char getType(int x, int y) {
        return this.matrix[y][x];
    }


    /**
     * Change le type de la case à la position spécifiée
     *
     * @param x La position en x
     * @param y La position en y
     * @param type Le type de la case
     */
    public void setType(int x, int y, char type) {
        this.matrix[y][x] = type;
    }


    /**
     * Retourne le plateau de jeu
     *
     * @return Le plateau de jeu
     */
    public char[][] getBoard() {
        return this.matrix;
    }


    /**
     * Retourne la largeur du plateau de jeu
     *
     * @return La largeur du plateau de jeu
     */
    public int getWidth() {
        return this.width;
    }


    /**
     * Retourne la hauteur du plateau de jeu
     *
     * @return La hauteur du plateau de jeu
     */
    public int getHeight() {
        return this.height;
    }


    /**
     * Sauvegarde l'etat du plateau de jeu
     */
    public void saveState() {
        // Sauvegarde du mouvement
        char[][] save = new char[this.height][this.width];
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                save[y][x] = this.getType(x, y);
            }
        }
        this.statesMatrix.add(save);
        Log.d("GameActivity", "Sauvegarde du mouvement");
        this.printMatrix();
    }


    /**
     * Restaure l'état du plateau de jeu
     */
    public void loadState() {
        if (this.statesMatrix.size() > 0) {
            int lastIndex = this.statesMatrix.size() - 1;
            char[][] lastState = this.statesMatrix.get(lastIndex);
            for (int y = 0; y < this.height; y++) {
                for (int x = 0; x < this.width; x++) {
                    this.setType(x, y, lastState[y][x]);
                }
            }
            this.statesMatrix.remove(lastIndex);
            this.displayBoard();
            GameActivity.control.decrementMoves();
            Log.d("GameActivity", "Annulation du mouvement");
            this.printMatrix();
        }
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
            imageView.setBackgroundResource(this.getResourcesId(Entity.getBackgroundImageName()));
            imageView.setImageResource(this.getResourcesId(name));
        }
    }


    /**
     * Ajoute une image vide au plateau
     */
    private void addNewImage() {
        ImageView imageView = new ImageView(this.context);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.width = 128;
        layoutParams.height = 128;
        imageView.setLayoutParams(layoutParams);
        this.board.addView(imageView);
    }


    /**
     * Retourne l'identifiant de la ressource spécifiée
     *
     * @param image Le nom de la ressource
     * @return L'identifiant de la ressource
     */
    private int getResourcesId(String image) {
        return this.context
                .getResources()
                .getIdentifier(image, "drawable", this.context.getPackageName());
    }


    /**
     * Verifie si toutes les caisses sont sur les cibles
     */
    public void checkWin() {
        for (int y = 0; y < this.matrix.length; y++) {
            for (int x = 0; x < this.matrix[y].length; x++) {
                int t = this.getType(x, y);
                // Il reste encore des caisse sans target
                if (t == Entity.TYPE_BOX) {
                    return;
                }
            }
        }
        Toast
                .makeText(this.context, "Vous avez gagné !", Toast.LENGTH_SHORT)
                .show();
        Handler handler = new Handler();
        handler.postDelayed(() -> ((Activity)this.context).finish(), 2000);
    }


    /**
     * Affichage du plateau de jeu dans la console
     */
    private void printMatrix() {
        Log.d("GameActivity", GameActivity.map.matrixToString(this.matrix));
    }

}
