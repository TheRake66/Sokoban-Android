package com.example.sokoban.logic;

import java.util.ArrayList;
import java.util.List;

public class Entity {

    // Les types de cases
    public static final char TYPE_EMPTY = ' ';
    public static final char TYPE_WALL = '#';
    public static final char TYPE_FLOOR = '.';
    public static final char TYPE_BOX = 'B';
    public static final char TYPE_TARGET = 'G';
    public static final char TYPE_PLAYER = 'P';
    public static final char TYPE_PLAYER_ON_TARGET = 'F';
    public static final char TYPE_BOX_ON_TARGET = 'S';
    public static final char TYPE_BACKGROUND = '.';

    // Liste des deplacements effectués
    private List<char[][]> matrixStates = new ArrayList<>();


    /**
     * Convertit un caractère en un nom d'image
     *
     * @param t Le type de case
     * @return Le nom de l'image
     */
    public static String typeToImageName(int t) {
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
     * Renvoi le nom de l'image servant d'arrière plan
     *
     * @return Le nom de l'image
     */
    public static String getBackgroundImageName() {
        return Entity.typeToImageName(Entity.TYPE_BACKGROUND);
    }

}
