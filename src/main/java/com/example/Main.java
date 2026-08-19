package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    

     Scanner scanner = new Scanner(System.in);
        int choix;

        do {
            System.out.println("===== BIBLIOTHÈQUE =====");
            System.out.println("1. Enregistrer un emprunt");
            System.out.println("2. Enregistrer un retour");
            System.out.println("3. Lister les emprunts d'un membre");
            System.out.println("4. Lister les livres en retard");
            System.out.println("5. Sortie");
            System.out.print("Votre choix : ");

            while (!scanner.hasNextInt()) {
                System.out.println("Entrez un nombre entre 1 et 5.");
                scanner.next();
            }

            choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    System.out.println("Enregistrer un emprunt");
                    break;
                case 2:
                    System.out.println("Enregistrer un retour");
                    break;
                case 3:
                    System.out.println("Lister les emprunts d'un membre");
                    break;
                case 4:
                    System.out.println("Lister les livres en retard");
                    break;
                case 5:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }

        } while (choix != 5);


        scanner.close();
    }
}
