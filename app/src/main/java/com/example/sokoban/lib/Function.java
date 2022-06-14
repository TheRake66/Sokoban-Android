package com.example.sokoban.lib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sokoban.R;
import com.example.sokoban.activity.GameActivity;
import com.example.sokoban.activity.HomeActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Function {

    /**
     * Cache les encoches
     *
     * @param act Le context actuelle
     * @param color La couleur
     */
    public static void addNotch(Activity act, int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(color));
        }
    }


    /**
     * Ajoute un onclick pour ouvrir une activite
     *
     * @param act Le context actuelle
     * @param open La classe cible
     * @param id L'id du bouton
     */
    public static void openAct(Activity act, Class open, int id) {
        act.findViewById(id).setOnClickListener(v -> {
            act.startActivity(new Intent(act, open));
        });
    }


    /**
     * Ajoute un onclick pour fermer une activite
     *
     * @param act Le context actuelle
     * @param id L'id du bouton
     */
    public static void closeAct(Activity act, int id) {
        act.findViewById(id).setOnClickListener(v -> {
            act.finish();
        });
    }

    /**
     * Definie un onclick
     *
     * @param act Le context actuelle
     * @param id L'id du bouton
     * @param i La fonction
     */
    public static void onClick(Activity act, int id, View.OnClickListener i) {
        act.findViewById(id).setOnClickListener(i);
    }


    /**
     * Creer un onclick sur les boutons mute
     *
     * @param act Le context actuelle
     * @param id L'id du bouton
     */
    public static void toogleMute(Activity act, int id) {
        Button buttonMute = act.findViewById(id);
        act.findViewById(id).setOnClickListener(v -> {
            if (HomeActivity.sound.isMute()) {
                HomeActivity.sound.setMute(false);
                buttonMute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sound, 0, 0, 0);
            } else {
                HomeActivity.sound.setMute(true);
                buttonMute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mute, 0, 0, 0);
            }
        });
    }


    /**
     * Creer un onclick sur les boutons ouvrir fichier
     *
     * @param act Le context actuelle
     * @param id L'id du bouton
     */
    public static void openFile(Activity act, int id) {
        act.findViewById(id).setOnClickListener(v -> {
            Intent intent = new Intent()
                    .setType("*/*")
                    .setAction(Intent.ACTION_GET_CONTENT);
            act.startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);
        });
    }


    /**
     * Retourne le text d'un element
     *
     * @param act Le context actuelle
     * @param id L'id du bouton
     * @return Le texte
     */
    public static String getText(Activity act, int id) {
        return ((TextView)act.findViewById(id)).getText().toString();
    }


    /**
     * Change le text d'un element
     *
     * @param act Le context actuelle
     * @param id L'id du bouton
     * @param text Le texte
     */
    public static void setText(Activity act, int id, String text) {
        ((TextView)act.findViewById(id)).setText(text);
    }


    /**
     * Lit le fichier d'un stream
     *
     * @param act Le context actuelle
     * @param requestCode
     * @param resultCode
     * @param data
     * @return
     */
    public static String readFile(Activity act, int requestCode, int resultCode, Intent data) {
        String results = null;
        if(requestCode == 123 && resultCode == act.RESULT_OK) {
            try {
                Uri selected = data.getData();
                InputStream fis = act.getContentResolver().openInputStream(selected);
                StringBuilder text = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String line;
                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
                results = text.toString();
            }
            catch (IOException e) {
                Toast
                    .makeText(act, "Unable to open this file" + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            }
        }
        return results;
    }


    /**
     * Retourne le nom d'un fichier sans l'extension
     *
     * @param data Le fichier
     * @return Le nom du fichier
     */
    public static String getFileNameWithoutExtension(Intent data) {
        return (new File("" + data.getData().getPath().split(":")[1])).getName().split("\\.")[0];
    }

}
