package org.example.battleship;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Battleship {

    Field user = new Field();
    Field opponent = new Field();
    Scanner sc = new Scanner(System.in);
    boolean finish = false;

    public Battleship() {

        fillingOpponentsField();
        fillingField();
        playGame();
    }

    private void playGame() {

        System.out.println("""
                Первый ход за тобой:
                Пример хода: а1
                Обозначения:
                - - мимо
                † - попал""");

        while (!finish) {

            userStep();

            if (!finish) {

                opponentStep();
            }
        }
    }

    private void opponentStep() {

        int column = getRandomNumber(1, 10);
        int line = getRandomNumber(1, 10);

        System.out.println("Ход противника");

        if (Objects.equals(user.getField()[line][column], "●")) {

            user.getField()[line][column] = "†";

            if (checkWin(user.getField())) {
                finish = true;

                System.out.println("Противник победил!");
            }

            user.printField();
            opponentStep();
        } else {

            user.getField()[line][column] = "-";
            user.printField();
        }

        System.out.println();
    }

    private void userStep() {

        opponent.printGameField();
        System.out.println("твой ход");

        String input = sc.nextLine().toLowerCase();
        int column = opponent.getColumnNames().get(String.valueOf(input.charAt(0)));
        int line = Integer.parseInt(input.substring(1));

        if (Objects.equals(opponent.getField()[line][column], "●")) {

            opponent.getField()[line][column] = "†";
            opponent.getGameField()[line][column] = "†";

            System.out.println("Попал! Следующий ход тоже за тобой");

            if (checkWin(opponent.getField())) {

                finish = true;

                System.out.println("Ты победил!");
            }

            userStep();
        } else {

            opponent.getGameField()[line][column] = "-";

            opponent.printGameField();
            System.out.println("Мимо. Переход хода");
        }
    }

    private Boolean checkWin(String[][] field) {

        for (String[] row : field) {

            for (String i : row) {

                if (i.equals("●")) {

                    return false;
                }
            }
        }

        return true;
    }

    private void fillingOpponentsField() {

        String fileName = "src/main/java/org/example/battleship/files/input" + getRandomNumber(1, 1) + ".txt";

        try (Scanner sc = new Scanner(new File(fileName))) {

            while (sc.hasNext()) {

                String[] xx = sc.nextLine().split(" ");
                int column = opponent.getColumnNames().get(xx[0]);
                int line = Integer.parseInt(xx[1]);
                opponent.getField()[line][column] = "●";
            }

        } catch (FileNotFoundException e) {

            System.out.println("нет файла");
        }
    }

    private int getRandomNumber(int min, int max) {

        return (int) ((Math.random() * (max - min)) + min);
    }

    public void fillingField() {

        System.out.println("""
                Правила:
                Все корабли должны быть прямыми, не допускается изогнутых и «диагональных»;
                Корабли располагаются на игровом поле таким образом, чтобы между ними всегда был зазор в одну клеточку, то есть они не должны касаться друг друга ни бортами, ни углами;
                При этом корабли могут касаться краев поля и занимать углы.
                Пример написания для большого корабля: а1-г1
                Пример написания для 1-палубного корабля: а1
                Так выглядит твоя доска:
                """);

        user.printField();

        for (int i = 4; i > 0; i--) {

            for (int j = i; j < 5; j++) {

                addShip("Размести " + i + "-палубный корабль.", i);
            }
        }
    }

    private void addShip(String text, int size) {

        try {

            System.out.println(text);

            String[] str = sc.nextLine().toLowerCase().replaceAll(" ", "").split("-");

            if (str.length < 1) {

                System.out.println("Некорректный ввод");
                throw new ConflictException();
            }

            String head = str[0];
            String tail = str.length == 1 ? str[0] : str[1];

            checking(head, tail, size);
            add(head, tail);
        } catch (ConflictException e) {

            addShip(text, size);
        }

        user.printField();
    }

    private void add(String head, String tail) {

        String headColumn = String.valueOf(head.charAt(0));
        String tailColumn = String.valueOf(tail.charAt(0));
        int headField = Integer.parseInt(head.substring(1));
        int tailField = Integer.parseInt(tail.substring(1));

        if (Objects.equals(headColumn, tailColumn)) {

            for (int line = headField; line <= tailField; line++) {

                int column = user.getColumnNames().get(headColumn);
                user.getField()[line][column] = "●";

                user.getLimiters().add(headColumn + line);
            }
        } else {

            for (int i = user.getColumnNames().get(headColumn); i <= user.getColumnNames().get(tailColumn); i++) {

                user.getField()[headField][i] = "●";

                user.getLimiters().add(user.getFieldNames().get(i) + headField);
            }
        }
    }

    private void checking(String head, String tail, int size) {

        if (head.length() < 2 || tail.length() < 2) {

            System.out.println("Некорректный ввод");
            throw new ConflictException();
        }

        String headColumn = String.valueOf(head.charAt(0));
        String tailColumn = String.valueOf(tail.charAt(0));
        Integer headLine = Integer.parseInt(head.substring(1));
        Integer tailLine = Integer.parseInt(tail.substring(1));

        inputChecking(headColumn, tailColumn, headLine, tailLine);
        sizeChecking(headColumn, tailColumn, headLine, tailLine, size);
        limitersChecking(headColumn, tailColumn, headLine, tailLine);
    }

    private void limitersChecking(String headColumn, String tailColumn, Integer headLine, Integer tailLine) {

        List<String> lim = findLimits(headColumn, tailColumn, headLine, tailLine);

        for (String string : lim) {

            if (user.getLimiters().contains(string)) {

                System.out.println("Нельзя ставить настолько близко к другому кораблю");
                throw new ConflictException();
            }
        }
    }

    private List<String> findLimits(String headColumn, String tailColumn, Integer headLine, Integer tailLine) {

        List<String> lim = new ArrayList<>();

        if (Objects.equals(headColumn, tailColumn)) {

            String prev = user.getFieldNames().get(user.getColumnNames().get(headColumn) - 1);
            String next = user.getFieldNames().get(user.getColumnNames().get(headColumn) + 1);

            for (int i = headLine - 1; i <= tailLine + 1 && i <= 10; i++) {

                if (prev != null && i != 0) lim.add(prev + i);
                if (i != 0) lim.add(headColumn + i);
                if (next != null && i != 0) lim.add(next + i);
            }
        } else if (Objects.equals(headLine, tailLine)) {

            int prev = headLine - 1;
            int next = headLine + 1;

            for (int i = user.getColumnNames().get(headColumn) - 1; i <= user.getColumnNames().get(tailColumn) + 1; i++) {

                String letter = user.getFieldNames().get(i);

                if (i == 0 || letter == null) {

                    continue;
                }

                if (prev != 0) lim.add(letter + prev);
                lim.add(letter + headLine);
                if (next < 10) lim.add(letter + next);
            }
        }

        return lim;
    }

    private void sizeChecking(String headColumn, String tailColumn, Integer headLine, Integer tailLine, int size) {

        if (Objects.equals(headColumn, tailColumn)) {

            if (size != (tailLine - headLine + 1)) {

                System.out.println("Некорректный размер");
                throw new ConflictException();
            }
        } else if (Objects.equals(headLine, tailLine)) {

            int head = user.getColumnNames().get(headColumn);
            int tail = user.getColumnNames().get(tailColumn);

            if (size != (tail - head + 1)) {

                System.out.println("Некорректный размер");
                throw new ConflictException();
            }

        } else {

            System.out.println("По-диагонали нельзя");
            throw new ConflictException();
        }
    }

    private void inputChecking(String headColumn, String tailColumn, Integer headLine, Integer tailLine) {

        if (!user.getColumnNames().containsKey(headColumn) ||
                !user.getColumnNames().containsKey(tailColumn) ||
                !user.getFieldNames().containsKey(headLine) ||
                !user.getFieldNames().containsKey(tailLine)) {

            System.out.println("Некорректный ввод");
            throw new ConflictException();
        }
    }
}
