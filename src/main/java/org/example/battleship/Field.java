package org.example.battleship;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Field {

    String[][] field = new String[11][11];
    String[][] gameField = new String[11][11];
    Set<String> limiters = new HashSet<>();
    final Map<String, Integer> columnNames = new HashMap<>(Map.of("а", 1, "б", 2, "в", 3, "г", 4, "д", 5, "е", 6, "ж", 7, "з", 8, "и", 9, "к", 10));
    final Map<Integer, String> fieldNames = new HashMap<>(Map.of(1, "а", 2, "б", 3, "в", 4, "г", 5, "д", 6, "е", 7, "ж", 8, "з", 9, "и", 10, "к"));

    public Field() {

        for (int i = 0; i < 11; i++) {

            for (int j = 0; j < 11; j++) {

                if (i == 0 && j != 0) {

                    field[i][j] = String.valueOf(fieldNames.get(j));
                    gameField[i][j] = String.valueOf(fieldNames.get(j));
                } else if (j == 0 && i != 0) {

                    field[i][j] = String.valueOf(i);
                    gameField[i][j] = String.valueOf(i);
                } else {

                    field[i][j] = "◦";
                    gameField[i][j] = "◦";
                }
            }
        }
    }

    public void printGameField() {

        for (String[] row : gameField) {

            for (String i : row) {

                System.out.print(i);
                System.out.print("\t");
            }

            System.out.println();
        }
    }

    public void printField() {

        for (String[] row : field) {

            for (String i : row) {

                System.out.print(i);
                System.out.print("\t");
            }

            System.out.println();
        }
    }
}
