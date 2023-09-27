package org.example.battleship;

import java.util.*;

public class Field {

    private String[][] field = new String[11][11];
    private String[][] gameField = new String[11][11];
    private Set<String> limiters = new HashSet<>();
    private final Map<String, Integer> columnNames = new HashMap<>(Map.of("а", 1, "б", 2, "в", 3, "г", 4, "д", 5, "е", 6, "ж", 7, "з", 8, "и", 9, "к", 10));
    private final Map<Integer, String> fieldNames = new HashMap<>(Map.of(1, "а", 2, "б", 3, "в", 4, "г", 5, "д", 6, "е", 7, "ж", 8, "з", 9, "и", 10, "к"));

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

    @Override
    public String toString() {
        return "Field{" +
                "field=" + Arrays.toString(field) +
                ", gameField=" + Arrays.toString(gameField) +
                ", limiters=" + limiters +
                ", columnNames=" + columnNames +
                ", fieldNames=" + fieldNames +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field1 = (Field) o;
        return Arrays.deepEquals(field, field1.field) && Arrays.deepEquals(gameField, field1.gameField) && Objects.equals(limiters, field1.limiters) && Objects.equals(columnNames, field1.columnNames) && Objects.equals(fieldNames, field1.fieldNames);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(limiters, columnNames, fieldNames);
        result = 31 * result + Arrays.deepHashCode(field);
        result = 31 * result + Arrays.deepHashCode(gameField);
        return result;
    }

    public String[][] getField() {
        return field;
    }

    public void setField(String[][] field) {
        this.field = field;
    }

    public String[][] getGameField() {
        return gameField;
    }

    public void setGameField(String[][] gameField) {
        this.gameField = gameField;
    }

    public Set<String> getLimiters() {
        return limiters;
    }

    public void setLimiters(Set<String> limiters) {
        this.limiters = limiters;
    }

    public Map<String, Integer> getColumnNames() {
        return columnNames;
    }

    public Map<Integer, String> getFieldNames() {
        return fieldNames;
    }
}
