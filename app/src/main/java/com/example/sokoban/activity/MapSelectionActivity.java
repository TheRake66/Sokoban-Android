package com.example.sokoban.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.example.sokoban.R;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_selection);

        // Le bontoun retour redirige vers l'accueil
        findViewById(R.id.button_return).setOnClickListener(v -> this.finish());

        //Le bouton mute coupe les sons et affiche un bouton pour les rétablir
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

        // définie les maps en dur
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



                        List<String>lesMapDure = Arrays.asList(map1, map2, map3, map4, map5, map6);
        //Ajoute les maps en dur dans le gridlayout et crée les boutons de sélection de map
        GridLayout gridLayoutDure = findViewById(R.id.gridLayoutDure);
        gridLayoutDure.setColumnCount(4);

        createButtons(gridLayoutDure, lesMapDure);


        //Lecture du fichier map.txt dans le dossier assets/maps
        List<String>lesMapsFichier = new ArrayList<>();
        AssetManager assetManager = getAssets();


        loadMapFile(assetManager, "maps/map.txt", lesMapsFichier);
        Log.d("mapFichier", lesMapsFichier.toString());

        loadMapFile(assetManager, "maps/map2.txt", lesMapsFichier);
        Log.d("mapFichier2", lesMapsFichier.toString());

        loadMapFile(assetManager, "maps/map3.txt", lesMapsFichier);
        Log.d("mapFichier3", lesMapsFichier.toString());

        loadMapFile(assetManager, "maps/map4.txt", lesMapsFichier);
        Log.d("mapFichier4", lesMapsFichier.toString());

        loadMapFile(assetManager, "maps/map5.txt", lesMapsFichier);
        Log.d("mapFichier5", lesMapsFichier.toString());

        loadMapFile(assetManager, "maps/map6.txt", lesMapsFichier);
        Log.d("mapFichier6", lesMapsFichier.toString());

        //Ajoute les maps en fichier dans le gridlayout et crée les boutons de sélection de map
        GridLayout gridLayoutFichier = findViewById(R.id.gridLayoutFichier);
        gridLayoutFichier.setColumnCount(4);
        createButtons(gridLayoutFichier, lesMapsFichier);



        //Lecture de l'API pour récupérer les maps
        List<String>lesMapsAPI = new ArrayList<>();;



        //Ajoute les maps de l'API dans le gridlayout et crée les boutons de sélection de map
        GridLayout gridLayoutAPI = findViewById(R.id.gridLayoutAPI);
        gridLayoutAPI.setColumnCount(4);
        createButtons(gridLayoutAPI, lesMapDure);


    }

    /**
     * fonction pour créer les boutons de sélection de map
     * @param gridLayout
     * @param lesMap
     */
    public void createButtons(GridLayout gridLayout, List<String> lesMap) {
        //parcourt toutes les maps
        for (int i = 0; i < lesMap.size(); i++) {
            //crée un bouton et ajoute le style
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
            //ajoute un listener sur le bouton pour lancer la partie
            button.setOnClickListener(v -> {
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("map", lesMap.get(finalI));
                intent.putExtra("level", finalI + 1);
                startActivity(intent);
            });
            //ajoute le bouton dans le gridlayout
            gridLayout.addView(button);
        }
    }

    public void loadMapFile(AssetManager assetManager,String fileName, List<String> lesMapsFichier) {
        try {
            //on lit le fichier map dans les assets
            InputStream inputStream = assetManager.open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            String map = "";
            //on parcourt le fichier ligne par ligne
            while ((line = bufferedReader.readLine()) != null) {
                //on construit la map
                map += line + "\n";
            }

            //On ajoute la map dans le tableau
            lesMapsFichier.add(map);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}