package com.example.sokoban.lib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    /**
     * Initialisation
     *
     * @param context
     */
    public MyDatabaseHelper(Context context)  {
        super(context, "sokoban", null, 1);
    }


    /**
     * Creer la BDD
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE boards (" +
                "   name varchar(50) NOT NULL," +
                "   board varchar(1000) NOT NULL," +
                "   width int(11) NOT NULL," +
                "   height int(11) NOT NULL," +
                "   PRIMARY KEY (name)" +
                ")");
    }


    /**
     * Recreer la BDD
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS boards");
        this.onCreate(db);
    }


    /**
     * Creer un niveau
     *
     * @param board Le niveau a creer
     */
    public void addBoard(BoardEntity board) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", board.name);
        values.put("board", board.board);
        values.put("width", board.width);
        values.put("height", board.height);
        db.insert("boards", null, values);
        db.close();
    }


    /**
     * Recupere les niveaux
     *
     * @return List<BoardEntity> Les niveaux
     */
    public List<BoardEntity> getAllBoards() {
        List<BoardEntity> boards = new ArrayList<BoardEntity>();
        String selectQuery = "SELECT * FROM boards";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                BoardEntity board = new BoardEntity(
                        cursor.getString(0),
                        cursor.getString(1),
                        Integer.parseInt(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3))
                );
                boards.add(board);
            } while (cursor.moveToNext());
        }
        return boards;
    }


    /**
     * Recupere le nombre de niveau
     *
     * @return int Le nombre
     */
    public int getBoardsCount() {
        String countQuery = "SELECT  * FROM boards";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


    /**
     * Supprime un niveau
     *
     * @param board Le niveau a supprimer
     */
    public void deleteNote(BoardEntity board) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("boards", "name = ?",
                new String[] { String.valueOf(board.name) });
        db.close();
    }

}
