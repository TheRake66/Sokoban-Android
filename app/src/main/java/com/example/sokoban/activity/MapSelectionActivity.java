package com.example.sokoban.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;

import com.example.sokoban.R;
import com.example.sokoban.lib.BoardEntity;
import com.example.sokoban.lib.Function;
import com.example.sokoban.lib.MyDatabaseHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapSelectionActivity extends AppCompatActivity {


    /**
     * Charge la liste des maps
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_selection);

        // Encoche
        Function.addNotch(this, R.color.header);

        // Bouton retour
        Function.closeAct(this, R.id.button_return);

        // Bouton Play
        Function.openAct(this, AdminActivity.class, R.id.button_admin);

        // Bouton mute
        Function.toogleMute(this, R.id.button_mute);

        //Ajoute les maps en dur dans le gridlayout et crée les boutons de sélection de map
        GridLayout gridLayoutDure = findViewById(R.id.gridLayoutDure);
        this.createButtons(gridLayoutDure, this.getHardMaps());

        // Bouton ouvrir
        Function.openFile(this, R.id.button_ouvrir);

        // Charge les maps en BDD
        GridLayout gridLayoutSQLite = findViewById(R.id.gridLayoutSQLite);
        List<BoardEntity> boards = HomeActivity.db.getAllBoards();
        this.createButtons(gridLayoutSQLite, boards);
    }


    /**
     * Recupere le fichier selectionne
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String result = Function.readFile(this, requestCode, resultCode, data);
        if (result != null) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("map", result.toString());
            String name = Function.getFileNameWithoutExtension(data);
            intent.putExtra("level", name);
            startActivity(intent);
        }
    }


    /**
     * Recupere la liste des maps en dure
     *
     * @return List<BoardEntity> Les maps
     */
    public List<BoardEntity> getHardMaps() {
        List<BoardEntity> maps = new ArrayList<>();

        maps.add(new BoardEntity(
                "Level 1",
                "######\n" +
                    "#..P.#\n" +
                    "#B####\n" +
                    "#.#---\n" +
                    "#G#---\n" +
                    "###---",
                0,
                0
        ));

        maps.add(new BoardEntity(
                "Level 2",
                "######\n" +
                    "#P...#\n" +
                    "#..BG#\n" +
                    "#.GB.#\n" +
                    "######",
                0,
                0
        ));

        maps.add(new BoardEntity(
                "Level 3",
                "#####-\n" +
                    "#P..##\n" +
                    "#GBS.#\n" +
                    "#..#.#\n" +
                    "#....#\n" +
                    "######",
                0,
                0
        ));

        maps.add(new BoardEntity(
                "Level 4",
                "----#######--------\n" +
                    "----#..#..####-----\n" +
                    "#####.B#B.#..##----\n" +
                    "#GG.#..#..#...#----\n" +
                    "#GG.#.B#B.#..B####-\n" +
                    "#G..#.....#B..#..#-\n" +
                    "#GG...B#..#.B....#-\n" +
                    "#GGP#..#B.#B..#..#-\n" +
                    "#GG.#.B#.....B#..#-\n" +
                    "#GG.#..#BB#B..#..##\n" +
                    "#GG.#.B#..#..B#B..#\n" +
                    "#GG.#..#..#...#...#\n" +
                    "##G.####..#####...#\n" +
                    "-####--####---#####",
                0,
                0
        ));

        maps.add(new BoardEntity(
                "Level 5",
                "####---######--------\n" +
                    "#.B#---#...G#--------\n" +
                    "#..#####..#########--\n" +
                    "#..G...#........B.#--\n" +
                    "#.B..G.#....G####.#--\n" +
                    "#..##..#..######..#--\n" +
                    "#.....###.######.##--\n" +
                    "####B.###.#####..#---\n" +
                    "---#..##..G####.##---\n" +
                    "---#G..#......B.#----\n" +
                    "---#.B..G.####..#----\n" +
                    "---#..###.####.##----\n" +
                    "--##.GG##B####.##----\n" +
                    "--#.B..........G#----\n" +
                    "--#........B.####----\n" +
                    "--####..###P.#-------\n" +
                    "-----#B.#-####-------\n" +
                    "-----#..#------------\n" +
                    "-----####------------",
                0,
                0
        ));

        maps.add(new BoardEntity(
                "Level 6",
                "--------#####-------------\n" +
                    "--------#...####----------\n" +
                    "--------#.B....####--####-\n" +
                    "--------#...#.B#..####..#-\n" +
                    "###########.#...B...#...#-\n" +
                    "#GG.....#.B..####.#..#..#-\n" +
                    "#GGB..#...B..#..B.#.B.G##-\n" +
                    "#GS#.#.B.B.##..##....#G#--\n" +
                    "#GG#B.P.#...##....BB.#G#--\n" +
                    "#GG#.B.B..B.B.##...##.G#--\n" +
                    "#GSBB.#.##...B.#B#.B.#G#--\n" +
                    "#GG#......##...#.....#G#--\n" +
                    "#GG#######..###.######G##-\n" +
                    "#.BB..................SG##\n" +
                    "#..##################..GG#\n" +
                    "####----------------######",
                0,
                0
        ));

        return maps;
    }


    /**
     * Creer une liste de boutons
     *
     * @param grid La liste ou on ajoute les boutons
     * @param maps La liste de maps
     */
    public void createButtons(GridLayout grid, List<BoardEntity> maps) {
        for (int i = 0; i < maps.size(); i++) {
            Button button = new Button(new ContextThemeWrapper(this, R.style.ButtonLevel), null, 0);
            button.setHeight(200);
            button.setWidth(200);
            button.setTextSize(25);
            button.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 0f), GridLayout.spec(GridLayout.UNDEFINED, 0f));
            params.setMargins(0, 0, 32, 32);
            button.setLayoutParams(params);
            button.setText(Integer.toString(i + 1));
            int finalI = i;
            button.setOnClickListener(v -> {
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("map", maps.get(finalI).board);
                intent.putExtra("level", maps.get(finalI).name);
                startActivity(intent);
            });
            grid.addView(button);
        }
    }

}