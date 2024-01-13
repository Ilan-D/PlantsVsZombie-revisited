package model;

import java.util.Scanner;

public class Launcher {

    public Launcher() {
        boolean shouldExit = true;
        while (shouldExit) {
            MapConfig map = new MapConfig(0);
            String choice = getUserInput();

            switch (choice) {
                case "1":
                    map.pour_facile();
                    shouldExit = false;
                    break;
                case "2":
                    map.pour_Moyen();
                    shouldExit = false;
                    break;
                case "3":
                    map.pour_dur();
                    shouldExit = false;
                    System.out.println("Nous n'avons pas encore implémenté le niveau difficile.");
                    break;
                case "M":
                    map.Pour_Marathon();
                    shouldExit = false;
                case "exit":
                    System.exit(0);
                    break;
                default:
                    System.out.println("\nVous n'avez pas bien rentré le numéro, veuillez réessayer.\n");
                    break;
            }
        }
    }

    public String getUserInput() {
        System.out.println("\n");
        System.out.print("Bonjour, veuillez choisir le niveau de difficulté souhaité (1, 2, 3, M) "
                + "pour quitter, tapez 'exit'): ");

        String userInput;
        try {
            Scanner scanner = new Scanner(System.in);
            userInput = scanner.next();
        } catch (Exception e) {
            System.out.println("Une erreur s'est produite lors de la saisie.");
            userInput = "";
        }
        return userInput;
    }

    public static void main(String[] args) {
        new Launcher();
    }
}
