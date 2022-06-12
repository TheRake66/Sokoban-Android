package com.example.sokoban.lib;

import java.io.Serializable;


public class BoardEntity implements Serializable {

    public String name;
    public String board;
    public int width;
    public int height;


    public BoardEntity(String name, String board, int width, int height) {
        this.name = name;
        this.board = board;
        this.width = width;
        this.height = height;
    }

}
