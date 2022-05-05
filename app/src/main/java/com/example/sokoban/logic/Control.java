package com.example.sokoban.logic;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import android.widget.TextView;

import com.example.sokoban.activity.GameActivity;
import com.example.sokoban.lib.OnSwipeTouchListener;

public class Control {

    // Compter le nombre de mouvements effectués
    private int moves = 0;
    private String baseText = "Moves count: ";
    private TextView movesTextView;
    private ConstraintLayout screenLayout;

    // Les directions
    private final int DIRECTION_UP = 0;
    private final int DIRECTION_RIGHT = 1;
    private final int DIRECTION_DOWN = 2;
    private final int DIRECTION_LEFT = 3;


    /**
     * Constructeur
     *
     * @param swipeContainer Le container de l'ecran tactile
     * @param movesCounter Le compteur de mouvements
     */
    @SuppressLint("ClickableViewAccessibility")
    public Control(ConstraintLayout swipeContainer, TextView movesCounter) {
        this.movesTextView = movesCounter;
        this.screenLayout = swipeContainer;
        this.refreshMoves();
        this.screenLayout.setOnTouchListener(new OnSwipeTouchListener(swipeContainer.getContext()) {
            public void onSwipeTop() {
                Control.this.swipeScreen(DIRECTION_UP);
            }
            public void onSwipeRight() {
                Control.this.swipeScreen(DIRECTION_RIGHT);
            }
            public void onSwipeLeft() {
                Control.this.swipeScreen(DIRECTION_LEFT);
            }
            public void onSwipeBottom() {
                Control.this.swipeScreen(DIRECTION_DOWN);
            }
        });
    }


    /**
     * Gere le glissement sur l'ecran tactile
     *
     * @param d La direction du glissement
     */
    private void swipeScreen(int d) {
        int[] pos = GameActivity.board.getPlayerPosition();
        int x = pos[0];
        int y = pos[1];
        if (this.canMove(x, y, d)) {
            GameActivity.board.saveState();
            this.moveEntity(x, y, d);
            this.incrementMoves();
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
     * Deplace le joueur ou la caisse dans la matrice
     *
     * @param x La position en x
     * @param y La position en y
     * @param d La direction du déplacement
     * @return True si le déplacement a été effectué, false sinon
     */
    private boolean moveEntity(int x, int y, int d) {
        // Si l'entite peut se déplacer
        if (this.canMove(x, y, d)) {
            // Calcul la nouvelle position
            int[] newPos = this.changePosition(x, y, d);
            int newX = newPos[0];
            int newY = newPos[1];

            // Recupere le type de l'entite
            char t = GameActivity.board.getType(x, y);
            // Recupere le type de l'entite a la nouvelle position
            char newT = GameActivity.board.getType(newX, newY);


            // Si l'entite est un joueur sur le target ou une caisse sur le target on remet le target sinon on met le sol
            char floor = t == Entity.TYPE_PLAYER_ON_TARGET || t == Entity.TYPE_BOX_ON_TARGET ?
                    Entity.TYPE_TARGET :
                    Entity.TYPE_FLOOR;

            // Si entite est un joueur et que la case est un target alors on met le joueur sur le target
            if (t == Entity.TYPE_PLAYER && newT == Entity.TYPE_TARGET) {
                t = Entity.TYPE_PLAYER_ON_TARGET;
                // Si entite est un joueur sur le target et que la case est pas un target alors on met le joueur sur le floor
            } else if (t == Entity.TYPE_PLAYER_ON_TARGET && newT != Entity.TYPE_TARGET) {
                t = Entity.TYPE_PLAYER;
            }

            // Si entite est une caisse et que la case est un target alors on met la caisse sur le target
            if (t == Entity.TYPE_BOX && newT == Entity.TYPE_TARGET) {
                t = Entity.TYPE_BOX_ON_TARGET;
                // Si entite est une caisse sur le target et que la case est pas un target alors on met la caisse sur le floor
            } else if (t == Entity.TYPE_BOX_ON_TARGET && newT != Entity.TYPE_TARGET) {
                t = Entity.TYPE_BOX;
            }

            // On déplace l'entité
            GameActivity.board.setType(newX, newY, t);
            GameActivity.board.setType(x, y, floor);
            GameActivity.board.displayBoard();

            // Verifi si le joueur a gagné
            GameActivity.board.checkWin();

            return true;
        } else {
            return false;
        }
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
        char[][] board = GameActivity.board.getBoard();
        if (newX < 0 || newX >= board[0].length ||
                newY < 0 || newY >= board.length) {
            return false;
        }

        // Futur type de la case
        char t = GameActivity.board.getType(newX, newY);

        // Si le joueur est sur un mur
        if (t == Entity.TYPE_WALL) {
            return false;

            // Si l'entite est sur une caisse
        } else if (t == Entity.TYPE_BOX || t == Entity.TYPE_BOX_ON_TARGET) {
            return this.moveEntity(newX, newY, d);

            // Si l'entite peut se déplacer
        } else {
            return true;
        }
    }


    /**
     * Incremente le nombre de coups
     */
    private void incrementMoves() {
        this.moves++;
        this.refreshMoves();
    }


    /**
     * Decremente le nombre de coups
     */
    public void decrementMoves() {
        this.moves--;
        this.refreshMoves();
    }


    /**
     * Remet le nombre de coups à 0
     */
    public void resetMoves() {
        this.moves = 0;
        this.refreshMoves();
    }


    /**
     * Affichage du nombre de coups
     */
    public void refreshMoves() {
        this.movesTextView.setText(this.baseText + this.moves);
    }

}
