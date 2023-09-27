package org.example;

import org.example.battleship.Battleship;

import java.util.Scanner;

import static org.example.Screen.showBattleshipScreen;

public class Main {
    public static void main(String[] args) {

        menu();
    }
    private static void menu() {

        Scanner sc = new Scanner(System.in);
        System.out.println("1 - морской бой");
        System.out.println("0 - выход");
        String answer = sc.nextLine();

        switch (answer) {
            case "1" -> {
                showBattleshipScreen();
                new Battleship();
            }
            case "0" -> System.out.println("до новых встреч");
            default -> {
                System.out.println("неправильный вариант. Напиши еще раз");
                menu();
            }
        }
    }
}