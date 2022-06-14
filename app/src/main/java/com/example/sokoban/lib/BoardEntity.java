package com.example.sokoban.lib;

import java.io.Serializable;


public class BoardEntity implements Serializable {

    /**
     * Les proprietes
     */
    public String name;
    public String board;
    public int width;
    public int height;


    /**
     * Constructeur
     *
     * @param name Le nom de la carte
     * @param board La carte
     * @param width La largeur
     * @param height La hauteur
     */
    public BoardEntity(String name, String board, int width, int height) {
        this.name = name;
        this.board = board;
        this.width = width;
        this.height = height;
    }

}
