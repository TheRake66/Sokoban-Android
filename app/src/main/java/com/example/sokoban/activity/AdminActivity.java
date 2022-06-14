package com.example.sokoban.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.sokoban.R;
import com.example.sokoban.lib.BoardEntity;
import com.example.sokoban.lib.Function;

import java.io.File;
import java.util.List;

public class AdminActivity extends AppCompatActivity {


    /**
     * Menu admin du jeu
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Encoche
        Function.addNotch(this, R.color.header);

        // Bouton
        Function.closeAct(this, R.id.button_return);
        Function.toogleMute(this, R.id.button_mute);
        Function.openFile(this, R.id.button_ouvrir2);

        // Chargement des niveaux
        this.loadMaps();
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
        if (result != null && result.length() > 0) {
            String name = Function.getFileNameWithoutExtension(data);
            String[] lines = result.split("\\r?\\n|\\r");
            int width = lines[0].length();
            int height = lines.length;
            BoardEntity board = new BoardEntity(name, result, width, height);
            HomeActivity.db.addBoard(board);
            this.loadMaps();
        }
    }


    /**
     * Charge la liste des maps
     */
    private void loadMaps() {
        LinearLayout grid = (LinearLayout)findViewById(R.id.map_list);
        grid.removeAllViews();

        List<BoardEntity> maps = HomeActivity.db.getAllBoards();
        for (int i = 0; i < maps.size(); i++) {
            LinearLayout item = new LinearLayout(this);
            item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            item.setOrientation(LinearLayout.HORIZONTAL);
            grid.addView(item);

            Button delete = new Button(new ContextThemeWrapper(this, R.style.ButtonCancel), null, 0);


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 32, 64);
            delete.setLayoutParams(params);

            delete.setText("╳");
            delete.setHeight(150);
            delete.setWidth(150);
            delete.setTextSize(20);
            delete.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            int finalI = i;
            delete.setOnClickListener(v -> {
                HomeActivity.db.deleteBoard(maps.get(finalI));
                loadMaps();
            });
            item.addView(delete);

            TextView name = new TextView(this);
            name.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            name.setText(maps.get(i).name + " (" + maps.get(i).width + "x" + maps.get(i).height + ")");
            name.setGravity(Gravity.CENTER);
            name.setTextSize(25);
            item.addView(name);
        }
    }
}