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

        // Bouton retour
        findViewById(R.id.button_return).setOnClickListener(v -> this.finish());

        // Bouton mute
        Button buttonMute = findViewById(R.id.button_mute);
        findViewById(R.id.button_mute).setOnClickListener(v -> {
            if (HomeActivity.sound.isMute()) {
                HomeActivity.sound.setMute(false);
                buttonMute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sound, 0, 0, 0);
            } else {
                HomeActivity.sound.setMute(true);
                buttonMute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mute, 0, 0, 0);
            }
        });

        // Bouton ouvrir
        findViewById(R.id.button_ouvrir).setOnClickListener(v -> {
            Intent intent = new Intent()
                    .setType("*/*")
                    .setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);
        });

        //Ajoute les maps en dur dans le gridlayout et crée les boutons de sélection de map
        GridLayout gridLayoutDure = findViewById(R.id.gridLayoutDure);
        this.createButtons(gridLayoutDure, this.getHardMaps());
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
        if(requestCode == 123 && resultCode == RESULT_OK) {
            try {
                Uri selected = data.getData();
                InputStream fis = getContentResolver().openInputStream(selected);
                StringBuilder text = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String line;
                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("map", text.toString());
                String name = (new File("" + selected.getPath().split(":")[1])).getName();
                intent.putExtra("level", name);
                startActivity(intent);
            }
            catch (IOException e) {
                Toast
                    .makeText(this, "Unable to open this file" + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            }
        }
    }


    /**
     * Recupere la liste des maps en dure
     *
     * @return List<String> Les maps
     */
    public List<String> getHardMaps() {
        String map1 =
                "######\n" +
                "#..P.#\n" +
                "#B####\n" +
                "#.#---\n" +
                "#G#---\n" +
                "###---";

        String map2 =
                "######\n" +
                "#P...#\n" +
                "#..BG#\n" +
                "#.GB.#\n" +
                "######";

        String map3 =
                "#####-\n" +
                "#P..##\n" +
                "#GBS.#\n" +
                "#..#.#\n" +
                "#....#\n" +
                "######";

        String map4 =
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
                "-####--####---#####";

        String map5 =
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
                "-----####------------";

        String map6 =
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
                "####----------------######";

        return Arrays.asList(map1, map2, map3, map4, map5, map6);
    }


    /**
     * Creer une liste de boutons
     *
     * @param grid La liste ou on ajoute les boutons
     * @param maps La liste de maps
     */
    public void createButtons(GridLayout grid, List<String> maps) {
        for (int i = 0; i < maps.size(); i++) {
            Button button = new Button(new ContextThemeWrapper(this, R.style.ButtonLevel), null, 0);
            button.setHeight(200);
            button.setWidth(200);
            button.setTextSize(25);
            button.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 0f), GridLayout.spec(GridLayout.UNDEFINED, 0f));
            params.setMargins(0, 0, 30, 20);
            button.setLayoutParams(params);
            button.setText(Integer.toString(i + 1));
            int finalI = i;
            button.setOnClickListener(v -> {
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("map", maps.get(finalI));
                intent.putExtra("level", "Level " + Integer.toString(finalI + 1));
                startActivity(intent);
            });
            grid.addView(button);
        }
    }

}