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
import java.util.Arrays;

public class MapSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_selection);

        // Le bontoun retour redirige vers l'accueil
        findViewById(R.id.button_return).setOnClickListener(v -> {
            startActivity(new Intent(MapSelectionActivity.this, HomeActivity.class));
        });

        //Le bouton mute coupe les sons et affiche un bouton pour les rétablir
        Button buttonMute = findViewById(R.id.button_mute);
        findViewById(R.id.button_mute).setOnClickListener(v -> {
                AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == 0) {
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    buttonMute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mute, 0, 0, 0);
                } else {
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    buttonMute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sound, 0, 0, 0);
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

        String[]lesMapDure = {map1,map2,map3};

        //Ajoute les maps en dur dans le gridlayout et crée les boutons de sélection de map
        GridLayout gridLayoutDure = findViewById(R.id.gridLayoutDure);

        Log.d("MapSelectionActivity", "lesMapsDure = " + Arrays.toString(lesMapDure));
        createButtons(gridLayoutDure, lesMapDure);


        //Lecture du fichier map.txt dans le dossier assets/maps
        String[] lesMapsFichier = null;
        AssetManager assetManager = getAssets();
        try {
            //on lit le fichier map dans les assets
            InputStream inputStream = assetManager.open("maps/map.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            String map = "";
            //on parcourt le fichier ligne par ligne
            while ((line = bufferedReader.readLine()) != null) {
                //on construit la map
                map += line + "\n";
            }
            map += ",";
            //On ajoute la map dans le tableau
            lesMapsFichier = map.split(",");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Ajoute les maps en fichier dans le gridlayout et crée les boutons de sélection de map
        GridLayout gridLayoutFichier = findViewById(R.id.gridLayoutFichier);
        createButtons(gridLayoutFichier, lesMapsFichier);



        //Lecture de l'API pour récupérer les maps
        String[] lesMapsAPI = {};



        //Ajoute les maps de l'API dans le gridlayout et crée les boutons de sélection de map
        GridLayout gridLayoutAPI = findViewById(R.id.gridLayoutAPI);
        createButtons(gridLayoutAPI, lesMapDure);


    }

    /**
     * fonction pour créer les boutons de sélection de map
     * @param gridLayout
     * @param lesMap
     */
    public void createButtons(GridLayout gridLayout, String[] lesMap) {
        //parcourt toutes les maps
        for (int i = 0; i < lesMap.length; i++) {
            //crée un bouton et ajoute le style
            Button button = new Button(new ContextThemeWrapper(this, R.style.ButtonValid), null, 0);
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
                intent.putExtra("map", lesMap[finalI]);
                startActivity(intent);
            });
            //ajoute le bouton dans le gridlayout
            gridLayout.addView(button);
        }
    }



}