package com.example.sokoban.logic;

public class Map {

    /**
     * Convertit une carte String en un tableau de char
     *
     * @param map La carte à charger
     * @return Un tableau de char
     */
    public static char[][] stringToMatrix(String map) {
        // Découpe la carte en lignes
        String[] lines = map.split("\n");
        // Initialise la matrice
        int height = lines.length;
        int width = lines[0].length();
        char[][] matrix = new char[height][width];
        // On parcourt les lignes de la carte
        int x = 0, y = 0;
        for (String line : lines) {
            // On parcourt les caractères de la ligne
            for (char c : line.toCharArray()) {
                // On remplit la matrice
                matrix[y][x] = c;
                x++;
            }
            x = 0;
            y++;
        }
        return matrix;
    }


    /**
     * Convertit un tableau de char en carte String
     *
     * @param map Le tableau de char
     * @return La carte en String
     */
    public static String matrixToString(char[][] map) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                sb.append(map[y][x]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
