package com.example;

import java.time.LocalDate;
import java.util.Scanner;

import com.example.entites.Categorie;
import com.example.entites.Emprunt;
import com.example.entites.Livre;
import com.example.entites.Membre;
import com.example.repository.EmpruntRepository;
import com.example.repository.LivreRepository;
import com.example.repository.MembreRepository;
import com.example.service.BibliothequeService;
import com.example.service.QuotaEmpruntException;

public class Main {
    public static void main(String[] args) {

        EmpruntRepository empruntRepo = new EmpruntRepository();
        LivreRepository livreRepo = new LivreRepository();
        MembreRepository membreRepo = new MembreRepository();

        BibliothequeService service = new BibliothequeService(empruntRepo);

        Livre livre = new Livre(1L, "Java", "Auteur", Categorie.INFORMATIQUE);
        Membre membre = new Membre(1L, "Ali");

        livreRepo.save(livre);
        membreRepo.save(membre);

        Scanner scanner = new Scanner(System.in);
        int choix;

        do {
            System.out.println("\n===== BIBLIOTHÈQUE =====");
            System.out.println("1. Emprunt");
            System.out.println("2. Retour");
            System.out.println("3. Emprunts membre");
            System.out.println("4. Livres en retard");
            System.out.println("5. Sortie");
            System.out.print("Choix : ");

            choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    Emprunt emprunt = new Emprunt(
                            1L, membre, livre,
                            LocalDate.now(),
                            LocalDate.now().plusDays(14)
                    );

                    try {
                        service.enregistrerEmprunt(emprunt);
                        System.out.println("Emprunt enregistré !");
                    } catch (QuotaEmpruntException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                 case 2:
                      service.enregistrerRetour(
                              new Emprunt(
                              1L,
                              membre,
                            livre,
                             LocalDate.now(),
                             LocalDate.now().plusDays(14)
            )
    );

    System.out.println("Retour enregistré !");
    break;
                case 3:
                    System.out.println(service.listerEmpruntsMembre(membre));
                    break;

                case 4:
                    System.out.println(service.listerLivresEnRetard());
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